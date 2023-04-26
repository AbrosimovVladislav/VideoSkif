package com.videoskif.phrase.service.client;

import com.videoskif.phrase.model.request.ChatGPTRequest;
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
public class ChatGPTClient {

  @Value("${open-ai.chat-gpt-url}")
  public String CHAT_GPT_URL;

  @Value("${open-ai.bearer-key}")
  public String OPEN_AI_BEARER_KEY;

  private final RestTemplate restTemplate;

  public ResponseEntity<String> call(ChatGPTRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(OPEN_AI_BEARER_KEY);

    HttpEntity<ChatGPTRequest> requestEntity = new HttpEntity<>(request, headers);
    ResponseEntity<String> response = restTemplate.exchange(
        CHAT_GPT_URL,
        HttpMethod.POST,
        requestEntity,
        String.class);
    log.info("ChatGPT request: <{}>. ChatGPT response: <{}>", request, response);
    return response;
  }

}
