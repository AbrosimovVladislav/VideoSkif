package com.videoskif.image.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DalleRequest {

  private String prompt;
  private Integer n;
  private String size;
}
