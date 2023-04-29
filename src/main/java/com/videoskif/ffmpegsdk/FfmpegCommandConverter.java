package com.videoskif.ffmpegsdk;

import com.videoskif.ffmpegsdk.model.FfmpegCommand;
import org.springframework.stereotype.Component;

@Component
public class FfmpegCommandConverter {

  public String[] getCommand(FfmpegCommand ffmpegCommand) {

    String ffmpegStart = ffmpegCommand.getFfmpegStart();
    String image = ffmpegCommand.getImages().get(0);
    String music = ffmpegCommand.getSounds().get(0);
    String phraseSound = ffmpegCommand.getSounds().get(1);
    String firstPhrase = ffmpegCommand.getPhrases().get(0);
    String secondPhrase = ffmpegCommand.getPhrases().get(1);
    String additionalPhrase = ffmpegCommand.getPhrases().get(2);
    String zoomOptions = ffmpegCommand.getZoomOptions();
    String outputPath = ffmpegCommand.getOutputPath();
    String drawOptions = ffmpegCommand.getDrawOptions();


    return new String[]{ffmpegStart, "-loop", "1", "-i", image,
        "-i", music,
        "-i", phraseSound,
        "-filter_complex", "amerge=inputs=2",
        "-vf",
        zoomOptions
            + drawOptions
            + firstPhrase
            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,0,4)',drawtext=text='"
            + secondPhrase
            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,5,8)',drawtext=text='"
            + additionalPhrase
            + "':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,8,15)'",
        "-shortest",
        "-t", "14", outputPath};
  }

}
