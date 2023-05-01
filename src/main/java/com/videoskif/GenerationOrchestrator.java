package com.videoskif;

import com.videoskif.ffmpegsdk.FfmpegVideoGenerator;
import com.videoskif.image.service.ImageGeneratorService;
import com.videoskif.phrase.component.PhraseCombinator;
import com.videoskif.phrase.service.PhraseGeneratorService;
import com.videoskif.music.MusicService;
import com.videoskif.textspeech.TextReadingService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationOrchestrator {

  private final PhraseGeneratorService phraseGeneratorService;
  private final ImageGeneratorService imageGeneratorService;
  private final MusicService musicService;
  private final PhraseCombinator phraseCombinator;
  private final FfmpegVideoGenerator generator;
  private final TextReadingService textReadingService;

  public List<String> generateVideos(String topicName, Integer amountOfVideos) {
    var phrases = phraseGeneratorService.getMockPhrases(topicName, amountOfVideos);
    var phrasesAndPictures = imageGeneratorService.getMockPictures(phrases);
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
