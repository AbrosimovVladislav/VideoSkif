package com.videoskif.ffmpegsdk.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FfmpegCommand {

  private List<String> images;
  private List<String> sounds;
  private List<String> phrases;
  private String zoomOptions;
  private String drawOptions;
  private String outputPath;

}
