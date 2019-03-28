package com.soundoftext;

public class SoundOfTextSuccessResponse implements SoundOfTextResponse {

  private boolean success;
  private String id;

  public SoundOfTextSuccessResponse() {
  }

  public SoundOfTextSuccessResponse(boolean success, String id) {
    this.success = success;
    this.id = id;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "com.soundoftext.SoundOfTextSuccessResponse{" +
        "success=" + success +
        ", id='" + id + '\'' +
        '}';
  }
}
