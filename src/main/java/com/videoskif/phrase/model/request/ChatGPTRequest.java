package com.videoskif.phrase.model.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTRequest {
  private String model;
  private Double temperature;
  private List<ChatGPTRequestMessageDTO> messages;

  public ChatGPTRequest gptRequestBody(String phrase, Integer amount) {
    return new ChatGPTRequest(
        "gpt-3.5-turbo",
        0.7,
        List.of(new ChatGPTRequestMessageDTO(
            "user",
            "Hello I want to get " + amount + " facts about " + phrase
                + ". I want this facts consists of two parts. I want to get them in next format. 'Interesting sentence' AND 'fact which is connected to it'. Example: The Roman Republic was established in 509 BC after the overthrow of the Roman monarchy AND By the legend it was created by two brothers Rome and Remul. Each sentence should be shorter than 10 words and bigger than 5 words. And it should be like, first sentence gives interesting fact, and second one gives even more interesting and connected to first one"
        ))
    );
  }
}
