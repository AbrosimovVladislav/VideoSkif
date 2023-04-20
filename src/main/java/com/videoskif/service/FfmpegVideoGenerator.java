package com.videoskif.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FfmpegVideoGenerator {

  public void generateVideo() {
    String image = "src/main/resources/image1.png";
    String audio = "src/main/resources/input.mp3";
    String output = "src/main/resources/output.mp4";
    String text = "And It built roads, aqueducts, and public works";

    String command = "ffmpeg -loop 1 -i "
        + image
        + " -i "
        + audio
        + " -vf \"scale=1920:1080,format=rgba,zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125,fade=t=in:st=0:d=1:alpha=1,fade=t=out:st=14:d=1:alpha=1,drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,0,7)',drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,8,14)',drawtext=text='The Roman Republic was known for its infrastructure':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,0,7)',drawtext=text='And It built roads, aqueducts, and public works':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,8,14)'\" -c:a copy -shortest -t 14 "
        + output + " ";

    ProcessBuilder pb = new ProcessBuilder(command);
    try {
      Process process = pb.start();
      process.waitFor();
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

}

