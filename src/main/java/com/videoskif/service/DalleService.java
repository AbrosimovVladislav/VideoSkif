package com.videoskif.service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DalleService {

  @Value("${open-ai.bearer-key}")
  public String BEARER_KEY;

  private final RestTemplate restTemplate;
  private final S3Service s3Service;

  public Map<String, String> getPicturesByPhrases(List<String> phrases) {
    return phrases.stream()
        .map(e -> new AbstractMap.SimpleEntry<>(e, getPictureByPhrase(e)))
        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
  }

  //ToDo Implement
  private String getPictureByPhrase(String phrase) {
    return dalleCall(phrase, "1024x1024");
  }

  private String dalleCall(String phrase, String size) {
    String url = "https://api.openai.com/v1/images/generations";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(BEARER_KEY);

    HttpEntity<DalleRequestBody> requestEntity = new HttpEntity<>(
        new DalleRequestBody(makePhraseMoreAccurateForDalleModel(phrase), 1, size), headers);
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
        String.class);
    String image = response.getBody();
    image = image.substring(image.indexOf("\"url\":"));
    image = image.replace("\"url\": \"", "");
    image = image.replace("\"", "");
    image = image.replace("}", "");
    image = image.replace("]", "");
    log.info("Image link received from Dalle <{}>", image);

    String savedImageLink = s3Service.saveImageToStore(image, phrase + "-" + Math.random());

    return savedImageLink;
  }

  private String makePhraseMoreAccurateForDalleModel(String phrase) {
    return phrase + " ultra realistic picture";
  }

  @Data
  class DalleResponse {

    Integer status;
    String headers;
    DalleResponseBody body;
  }

  @Data
  class DalleResponseBody {

    Long created;
    List<String> data;
  }

  @Data
  @AllArgsConstructor
  class DalleRequestBody {

    String prompt;
    Integer n;
    String size;
  }

  /*
  * {
  "prompt": "The Roman Republic was known for its infrastructure. AND It built roads, aqueducts, and public works.",
  "n": 1,
  "size": "1024x1024"
}*/

}
