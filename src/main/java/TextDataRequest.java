public class TextDataRequest {

  private String text;
  private String voice;

  public TextDataRequest() {
  }

  public TextDataRequest(String text, String voice) {
    this.text = text;
    this.voice = voice;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getVoice() {
    return voice;
  }

  public void setVoice(String voice) {
    this.voice = voice;
  }

  @Override
  public String toString() {
    return "TextDataRequest{" +
        "text='" + text + '\'' +
        ", voice='" + voice + '\'' +
        '}';
  }
}
