package com.videoskif.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationOrchestrator {

  private final ChatGPTService chatGPTService;
  private final DalleService dalleService;
  private final MusicService musicService;

  public void generateVideos(String topicName) {
    var phrases = chatGPTService.getPhrasesByTopicName(topicName);
    var phrasesAndPictures = dalleService.getPicturesByPhrases(phrases);
    var music = musicService.getRandomMusicByTopicName(topicName);


  }

}
