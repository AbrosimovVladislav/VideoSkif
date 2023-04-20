package com.videoskif.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DalleService {

  public Map<String, String> getPicturesByPhrases(List<String> phrases) {
    return phrases.stream()
        .map(e -> Pair.of(e, getPictureByPhrase(e)))
        .collect(Collectors.toMap(e -> e.getFirst(), e -> e.getSecond()));
  }

  //ToDo Implement
  private String getPictureByPhrase(String phrase) {

    return "https://oaidalleapiprodscus.blob.core.windows.net/private/org-oymrFb2GrdFZbEK4snFqDPoG/user-XPJIyq8TpKMoKahb9vectKGs/img-GuBWAVZwbp61opuqg2Yt3Tng.png?st=2023-04-20T13%3A48%3A11Z&se=2023-04-20T15%3A48%3A11Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-04-20T10%3A26%3A49Z&ske=2023-04-21T10%3A26%3A49Z&sks=b&skv=2021-08-06&sig=fQk45/xVgHZu4YeTVa14/HO0M0gjBVEwpNet/OOqN%2Bo%3D";
  }

}
