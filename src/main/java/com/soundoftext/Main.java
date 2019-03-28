package com.soundoftext;

public class Main {

  public static void main(String[] args) {
    SoundOfTextService soundOfTextService = new SoundOfTextService();
    soundOfTextService.create("Sound of Text.com", "en-US")
        .ifPresent(System.out::println);
  }

}
