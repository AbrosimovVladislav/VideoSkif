package com.videoskif.service;

import com.github.kilianB.apis.googleTextToSpeech.GLanguage;
import com.github.kilianB.apis.googleTextToSpeech.GoogleTextToSpeech;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextReadingService {

  private final S3Service s3Service;

  public String readText(String text) {
    String outputPath = "src/main/resources/";

    //Create directory
    File outputDirectory = new File(outputPath);
    outputDirectory.mkdirs();

    //Convert the text and retrieve an mp3 file
    GoogleTextToSpeech tts = new GoogleTextToSpeech(outputPath);
    File convertedTextMP3 = tts.convertText(text, GLanguage.English_US, "FileName");
    String sound = s3Service.saveSoundToStore(convertedTextMP3,
        "speach-" + Math.abs(Math.abs(Math.random())*100 * Math.random()) + ".mp3");

    return sound;
  }


}
