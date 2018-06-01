# Project 4 Android Example

This is an example Android project to demonstrate how to:

- Change between Android Activities
- Make RESTful HTTPS requests
- Maintain cookies and sessions
- [Google SafetyNet reCAPTCHA API](https://developer.android.com/training/safetynet/recaptcha)

## Running this project

This Android app depends on [project4-login-example](https://github.com/UCI-Chenli-teaching/project4-login-example) as the backend server to work.

1. Import this project to Android Studio
2. Setup Android Emulator / real Android Device to debug (https://developer.android.com/training/basics/firstapp/running-app)
3. Build and run this project (Make sure `project4-login-example` is up and running)

If you're running an Android Emulator in Android Studio, you can use the IP `10.0.2.2` to reference your host machine. Otherwise, if you're running this app on a real Android Device, you might need to change the urls in `RedActivity` to point to your backend server.

## Note on reCAPTCHA on Android
- You'll need to register a new `site-key` and `secret-key` pair on [reCAPCHA's admin area](https://www.google.com/recaptcha/admin) **in addition to** the one that already exist for the web version of Fabflix.
  1. Register a new site
    - Choose reCAPTCHA Android
    - Package Names
      - Specify the android app's package name. In `project4-android-example`, the package name is `edu.uci.ics.fabflixmobile`.
  2. Replace the `your-site-key` in `RedActivity`, and replace the `secret-key` variable on the server side (project4-login-example).
- Due to the limitation of Google reCAPTCHA, if you need to support both versions (web, and Android) of reCAPTCHA, you'll need to use different key pairs when verifying the client's reCAPTCHA response with Google. You should handle this logic in your `LoginServlet` such that if the request is from an Android client, use `x` as the secret key, and if it's a request from the web interface, use `y` as the secret instead.
