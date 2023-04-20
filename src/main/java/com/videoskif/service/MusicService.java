package com.videoskif.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {

  //ToDo Implement
  public String getRandomMusicByTopicName(String topicName){

    return "https://creators.aiva.ai/publicPlayer?c=64415e2c5ff7d4001c007b38";
  }

}
