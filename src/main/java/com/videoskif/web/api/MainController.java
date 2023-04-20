package com.videoskif.web.api;

import com.videoskif.service.GenerationOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

  private final GenerationOrchestrator orchestrator;

  @GetMapping("/topic/{topicName}")
  public void generateVideosByTopic(@PathVariable String topicName){

  }

}
