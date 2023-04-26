package com.videoskif;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoGenApi {

  private final GenerationOrchestrator orchestrator;

  @GetMapping("/topic/{topicName}")
  public List<String> generateVideosByTopic(@PathVariable String topicName){
    return orchestrator.generateVideos(topicName, 3);
  }

}
