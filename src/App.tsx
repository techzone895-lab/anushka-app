import React, { useState } from 'react';
import { MusicProvider } from './context/MusicContext';
import { LoginScreen } from './screens/LoginScreen';
import { BiometricScreen } from './screens/BiometricScreen';
import { SplashScreen } from './screens/SplashScreen';
import { AnushkaCheckScreen } from './screens/AnushkaCheckScreen';
import { BirthdaySurpriseScreen } from './screens/BirthdaySurpriseScreen';
import { HomeScreen } from './screens/HomeScreen';
import { AngelScreen } from './screens/AngelScreen';
import { GalleryScreen } from './screens/GalleryScreen';
import { SpecialMessageScreen } from './screens/SpecialMessageScreen';

type Screen =
  | 'login'
  | 'biometric'
  | 'splash'
  | 'check'
  | 'surprise'
  | 'home'
  | 'angel'
  | 'gallery'
  | 'letter';

export const AppContent: React.FC = () => {
  const [currentScreen, setCurrentScreen] = useState<Screen>('login');

  return (
    <div className="min-h-screen w-full relative">
      {currentScreen === 'login' && (
        <LoginScreen onProceed={() => setCurrentScreen('biometric')} />
      )}

      {currentScreen === 'biometric' && (
        <BiometricScreen onAuthenticated={() => setCurrentScreen('splash')} />
      )}

      {currentScreen === 'splash' && (
        <SplashScreen onComplete={() => setCurrentScreen('check')} />
      )}

      {currentScreen === 'check' && (
        <AnushkaCheckScreen onYesClicked={() => setCurrentScreen('surprise')} />
      )}

      {currentScreen === 'surprise' && (
        <BirthdaySurpriseScreen onProceed={() => setCurrentScreen('home')} />
      )}

      {currentScreen === 'home' && (
        <HomeScreen onNavigateToAngel={() => setCurrentScreen('angel')} />
      )}

      {currentScreen === 'angel' && (
        <AngelScreen
          onBack={() => setCurrentScreen('home')}
          onNavigateToGallery={() => setCurrentScreen('gallery')}
        />
      )}

      {currentScreen === 'gallery' && (
        <GalleryScreen
          onBack={() => setCurrentScreen('angel')}
          onNavigateToLetter={() => setCurrentScreen('letter')}
        />
      )}

      {currentScreen === 'letter' && (
        <SpecialMessageScreen onBack={() => setCurrentScreen('gallery')} />
      )}
    </div>
  );
};

export const App: React.FC = () => {
  return (
    <MusicProvider>
      <AppContent />
    </MusicProvider>
  );
};

export default App;
