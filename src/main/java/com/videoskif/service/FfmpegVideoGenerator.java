package com.videoskif.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FfmpegVideoGenerator {

  @Value("${video-generation.audiopath}")
  public String AUDIO_PATH;
  @Value("${video-generation.output-path}")
  public String OUTPUT_PATH;

  public String generateVideo(String firstPhrase, String secondPhrase, String image, String music) {

    String outputPath = OUTPUT_PATH.replace("{num}",String.valueOf(Math.random()));

    String[] cmd = {"/opt/homebrew/bin/ffmpeg", "-loop", "1", "-i", image, "-i", music,
        "-vf",
        "scale=1920:1080,format=rgba,zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125,fade=t=in:st=0:d=1:alpha=1,fade=t=out:st=14:d=1:alpha=1,drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,0,7)',drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,8,14)',drawtext=text='"
            + firstPhrase
            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,0,7)',drawtext=text='"
            + secondPhrase
            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,8,14)'",
        "-c:a", "copy", "-shortest", "-t", "14", outputPath};

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

