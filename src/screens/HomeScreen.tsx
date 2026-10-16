import React, { useEffect, useState } from 'react';
import confetti from 'canvas-confetti';
import { ArrowRight } from 'lucide-react';
import { HeartsBackground } from '../components/HeartsBackground';
import { FloatingBalloons } from '../components/FloatingBalloons';
import { SparklesOverlay } from '../components/SparklesOverlay';
import { GlowingButton } from '../components/GlowingButton';
import { useMusic } from '../context/MusicContext';

interface HomeScreenProps {
  onNavigateToAngel: () => void;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({ onNavigateToAngel }) => {
  const { playTrack } = useMusic();
  const [isLoaded, setIsLoaded] = useState(false);

  useEffect(() => {
    playTrack('/audio/dummy_bgm.mp3', 'Anushka BGM 🎵');

    // Trigger romantic confetti blast
    const timer = setTimeout(() => {
      setIsLoaded(true);
      confetti({
        particleCount: 80,
        spread: 80,
        origin: { y: 0.2 },
        colors: ['#FF758F', '#FFD166', '#FF4D6D', '#FFCCD5', '#C9184A'],
      });
    }, 400);

    return () => clearTimeout(timer);
  }, [playTrack]);

  return (
    <div className="relative min-h-screen w-full flex flex-col items-center justify-center p-6 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-hidden text-center">
      <HeartsBackground />
      <FloatingBalloons />
      <SparklesOverlay />

      <div className="relative z-10 w-full max-w-lg mx-auto flex flex-col items-center">
        {/* Animated Romantic Greeting Header */}
        <div className="mb-8 flex flex-col items-center">
          <span
            className={`text-sm sm:text-base font-extrabold uppercase tracking-[0.35em] text-[#C9184A] mb-2 transition-all duration-1000 ${
              isLoaded ? 'opacity-100 translate-y-0' : 'opacity-0 -translate-y-4'
            }`}
          >
            TO MY SPECIAL ONE ✨
          </span>

          <h1
            className={`font-pacifico text-6xl sm:text-7xl md:text-8xl text-[#590D22] drop-shadow-sm select-none transition-all duration-1000 delay-200 ${
              isLoaded ? 'scale-100 opacity-100' : 'scale-50 opacity-0'
            }`}
            style={{
              animation: isLoaded ? 'heartPulse 2.8s ease-in-out infinite' : undefined,
            }}
          >
            Anushka
          </h1>
        </div>

        {/* Romantic Quote Card */}
        <div
          className="w-full rounded-[28px] p-6 sm:p-7 glass-card mb-8 max-w-md mx-auto"
          style={{
            background: 'rgba(255, 255, 255, 0.88)',
            border: '1.5px solid rgba(255, 255, 255, 0.8)',
            boxShadow: '0 16px 36px -10px rgba(255, 117, 143, 0.35)',
          }}
        >
          <p className="text-base sm:text-lg text-[#8B263E] font-medium leading-relaxed">
            Every moment with you feels like a dream come true. You make my world infinitely brighter! 🌹✨
          </p>
        </div>

        {/* Glowing Action Button */}
        <GlowingButton onClick={onNavigateToAngel}>
          <span>See My Angel 🕊️💖</span>
          <ArrowRight className="h-5 w-5" />
        </GlowingButton>
      </div>
    </div>
  );
};
