package com.videoskif.image.service;

import com.videoskif.amazon.S3Service;
import com.videoskif.image.model.request.DalleRequest;
import com.videoskif.image.service.client.DalleClient;
import com.videoskif.random.RandomService;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageGeneratorService {

  @Value("${image-generation.size}")
  public String IMAGE_SIZE;

  private final S3Service s3Service;
  private final DalleClient dalleClient;
  private final RandomService randomService;

  public Map<String, String> getPicturesByPhrases(List<String> phrases) {
    return phrases.stream()
        .map(e -> new AbstractMap.SimpleEntry<>(e, getPictureByPhrase(e)))
        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
  }

  public Map<String,String> getMockPictures(List<String> phrases){
    return Map.of(phrases.get(0),"https://gunmarket.fra1.digitaloceanspaces.com/2.%20Rome's%20expansion%20during%20the%20Republic%20led%20to%20Punic%20Wars%20AND%20the%20defeat%20of%20Carthage.-7834592");
  }

  private String getPictureByPhrase(String phrase) {
    DalleRequest dalleRequest =
        new DalleRequest(makePhraseMoreAccurateForModel(phrase), 1, IMAGE_SIZE);
    ResponseEntity<String> response = dalleClient.call(dalleRequest);
    String responseBody = response.getBody();
    String image = convertResponseToImage(Objects.requireNonNull(responseBody));
    log.info("Request to Dalle successfully done. Image Url:<{}>", image);
    return s3Service.saveImageToStore(image, phrase.strip() + "-" + randomService.randomNumber());
  }

  private String convertResponseToImage(String response) {
    String image = response.substring(response.indexOf("\"url\":"));
    image = image.replace("\"url\": \"", "");
    image = image.replace("\"", "");
    image = image.replace("}", "");
    return image.replace("]", "");
  }

  private String makePhraseMoreAccurateForModel(String phrase) {
    return phrase + " ultra realistic picture";
  }
}
