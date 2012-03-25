/*
 * Copyright 2008-2012 Xebia and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.xebia.cocktail;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

@Controller
public class CocktailManager {

    private AmazonS3 amazonS3;

    private String amazonS3BucketBaseUrl = "http://xebia-cocktail.s3-website-us-east-1.amazonaws.com/";

    private String amazonS3BucketName = "xebia-cocktail";

    @Inject
    private CocktailRepository cocktailRepository;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private MailService mailService;

    private final Random random = new Random();

    public CocktailManager() {
        String credentialsFilePath = "AwsCredentials.properties";
        InputStream credentialsAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(credentialsFilePath);
        Preconditions.checkState(credentialsAsStream != null, "File '" + credentialsFilePath + "' NOT found in the classpath");
        try {
            AWSCredentials awsCredentials = new PropertiesCredentials(credentialsAsStream);
            amazonS3 = new AmazonS3Client(awsCredentials);
        } catch (IOException e) {
            throw new IllegalStateException("Exception loading '" + credentialsFilePath + "'", e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cocktail/completion")
    @ResponseBody
    public List<String> autoCompleteName(@RequestParam("term") String term) {
        List<String> words = this.cocktailRepository.autocompleteCocktailNameWords(term);
        logger.trace("autocomplete word for {}:{}", term, words);
        return words;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cocktail")
    public String create(@Valid Cocktail cocktail, BindingResult result) {
        if (result.hasErrors()) {
            return "cocktail/create-form";
        }

        cocktailRepository.insert(cocktail);

        return "redirect:/cocktail/" + cocktail.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cocktail/create-form")
    public String displayCreateForm(Model model) {
        model.addAttribute(new Cocktail());
        return "cocktail/create-form";
    }

    @RequestMapping(value = "/cocktail/{id}/edit-form", method = RequestMethod.GET)
    public String displayEditForm(@PathVariable String id, Model model) {
        Cocktail cocktail = cocktailRepository.get(id);
        if (cocktail == null) {
            throw new ResourceNotFoundException(id);
        }
        model.addAttribute(cocktail);
        return "cocktail/edit-form";
    }

    @RequestMapping(value = "/cocktail/search-form", method = RequestMethod.GET)
    public String displaySearchForm() {
        return "cocktail/search-form";
    }

    @RequestMapping(value = "/cocktail/{id}/mail", method = RequestMethod.POST)
    public String sendEmail(@PathVariable String id, @RequestParam("recipientEmail") String recipientEmail, HttpServletRequest request) {
        Cocktail cocktail = cocktailRepository.get(id);
        if (cocktail == null) {
            throw new ResourceNotFoundException(id);
        }
        try {
            String cocktailPageUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/cocktail/" + id;
            mailService.sendCocktail(cocktail, recipientEmail, cocktailPageUrl);
        } catch (MessagingException e) {
            throw Throwables.propagate(e);
        }
        return "redirect:/cocktail/" + cocktail.getId();
    }

    @RequestMapping(value = "/cocktail/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable String id, @Valid Cocktail cocktail, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/cocktail/{id}/edit";
        }

        cocktail.setId(id);
        cocktailRepository.update(cocktail);

        return "redirect:/cocktail/{id}";
    }

    /**
     * TODO use PUT instead of POST
     * 
     * @param id
     * @param photo
     * @return
     */
    @RequestMapping(value = "/cocktail/{id}/photo", method = RequestMethod.POST)
    public String updatePhoto(@PathVariable String id, @RequestParam("photo") MultipartFile photo) {
        final Map<String, String> permittedPhotoExtensionsWithContentType = Maps.newHashMapWithExpectedSize(4);
        permittedPhotoExtensionsWithContentType.put("jpg", "image/jpeg");
        permittedPhotoExtensionsWithContentType.put("jpeg", "image/jpeg");
        permittedPhotoExtensionsWithContentType.put("png", "image/png");
        permittedPhotoExtensionsWithContentType.put("gif", "image/gif");

        String originalFilename = photo.getOriginalFilename();
        if (photo.isEmpty()) {
            logger.warn("photo", "Skip empty file '" + originalFilename + "'");
        } else {
            try {

                String extension = Iterables.getLast(Splitter.on('.').split(originalFilename), null);
                extension = Strings.nullToEmpty(extension).toLowerCase();
                String contentType = permittedPhotoExtensionsWithContentType.get(extension);
                if (contentType == null) {
                    logger.warn("photo", "Skip file with unsupported extension '" + originalFilename + "'");
                } else {
                    String photoFileName = Math.abs(random.nextLong()) + "." + extension;
                    String photoUrl;

                    byte[] bytes = photo.getBytes();

                    // AMAZON S3
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(bytes.length);
                    objectMetadata.setContentType(contentType);
                    objectMetadata.setCacheControl("public, max-age=" + TimeUnit.SECONDS.convert(365, TimeUnit.DAYS));
                    amazonS3.putObject(amazonS3BucketName, photoFileName, ByteStreams.newInputStreamSupplier(bytes).getInput(),
                            objectMetadata);
                    photoUrl = amazonS3BucketBaseUrl + photoFileName;

                    Cocktail cocktail = cocktailRepository.get(id);
                    logger.info("Saved {}", photoUrl);
                    cocktail.setPhotoUrl(photoUrl);
                    cocktailRepository.update(cocktail);

                }

            } catch (IOException e) {
                throw Throwables.propagate(e);
            }
        }
        return "redirect:/cocktail/" + id;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cocktail/{id}")
    public String view(@PathVariable String id, Model model) {
        Cocktail cocktail = cocktailRepository.get(id);
        if (cocktail == null) {
            throw new ResourceNotFoundException(id);
        }
        model.addAttribute(cocktail);
        return "cocktail/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cocktail")
    public ModelAndView viewAll() {
        return new ModelAndView("cocktail/view-all", "cocktails", cocktailRepository.getAll());
    }
}
