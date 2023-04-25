package com.videoskif.web.api;

import com.videoskif.service.GenerationOrchestrator;
import com.videoskif.service.S3Service;
import java.util.List;
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
  private final S3Service s3Service;

  @GetMapping("/topic/{topicName}")
  public List<String> generateVideosByTopic(@PathVariable String topicName){
    return orchestrator.generateVideos(topicName);
  }

}
