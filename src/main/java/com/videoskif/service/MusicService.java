package com.videoskif.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {

  //ToDo Implement
  public String getRandomMusicByTopicName(String topicName){

    return "https://gunmarket.fra1.digitaloceanspaces.com/Roman%20Republic.mp3";
  }

}
