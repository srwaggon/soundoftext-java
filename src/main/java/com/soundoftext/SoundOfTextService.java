package com.soundoftext;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class SoundOfTextService {

  private static final String soundOfTextUrl = "https://api.soundoftext.com";
  private static final String soundsEndpoint = "/sounds";

  // region create
  public Optional<String> create(String text, String voice) {
    Optional<SoundOfTextResponse> request = request(text, voice);
    if (!request.isPresent()) {
      return Optional.empty();
    }

    SoundOfTextResponse response = request.get();

    if (!response.isSuccess()) {
      return Optional.empty();
    }

    SoundOfTextSuccessResponse success = (SoundOfTextSuccessResponse) response;
    String id = success.getId();

    try {
      return Optional.of(location(id));
    } catch (TimeoutException | SoundOfTextException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
  // endregion create

  // region request
  public Optional<SoundOfTextResponse> request(
      String text,
      String voice
  ) {
    try {
      HttpURLConnection requestConnection = newRequestConnection();
      String body = SoundOfTextRequest.newWithTextAndVoice(text, voice).toJson();
      byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
      requestConnection.setDoOutput(true);
      requestConnection.setRequestMethod("POST");
      requestConnection.setRequestProperty("Content-Type", "application/json");
      requestConnection.setRequestProperty("Content-Length", String.valueOf(bodyBytes.length));
      requestConnection.getOutputStream().write(bodyBytes);
      Class<? extends SoundOfTextResponse> responseClass = getResponseClass(requestConnection);
      return readRequestResponse(requestConnection, responseClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  private HttpURLConnection newRequestConnection() throws IOException {
    URL url = new URL(soundOfTextUrl + soundsEndpoint);
    return (HttpURLConnection) url.openConnection();
  }

  private Class<? extends SoundOfTextResponse> getResponseClass(
      HttpURLConnection httpURLConnection
  ) throws IOException {
    return httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK
        ? SoundOfTextSuccessResponse.class
        : SoundOfTextFailureResponse.class;
  }

  private Optional<SoundOfTextResponse> readRequestResponse(
      HttpURLConnection httpURLConnection,
      Class<? extends SoundOfTextResponse> responseClass
  ) throws IOException {
    String responseString = readResponse(httpURLConnection);
    SoundOfTextResponse response = new ObjectMapper().readValue(responseString, responseClass);
    return Optional.of(response);
  }

  // endregion request

  // region location
  public String location(
      String id
  ) throws TimeoutException, SoundOfTextException {
    return location(id, 1000);
  }

  public String location(
      String id,
      int timeout
  ) throws TimeoutException, SoundOfTextException {
    while (true) {
      Optional<SoundOfTextRequestStatusResponse> statusRequest = status(id);
      if (!statusRequest.isPresent()) {
        throw new SoundOfTextException("Failed while checking status of sound with id " + id);
      }
      SoundOfTextRequestStatusResponse response = statusRequest.get();
      SoundOfTextStatus status = response.getStatus();

      if (SoundOfTextStatus.Done.equals(status)) {
        return response.getLocation();
      }

      if (SoundOfTextStatus.Error.equals(status)) {
        throw new SoundOfTextException(response.getMessage());
      }

      if (timeout > 30 * 1000) {
        throw new TimeoutException();
      }

      timeout *= 2;
    }
  }
  // endregion location

  // region status
  public Optional<SoundOfTextRequestStatusResponse> status(
      String id
  ) {
    try {
      HttpURLConnection statusConnection = newStatusConnection(id);
      statusConnection.setDoOutput(true);
      statusConnection.setRequestMethod("GET");
      String responseString = readResponse(statusConnection);
      SoundOfTextRequestStatusResponse response = new ObjectMapper().readValue(responseString, SoundOfTextRequestStatusResponse.class);
      return Optional.of(response);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  private HttpURLConnection newStatusConnection(
      String id
  ) throws IOException {
    URL url = new URL(soundOfTextUrl + soundsEndpoint + "/" + id);
    return (HttpURLConnection) url.openConnection();
  }
  // endregion status

  private String readResponse(
      HttpURLConnection httpURLConnection
  ) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
    StringBuilder responseString = new StringBuilder();
    int c;
    while ((c = in.read()) >= 0) {
      responseString.append((char) c);
    }
    return responseString.toString();
  }

}
