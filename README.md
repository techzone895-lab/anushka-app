# For Anushka ❤️ - Special Romantic App

A customized, modern, romantic web application created specially for **Anushka**, rewritten in React + TypeScript + Vite with Tailwind CSS.

---

## ✨ Features Included

1. **Romantic Greeting & Login Screen**:
   - Welcome card with "Hello Anushka ❤️"
   - Sign in with Google / Enter Directly options
   - Floating romantic hearts background

2. **Biometric & PIN Verification**:
   - "Anushka's Secret Space 🔐"
   - Interactive fingerprint sensor simulation with haptic feedback & bypass

3. **Immersive Video Intro**:
   - Cinematic Zyvraon intro video with audio & skip option

4. **Anushka Verification Question**:
   - "Kya aap sach me meri Anushka ho? 🙈❤️"
   - "Yes, tumhari Anushka! 🥰" / playful "Nahi, koi aur hoon 😜" response

5. **Birthday / Romantic Surprise Preparation**:
   - Floating balloons and emoji animation (`FloatingBalloons`)
   - Pulsing crown animation (`👑✨`)

6. **Animated Romantic Home Screen**:
   - Bouncing & pulsing "Anushka" in Pacifico typography
   - Sparkling Canvas overlay effect
   - Celebratory confetti showers
   - Glowing button with glitter sparkles

7. **My Angel Screen**:
   - Featured portrait (`anushka_photo.jpg`) in a framed glassmorphic card
   - Dedicated romantic melody (`bg_audio.mp3`)

8. **Photo Memories Gallery**:
   - 2-column memory photo grid (`anushka_1.jpg` to `anushka_12.jpg`)
   - Fullscreen lightbox viewer with next/prev navigation
   - Background music (`new_song.mp3`)

9. **Heartfelt Secret Letter**:
   - Personal letter text with authentic romantic message
   - Dedicated letter music (`messeage_music.mp3`)

10. **Audio Player**:
    - Floating background music control FAB (mute/unmute) with current track indicators

---

## 📸 How to Add / Replace Photos for Anushka

Add or replace photos inside:
`public/images/`

- **Featured Photo:** `public/images/anushka_photo.jpg`
- **Gallery Photos:** `public/images/anushka_1.jpg`, `anushka_2.jpg`, etc.

---

## 🚀 Running & Building

### 📱 Android Release APK Build

1. **Via GitHub Actions (Automated):**
   - Push your code to GitHub or trigger the **Build Anushka App Release APK** workflow from the Actions tab.
   - It will compile `./gradlew assembleRelease` using JDK 21 and the signed keystore.
   - Download the generated signed APK artifact: `For-Anushka-App-Release-APK` (`app-release.apk`).

2. **Locally in terminal / Android Studio:**
   ```bash
   # Decode keystore if not already present
   base64 -d debug.keystore.base64 > debug.keystore

   # Build signed release APK
   ./gradlew assembleRelease
   ```
   The generated APK will be at:
   `app/build/outputs/apk/release/app-release.apk`

---

### 🌐 Web (React + Vite)
```bash
# Start Vite development server on port 3000
npm run dev

# Build web distribution
npm run build
```
