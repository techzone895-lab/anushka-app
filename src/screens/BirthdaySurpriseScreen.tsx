import React, { useEffect, useState } from 'react';
import { FloatingBalloons } from '../components/FloatingBalloons';
import { SparklesOverlay } from '../components/SparklesOverlay';
import { useMusic } from '../context/MusicContext';

interface BirthdaySurpriseScreenProps {
  onProceed: () => void;
}

export const BirthdaySurpriseScreen: React.FC<BirthdaySurpriseScreenProps> = ({ onProceed }) => {
  const { playTrack } = useMusic();
  const [countdown, setCountdown] = useState(4);

  useEffect(() => {
    playTrack('/audio/dummy_bgm.mp3', 'Anushka BGM 🎵');

    const interval = setInterval(() => {
      setCountdown((prev) => {
        if (prev <= 1) {
          clearInterval(interval);
          onProceed();
          return 0;
        }
        return prev - 1;
      });
    }, 1100);

    return () => clearInterval(interval);
  }, [onProceed, playTrack]);

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-4 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-hidden">
      <FloatingBalloons />
      <SparklesOverlay />

      <div className="relative z-10 w-full max-w-md mx-auto">
        <div
          className="rounded-[32px] p-8 sm:p-10 text-center glass-card"
          style={{
            background: 'rgba(255, 255, 255, 0.88)',
            border: '1.5px solid rgba(255, 255, 255, 0.8)',
            boxShadow: '0 20px 40px -15px rgba(255, 117, 143, 0.45)',
          }}
        >
          {/* Pulsing Crown */}
          <div className="mb-6 text-6xl sm:text-7xl animate-crown-bounce select-none">
            👑✨
          </div>

          <h2 className="font-pacifico text-2xl sm:text-3xl text-[#C9184A] leading-snug mb-4">
            Yay! Welcome to your special world, Anushka! 💖
          </h2>

          <p className="text-base sm:text-lg text-[#8B263E] font-medium leading-relaxed mb-6">
            You bring so much joy & love into my life! Getting your surprise ready... 🌹
          </p>

          <div className="flex flex-col items-center gap-3">
            <div className="h-2 w-48 overflow-hidden rounded-full bg-pink-100">
              <div
                className="h-full bg-gradient-to-r from-[#FF758F] to-[#C9184A] transition-all duration-1000 ease-linear"
                style={{ width: `${((4 - countdown) / 4) * 100}%` }}
              />
            </div>

            <button
              onClick={onProceed}
              className="mt-3 text-xs uppercase tracking-widest font-bold text-[#C9184A] hover:underline cursor-pointer"
            >
              Open Surprise Now ✨
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
