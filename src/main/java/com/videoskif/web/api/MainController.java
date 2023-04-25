package com.videoskif.web.api;

import com.videoskif.service.GenerationOrchestrator;
import com.videoskif.service.S3Service;
import com.videoskif.web.api.dto.SaveImageRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/image")
  public String saveImage(@RequestBody SaveImageRequest request){
    return s3Service.saveImageToStore(request.getSrc(), "Image-1");
  }

}
