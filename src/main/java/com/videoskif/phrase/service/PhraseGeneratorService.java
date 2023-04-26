package com.videoskif.phrase.service;

import com.videoskif.phrase.model.request.ChatGPTRequest;
import com.videoskif.phrase.service.client.ChatGPTClient;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhraseGeneratorService {

  private final ChatGPTClient chatGPTClient;

  public List<String> getPhrasesByTopicName(String topicName, Integer amountOfVideos) {
    ResponseEntity<String> response = chatGPTClient.call(
        new ChatGPTRequest().gptRequestBody(topicName, amountOfVideos));
    String responseBody = response.getBody();
    List<String> phrases = convertResponseToPhrases(responseBody);
    log.info("Request to ChatGPT successfully done. Phrases:<{}>", phrases);
    return phrases;
  }

  private List<String> convertResponseToPhrases(String response) {
    String lines = response.substring(response.indexOf("\"content\":\""));
    lines = lines.replace("\"content\":\"", "");
    lines = lines.replace("\"},\"finish_reason\":\"stop\",\"index\":0}]}", "");
    lines = lines.replace("\\n\\n", "\\n");
    lines = lines.replace("\\n", "@");
    return Arrays.stream(lines.split("@")).collect(Collectors.toList());
  }

}