package com.videoskif.music;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicService {

  //ToDo Implement
  public String getRandomMusicByTopicName(String topicName){
    String musicUrl = "https://gunmarket.fra1.digitaloceanspaces.com/Roman%20Republic%20SILENCE-2.mp3";
    log.info("Music saved to s3. Music Url:<{}>", musicUrl);
    return musicUrl;
  }

}
