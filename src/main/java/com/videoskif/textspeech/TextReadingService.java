package com.videoskif.textspeech;

import com.github.kilianB.apis.googleTextToSpeech.GLanguage;
import com.github.kilianB.apis.googleTextToSpeech.GoogleTextToSpeech;
import com.videoskif.amazon.S3Service;
import com.videoskif.random.RandomService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextReadingService {

  @Value("${text-speech.output-path}")
  public String FILE_OUTPUT_PATH;

  private final S3Service s3Service;
  private final RandomService randomService;

  public String readText(String text) {
    //Create directory
    File outputDirectory = new File(FILE_OUTPUT_PATH);
    outputDirectory.mkdirs();

    //Convert the text and retrieve an mp3 file
    GoogleTextToSpeech tts = new GoogleTextToSpeech(FILE_OUTPUT_PATH);
    File convertedTextMP3 = tts.convertText(text, GLanguage.English_US,
        "FileName" + randomService.randomNumber());
    log.info("Text speech sound successfully created: <{}>", convertedTextMP3.getPath());

    return s3Service.saveSoundToStore(convertedTextMP3,
        "speech-" + Math.abs(Math.abs(Math.random()) * 100 * Math.random()) + ".mp3");
  }


}
