class SoundOfTextRequest {
  private String engine;
  private TextDataRequest data;

  SoundOfTextRequest(String engine, TextDataRequest data) {
    this.engine = engine;
    this.data = data;
  }

  static SoundOfTextRequest newWithText(String text) {
    TextDataRequest textDataRequest = new TextDataRequest(text, "en-US");
    return new SoundOfTextRequest("Google", textDataRequest);
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
}
