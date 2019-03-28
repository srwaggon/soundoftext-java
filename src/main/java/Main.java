import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Main {

  public static void main(String[] args) {
    System.out.println(requestSoundForText("Help me"));
  }

  private static Optional<SoundOfTextResponse> requestSoundForText(String text) {
    try {
      String baseUrl = "https://api.soundoftext.com";
      String soundsEndpoint = "/sounds";
      URL url = new URL(baseUrl + soundsEndpoint);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

      String body = createJsonForText(text);
      byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

      httpURLConnection.setRequestProperty("method", "POST");
      httpURLConnection.setRequestProperty("Content-Type", "application/json");
      httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bodyBytes.length));
      httpURLConnection.setDoOutput(true);
      httpURLConnection.getOutputStream().write(bodyBytes);

      Class<? extends SoundOfTextResponse> responseClass = getResponseClass(httpURLConnection);
      return readResponse(httpURLConnection, responseClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  private static Class<? extends SoundOfTextResponse> getResponseClass(
      HttpURLConnection httpURLConnection
  ) throws IOException {
    return httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK
        ? SoundOfTextSuccessResponse.class
        : SoundOfTextFailureResponse.class;
  }

  private static Optional<SoundOfTextResponse> readResponse(
      HttpURLConnection httpURLConnection,
      Class<? extends SoundOfTextResponse> responseClass
  ) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
    StringBuilder responseString = new StringBuilder();
    int c;
    while ((c = in.read()) >= 0) {
      responseString.append((char) c);
    }
    return Optional.of(new ObjectMapper().readValue(responseString.toString(), responseClass));
  }

  private static String createJsonForText(String text) {
    return createJsonForRequest(SoundOfTextRequest.newWithText(text));
  }

  private static String createJsonForRequest(SoundOfTextRequest properties) {
    try {
      return new ObjectMapper().writeValueAsString(properties);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }


}
