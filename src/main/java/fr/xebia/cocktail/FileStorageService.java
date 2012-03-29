package fr.xebia.cocktail;

import java.io.InputStream;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

@Service
public class FileStorageService {

    private Map<String, String> contentTypeByFileExtension;

    private Map<String, String> defaultFileExtensionByContentType;

    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucketBaseUrl}")
    private String amazonS3BucketBaseUrl;

    @Value("${aws.s3.bucketName}")
    private String amazonS3BucketName;

    private final Random random = new Random();

    @Inject
    public FileStorageService(@Value("${aws.accessKey}") String awsAccessKey, @Value("${aws.secretKey}") String awsSecretKey) {

        amazonS3 = new AmazonS3Client(new BasicAWSCredentials(awsAccessKey, awsSecretKey));

        contentTypeByFileExtension = Maps.newHashMapWithExpectedSize(4);
        contentTypeByFileExtension.put("jpg", "image/jpeg");
        contentTypeByFileExtension.put("jpeg", "image/jpeg");
        contentTypeByFileExtension.put("png", "image/png");
        contentTypeByFileExtension.put("gif", "image/gif");

        defaultFileExtensionByContentType = Maps.newHashMapWithExpectedSize(3);
        defaultFileExtensionByContentType.put("image/jpeg", "jpg");
        defaultFileExtensionByContentType.put("image/png", "png");
        defaultFileExtensionByContentType.put("image/gif", "gif");
    }

    @Nullable
    public String findContentType(String fileName) {
        String fileExtension = Iterables.getLast(Splitter.on('.').split(fileName), null);
        fileExtension = Strings.nullToEmpty(fileExtension).toLowerCase();
        return contentTypeByFileExtension.get(fileExtension);
    }

    @Nonnull
    public String storeFile(InputStream in, ObjectMetadata objectMetadata) {
        String extension = defaultFileExtensionByContentType.get(objectMetadata.getContentType());
        String fileName = Math.abs(random.nextLong()) + "." + extension;

        amazonS3.putObject(amazonS3BucketName, fileName, in, objectMetadata);

        return amazonS3BucketBaseUrl + fileName;
    }
}
