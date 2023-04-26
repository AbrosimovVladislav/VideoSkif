package com.videoskif.service;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationOrchestrator {

  private final ChatGPTService chatGPTService;
  private final DalleService dalleService;
  private final MusicService musicService;
  private final PhraseCombinator phraseCombinator;
  private final FfmpegVideoGenerator generator;
  private final TextReadingService textReadingService;

  public List<String> generateVideos(String topicName, Integer amountOfVideos) {
    var phrases = chatGPTService.getPhrasesByTopicName(topicName, amountOfVideos);
    var phrasesAndPictures = dalleService.getPicturesByPhrases(phrases);
    var music = musicService.getRandomMusicByTopicName(topicName);

    return phrasesAndPictures.entrySet().stream()
        .map(e -> {
          String phrase = e.getKey();
          var twoPartsPhrase = phraseCombinator.twoPartsPhrase(phrase);
          return generator.generateVideo(twoPartsPhrase.getKey(), twoPartsPhrase.getValue(),
              e.getValue(),
              music, textReadingService.readText(phrase));
        })
        .collect(Collectors.toList());

  }

}
