package com.videoskif.service;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PhraseCombinator {

  public Map.Entry<String, String> twoPartsPhrase(String phrase) {
    String[] phrases = phrase.split("AND");
    return new SimpleEntry<>(phrases[0], phrases[1]);
  }

}
