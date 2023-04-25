package com.videoskif.service;

import com.videoskif.service.DalleService.DalleRequestBody;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

  private final RestTemplate restTemplate;

  //ToDo Implement
  public List<String> getPhrasesByTopicName(String topicName) {

    return gptCall(topicName, 5);

//    return List.of(
//        "The Roman Republic was known for its infrastructure AND It built roads, aqueducts, and public works",
//        "The Roman Republic began in 509 BC AND It replaced the Roman monarchy",
//        "The Roman Republic was founded on shared power AND Executive, legislative, and judicial branches shared power",
//        "The highest office in the Roman Republic was consul AND Two consuls were elected each year",
//        "The Roman Republic's legal system was codified AND The Twelve Tables were a key part"
////        "The Roman Republic was an expansionist power AND It conquered numerous territories",
////        "The Roman Republic had a strong military AND Soldiers were loyal to the state",
////        "The Roman Republic had a complex social hierarchy AND Plebeians and patricians had different status",
////        "The Roman Republic was a polytheistic society AND Religion played an important role",
////        "The Roman Republic fell in 27 BC AND It was replaced by the Roman Empire"
//    );
  }

  public List<String> gptCall(String topicName, Integer amount) {
    String url = "https://api.openai.com/v1/chat/completions";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth("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");

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
//    String content = response.getBody().getChoices().get(0).getMessage().getContent();
//    return Arrays.stream(content.split(".\\n2.")).collect(Collectors.toList());
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