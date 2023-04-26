package com.videoskif.image.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DalleResponseBody {
  private Long created;
  private List<String> data;
}
