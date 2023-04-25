package com.videoskif.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGPTService {

  @Value("${open-ai.bearer-key}")
  public String BEARER_KEY;

  private final RestTemplate restTemplate;

  //ToDo Implement
  public List<String> getPhrasesByTopicName(String topicName) {

    return gptCall(topicName, 5);
  }

  public List<String> gptCall(String topicName, Integer amount) {
    String url = "https://api.openai.com/v1/chat/completions";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(BEARER_KEY);

    HttpEntity<GPTRequestBody> requestEntity = new HttpEntity<>(
        new GPTRequestBody().gptRequestBody(topicName, amount), headers);
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
        requestEntity,
        String.class);

    String lines = response.getBody();
    lines = lines.substring(lines.indexOf("\"content\":\""));
    lines = lines.replace("\"content\":\"", "");
    lines = lines.replace("\"},\"finish_reason\":\"stop\",\"index\":0}]}","");
    lines = lines.replace(".\\n","@");
    return Arrays.stream(lines.split("@")).collect(Collectors.toList());
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class GPTRequestBody {

    private String model;
    private Double temperature;
    private List<RequestMessage> messages;

    public GPTRequestBody gptRequestBody(String phrase, Integer amount) {
      return new GPTRequestBody(
          "gpt-3.5-turbo",
          0.7,
          List.of(new RequestMessage(
              "user",
              "Hello I want to get " + amount + " facts about " + phrase
                  + ". I want this facts consists of two parts. I want to get them in next format. 'Interesting sentence' AND 'fact which is connected to it'. Example: The Roman Republic was established in 509 BC after the overthrow of the Roman monarchy AND By the legend it was created by two brothers Rome and Remul. Each sentence should be shorter than 10 words and bigger than 5 words. And it should be like, first sentence gives interesting fact, and second one gives even more interesting and connected to first one"
          ))
      );
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class RequestMessage {

    private String role;
    private String content;
  }

  /*
  * {
  "model": "gpt-3.5-turbo",
  "messages": [
    {
      "role": "user",
      "content": "Hello I want to get 10 facts about Roman Republic. I want this facts consists of two parts. I want to get them in next format. 'Interesting sentence' AND 'fact which is connected to it'. Example: The Roman Republic was established in 509 BC after the overthrow of the Roman monarchy AND By the legend it was created by two brothers Rome and Remul. Each sentence should be shorter than 10 words and bigger than 5 words. And it should be like, first sentence gives interesting fact, and second one gives even more interesting and connected to first one"
    }
  ],
  "temperature": 0.7
}*/

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class GPTResponse {

    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class Choice {

    private Message message;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class Message {

    private String role;
    private String content;
  }

}





/*
* {
  "id": "chatcmpl-77nLS3lluyt4JXejnrnOlKIQ62a0z",
  "object": "chat.completion",
  "created": 1682092334,
  "model": "gpt-3.5-turbo-0301",
  "usage": {
    "prompt_tokens": 124,
    "completion_tokens": 186,
    "total_tokens": 310
  },
  "choices": [
    {
      "message": {
        "role": "assistant",
        "content": "1. Roman Republic lasted for over 450 years AND was replaced by Roman Empire in 27 BC.\n2. The Roman Republic was governed by two consuls AND they held power for one year.\n3. Roman Republic's territory expanded through military conquests AND it controlled most of Mediterranean.\n4. Plebeians had limited rights in Roman Republic AND they could not hold important positions.\n5. The Roman Republic had a complex system of government AND it included the Senate and Assembly.\n6. The Punic Wars were fought during the Roman Republic AND it was against Carthage.\n7. Roman Republic's society was divided into social classes AND it had slaves.\n8. Julius Caesar was assassinated during the Roman Republic AND it led to the end of the Republic.\n9. The Roman Republic was characterized by political instability AND frequent civil wars.\n10. Roman Republic's legal system influenced modern Western law AND it included the Twelve Tables."
      },
      "finish_reason": "stop",
      "index": 0
    }
  ]
}
* */