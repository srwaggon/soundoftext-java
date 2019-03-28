# soundoftext-java

A Java library for [SoundOfText Api](https://soundoftext.com/docs) inspired by [soundoftext-js](https://github.com/ncpierson/soundoftext-js).

## Install

Install with **Gradle**:

```bash
# Using the Gradle Wrapper ()
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

```java
String text = "soundoftext-java";
String voice = "en-US";

Optional<String> locationMaybe = soundOfTextService.create(text, voice);
locationMaybe.ifPresent(System.out::println);
```

### location
```java
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

### request
```java

```

### status

```java

```
