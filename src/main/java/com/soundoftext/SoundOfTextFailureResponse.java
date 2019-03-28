package com.soundoftext;

public class SoundOfTextFailureResponse implements SoundOfTextResponse {

  private boolean success;
  private String message;

  public SoundOfTextFailureResponse() {
  }

  public SoundOfTextFailureResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "com.soundoftext.SoundOfTextFailureResponse{" +
        "success=" + success +
        ", message='" + message + '\'' +
        '}';
  }
}
