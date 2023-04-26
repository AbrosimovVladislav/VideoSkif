package com.videoskif.image.service.client;

import com.videoskif.image.model.request.DalleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DalleClient {

  @Value("${open-ai.dalle-url}")
  public String DALLE_URL;

  @Value("${open-ai.bearer-key}")
  public String OPEN_AI_BEARER_KEY;

  private final RestTemplate restTemplate;

  public ResponseEntity<String> call(DalleRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(OPEN_AI_BEARER_KEY);

    HttpEntity<DalleRequest> requestEntity = new HttpEntity<>(request, headers);
    ResponseEntity<String> response = restTemplate.exchange(
        DALLE_URL,
        HttpMethod.POST,
        requestEntity,
        String.class);
    log.info("Dalle request: <{}>. Dalle response: <{}>", request, response);
    return response;
  }

}
