package com.videoskif.image.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DalleResponse {
  private Integer status;
  private String headers;
  private DalleResponseBody body;
}
