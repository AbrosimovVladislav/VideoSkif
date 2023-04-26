package com.videoskif.random;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomService {

  public int randomNumber(){
    return new Random().nextInt(10000000) + 1;
  }

}
