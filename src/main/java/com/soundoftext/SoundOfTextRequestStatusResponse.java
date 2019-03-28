package com.soundoftext;

public class SoundOfTextRequestStatusResponse {

  private SoundOfTextStatus status;
  private String message;
  private String location;

  public SoundOfTextRequestStatusResponse() {
  }

  public SoundOfTextRequestStatusResponse(SoundOfTextStatus status, String message, String location) {
    this.status = status;
    this.message = message;
    this.location = location;
  }

  public SoundOfTextStatus getStatus() {
    return status;
  }

  public void setStatus(SoundOfTextStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "com.soundoftext.SoundOfTextRequestStatusResponse{" +
        "status=" + status +
        ", message='" + message + '\'' +
        ", location='" + location + '\'' +
        '}';
  }
}
