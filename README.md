# soundoftext-java

A Java library for [SoundOfText Api](https://soundoftext.com/docs) inspired by [soundoftext-js](https://github.com/ncpierson/soundoftext-js).

## Install

Install with **Gradle**:

```bash
# Using the Gradle Wrapper
$ gradlew plz
```

```bash
# Using a global installation of Gradle
$ gradle plz
```
or **maven**

```bash
$ mvn plz
```

## Usage

This library supports four operations: `create`, `location`, `request`, and `status`.

You will **most likely** want to use `create` as it has the simplest interface.

Start by instantiating an instance of the `SoundOfTextService` class.

```java
private void amazingFunction() {
  SoundOfTextService soundOfTextService = new SoundOfTextService();
  ...
}
```

### create

This function requests for a sound to be created, and polls the API until the sound is finished being created, eventually returning an Optional of a URL that links to the MP3 file.

This function takes two arguments:

- text (String) - the text to be spoken
- voice (String) - the voice (language + accent) to use

and returns an Optional of the url for an MP3 file that you could then download, and the empty Optional in the event of a failure or timeout.

```java
SoundOfTextService soundOfTextService = new SoundOfTextService();
String text = "soundoftext-java";
String voice = "en-US";

Optional<String> locationMaybe = soundOfTextService.create(text, voice);
locationMaybe.ifPresent(System.out::println);
```
### request

This function requests for a sound to be created and returns an object containing the sound id.

This function takes two arguments:

- text (String) - the text to be spoken
- voice (String) - the voice (language + accent) to use

and returns an Optional of a `SoundOfTextResponse`, either successful or failure, or the empty Optional in the case of an error.

`SoundOfTextResponse` is implemented by two classes: `SoundOfTextSuccessResponse` and `SoundOfTextFailureResponse`.

`SoundOfTextSuccessResponse` looks like this:

```javascript
{ success: true, id: '<sound-id>' }
```

`SoundOfTextFailureResponse` looks like this:
```javascript
{ success: false, message: '<error-message>' }
```

```java
SoundOfTextService soundOfTextService = new SoundOfTextService();
String text = "soundoftext-java";
String voice = "en-US";

Optional<SoundOfTextResponse> responseMaybe = soundofTextService.request(text, voice);
responseMaybe.ifPresent(System.out::println);
```

### status

This function takes a sound id and returns the current status.

This function takes one argument:

- id (String) - the id for the sound

and returns an Optional of a `SoundOfTextRequestStatusResponse`, or the empty Optional on an error.

`SoundOfTextRequestStatusResponse` looks like this:

```javascript
// one of
{ status: 'Error', message: '<error-message>', location: null }
{ status: 'Pending', message: null, location: null }
{ status: 'Done', message: null, location: '<url-for-mp3-file>' }
```

```java
SoundOfTextService soundOfTextService = new SoundOfTextService();
String text = "soundoftext-java";
String voice = "en-US";

Optional<SoundOfTextResponse> responseMaybe = soundOfTextService.request(text, voice);
responseMaybe.ifPresent(response -> {
  if (response.isSuccess()) {
    SoundOfTextSuccessResponse success = (SoundOfTextSuccessResponse) response;
    String id = success.getId();

    Optional<SoundOfTextRequestStatusResponse> statusMaybe = soundOfTextService.status(id);
    statusMaybe.ifPresent(System.out::println);
  }
});
```

### location

This is a convenience wrapper for `status`, which starts polling regularly for the status to be 'Done' before returning the url for the mp3 file. It throws a `java.util.concurrent.TimeoutException` if it times out (~60 seconds) and throws a `SoundOfTextException` if the API returns an 'Error' status.

This function takes one argument:

- id (String) - the id for the sound

and returns the url location of the mp3 file as a String.

```java
SoundOfTextService soundOfTextService = new SoundOfTextService();
String text = "soundoftext-java";
String voice = "en-US";

Optional<SoundOfTextResponse> responseMaybe = soundOfTextService.request(text, voice);
responseMaybe.ifPresent(response -> {
  if (response.isSuccess()) {
    SoundOfTextSuccessResponse success = (SoundOfTextSuccessResponse) response;
    String id = success.getId();

    Optional<String> locationMaybe = soundOfTextService.location(id);
    locationMaybe.ifPresent(System.out::println);
  }
})
```
## FAQ

**What voices does this support?**

You can find a list of language codes in the [documentation for Sound of Text](https://soundoftext.com/docs#voices).