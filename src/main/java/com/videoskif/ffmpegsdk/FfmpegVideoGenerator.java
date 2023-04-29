package com.videoskif.ffmpegsdk;

import com.videoskif.ffmpegsdk.model.FfmpegCommand;
import com.videoskif.random.RandomService;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FfmpegVideoGenerator {

  public static final String ADDITIONAL_PHRASE = "If you want to know more, check link in the description!";

  @Value("${video-generation.output.directory}")
  public String DIRECTORY_PATH;

  @Value("${video-generation.output.filename_pattern}")
  public String FILENAME_PATTERN;

  private final RandomService randomService;
  private final FfmpegCommandConverter ffmpegCommandConverter;

  public String generateVideo(String firstPhrase, String secondPhrase, String image, String music,
      String phraseSound) {
    var outputDirectory = new File(DIRECTORY_PATH);
    outputDirectory.mkdirs();

    String outputPath = DIRECTORY_PATH + FILENAME_PATTERN.replace("{num}", String.valueOf(randomService.randomNumber()));

    String[] cmd = ffmpegCommandConverter.getCommand(
        FfmpegCommand.builder()
            .images(List.of(image))
            .sounds(List.of(music, phraseSound))
            .phrases(List.of(firstPhrase, secondPhrase, ADDITIONAL_PHRASE))
            .zoomOptions("scale=1920:1080,format=rgba,zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125,")
            .drawOptions("fade=t=in:st=0:d=1:alpha=1,fade=t=out:st=14:d=1:alpha=1,drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,0,8)',drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,8,15)',drawtext=text='")
            .outputPath(outputPath)
            .build()
    );

//    String[] cmd = {"/opt/homebrew/bin/ffmpeg", "-loop", "1", "-i", image, "-i", music, "-i",
//        phraseSound,
//        "-filter_complex", "amerge=inputs=2",
//        "-vf",
//        "scale=1920:1080,format=rgba,zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125,fade=t=in:st=0:d=1:alpha=1,fade=t=out:st=14:d=1:alpha=1,drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,0,7)',drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,8,14)',drawtext=text='"
//            + firstPhrase
//            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,0,5)',drawtext=text='"
//            + secondPhrase
//            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,6,10)',drawtext=text='"
//            + ADDITIONAL_PHRASE
//            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,11,14)'",
//        "-shortest",
//        "-t", "14", outputPath};

    System.out.println("AFTER ffmpeg_command: " + Arrays.toString(cmd));

    ProcessBuilder pb = new ProcessBuilder(cmd);
    pb.redirectErrorStream(true);

    try {
      Process process = pb.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        log.info(line);
      }
      return outputPath;
    } catch (Exception e) {
      log.error(e.toString());
      return null;
    }

  }

}

