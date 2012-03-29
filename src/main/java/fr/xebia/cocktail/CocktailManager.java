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
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
@Controller
public class CocktailManager {

    @Inject
    private CocktailRepository cocktailRepository;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private MailService mailService;

    @Inject
    private AmazonS3FileStorageService fileStorageService;

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

    @RequestMapping(method = RequestMethod.GET, value = "/cocktail/suggest/ingredient")
    @ResponseBody
    public List<String> suggestCocktailIngredientWord(@RequestParam("term") String term) {
        List<String> words = this.cocktailRepository.suggestCocktailIngredientWords(term);
        logger.trace("autocomplete word for {}:{}", term, words);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cocktail/suggest/name")
    @ResponseBody
    public List<String> suggestCocktailNameWord(@RequestParam("term") String term) {
        List<String> words = this.cocktailRepository.suggestCocktailNameWords(term);
        logger.trace("autocomplete word for {}:{}", term, words);
        return words;
    }

    @RequestMapping(value = "/cocktail/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable String id, @Valid Cocktail cocktail, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/cocktail/{id}/edit";
        }

        cocktail.setId(id);

        // remove empty ingredients (caused by empty input fields in the GUI)
        Collection<Ingredient> ingredients = Collections2.filter(cocktail.getIngredients(), new Predicate<Ingredient>() {
            @Override
            public boolean apply(Ingredient ingredient) {
                return StringUtils.hasLength(ingredient.getName()) && StringUtils.hasLength(ingredient.getQuantity());
            }
        });

        cocktail.setIngredients(Lists.newArrayList(ingredients));
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

        if (!photo.isEmpty()) {
            try {
                String contentType = fileStorageService.findContentType(photo.getOriginalFilename());
                if (contentType == null) {
                    logger.warn("photo", "Skip file with unsupported extension '" + photo.getOriginalFilename() + "'");
                } else {

                    InputStream photoInputStream = photo.getInputStream();
                    long photoSize = photo.getSize();

                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(photoSize);
                    objectMetadata.setContentType(contentType);
                    objectMetadata.setCacheControl("public, max-age=" + TimeUnit.SECONDS.convert(365, TimeUnit.DAYS));
                    String photoUrl = fileStorageService.storeFile(photoInputStream, objectMetadata);

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
