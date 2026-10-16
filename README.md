# For Anushka ❤️ - Special Romantic Android App

A customized, modern, romantic native Android application created specially for **Anushka**, built with **Kotlin**, **Jetpack Compose**, and **Material 3**.

---

## ✨ Features Included

1. **Romantic Greeting & Login Screen**:
   - Welcome card with "Hello Anushka ❤️"
   - Sign in with Google / Enter Directly options
   - Floating romantic hearts Lottie animation and sparkles overlay

2. **Biometric & PIN Verification**:
   - "Anushka's Secret Space 🔐"
   - Native Android `BiometricPrompt` supporting fingerprint and device PIN credentials with graceful skip fallback

3. **Immersive Video Intro**:
   - Cinematic intro video (`zyvraon_intro.mp4`) with smooth audio and skip option

4. **Anushka Verification Question**:
   - "Kya aap sach me meri Anushka ho? 🙈❤️"
   - "Yes, tumhari Anushka! 🥰" / playful "Nahi, koi aur hoon 😜" response with cute dialog

5. **Birthday / Romantic Surprise Preparation**:
   - Floating balloons and emoji animation (`FloatingBalloonsAnimation`)
   - Pulsing crown animation (`👑✨`)
   - Celebratory confetti showers

6. **Animated Romantic Home Screen**:
   - Animated cursive "Anushka" in Pacifico typography
   - Sparkling Canvas overlay effect
   - Celebratory confetti showers
   - Glowing button with glitter sparkles

7. **My Angel Screen**:
   - Featured portrait (`anushka_photo.jpg`) in a framed romantic card with soft glow
   - Dedicated romantic melody (`bg_audio.mp3`)

8. **Photo Memories Gallery**:
   - 2-column memory photo grid with all 13 photos (`anushka_1.jpg` to `anushka_13.jpg`)
   - Captions and romantic badges on each photo
   - Fullscreen modal photo viewer with next/prev navigation, touch gestures, and memory counter
   - Background music (`new_song.mp3`)

9. **Heartfelt Secret Letter**:
   - Personal letter with authentic romantic Hindi message
   - Pacifico and Serif typography, custom dividers, and confetti
   - Dedicated letter music (`messeage_music.mp3`)

10. **Audio Player**:
    - Floating background music control FAB (mute/unmute) with synchronized playback across routes

---

## 📸 Photo & Audio Assets

All media assets are located in the Android resource directories:

- **Featured Photo & Gallery:** `app/src/main/res/drawable/anushka_photo.jpg`, `anushka_1.jpg` … `anushka_13.jpg`
- **Audio & Video:** `app/src/main/res/raw/` (`bg_audio.mp3`, `dummy_bgm.mp3`, `new_song.mp3`, `messeage_music.mp3`, `zyvraon_intro.mp4`)
- **Lottie Animations:** `app/src/main/assets/` (`hearts.json`, `button_glitter_blink.json`)
- **Fonts:** `app/src/main/res/font/pacifico.ttf`

---

## 🚀 Building & Testing

### Android Build
```bash
gradle assembleDebug
```
The compiled APK will be output at:
`app/build/outputs/apk/debug/app-debug.apk`

### Local JVM Unit & Robolectric Tests
```bash
gradle :app:testDebugUnitTest
```
