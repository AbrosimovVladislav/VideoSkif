package com.videoskif.phrase.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponse {

  private String id;
  private String object;
  private Long created;
  private String model;
  private List<ChatGPTChoiceDTO> choices;
}
