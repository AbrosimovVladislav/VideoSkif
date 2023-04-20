package com.videoskif.service;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTService {

  //ToDo Implement
  public List<String> getPhrasesByTopicName(String topicName) {

    return List.of(
        "The Roman Republic was known for its infrastructure AND It built roads, aqueducts, and public works");
  }

}
