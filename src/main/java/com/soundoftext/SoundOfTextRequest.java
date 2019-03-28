package com.soundoftext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class SoundOfTextRequest {
  private String engine;
  private TextDataRequest data;

  SoundOfTextRequest(String engine, TextDataRequest data) {
    this.engine = engine;
    this.data = data;
  }

  static SoundOfTextRequest newWithText(String text) {
    return newWithTextAndVoice(text, "en-US");
  }

  static SoundOfTextRequest newWithTextAndVoice(String text, String voice) {
    TextDataRequest textDataRequest = new TextDataRequest(text, voice);
    return new SoundOfTextRequest("Google", textDataRequest);
  }

  String toJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getEngine() {
    return engine;
  }

  public void setEngine(String engine) {
    this.engine = engine;
  }

  public TextDataRequest getData() {
    return data;
  }

  public void setData(TextDataRequest data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "com.soundoftext.SoundOfTextRequest{" +
        "engine='" + engine + '\'' +
        ", data=" + data +
        '}';
  }
}
