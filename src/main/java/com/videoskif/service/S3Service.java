package com.videoskif.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

  private final AmazonS3 s3Client;

  private static final String IMAGE_CONTENT_TYPE = "image/png";
  private static final String MP3_CONTENT_TYPE = "audio/mp3";

  @Value("${do.spaces.bucket}")
  private String doSpaceBucket;

  @Value("${do.spaces.endpoint}")
  private String doSpaceEndpoint;

  public String saveImageToStore(String srcImageUrl, String imgName) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(IMAGE_CONTENT_TYPE);

    try {
      var url = new URL(srcImageUrl);
      s3Client.putObject(
          new PutObjectRequest(doSpaceBucket, imgName, url.openStream(), metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (Exception e) {
      log.error("Image downloading failed for product image: " + imgName);
      e.printStackTrace();
    }
    return "https://" + doSpaceBucket + "." + doSpaceEndpoint + "/" + imgName;
  }

  public String saveSoundToStore(File soundFile, String soundName) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(MP3_CONTENT_TYPE);

    try {
      s3Client.putObject(new PutObjectRequest(doSpaceBucket, soundName, soundFile)
          .withMetadata(metadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (Exception e) {
      log.error("Sound uploading failed for audio file: " + soundName);
      e.printStackTrace();
    }
    return "https://" + doSpaceBucket + "." + doSpaceEndpoint + "/" + soundName;
  }

}