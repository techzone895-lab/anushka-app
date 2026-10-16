import React, { useEffect } from 'react';
import confetti from 'canvas-confetti';
import { FloatingBalloons } from '../components/FloatingBalloons';
import { SparklesOverlay } from '../components/SparklesOverlay';

interface BirthdaySurpriseScreenProps {
  onProceed: () => void;
}

export const BirthdaySurpriseScreen: React.FC<BirthdaySurpriseScreenProps> = ({ onProceed }) => {
  useEffect(() => {
    confetti({
      particleCount: 100,
      spread: 90,
      origin: { y: 0.3 },
      colors: ['#FF758F', '#FFD166', '#FF4D6D', '#FFCCD5', '#C9184A'],
    });

    const timer = setTimeout(() => {
      onProceed();
    }, 4500);

    return () => clearTimeout(timer);
  }, [onProceed]);

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-6 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-hidden text-center">
      <FloatingBalloons />
      <SparklesOverlay />

      <div className="relative z-10 w-full max-w-md rounded-[32px] p-8 sm:p-10 glass-card border border-white/80 shadow-2xl">
        <div className="text-6xl mb-4 animate-crown-bounce select-none">
          👑✨
        </div>

        <h2 className="text-2xl sm:text-3xl font-bold text-[#C9184A] mb-4 leading-snug">
          Yay! Welcome to your special world, Anushka! 💖
        </h2>

        <p className="text-sm sm:text-base text-[#8B263E] mb-8 font-medium leading-relaxed">
          You bring so much joy & love into my life! Getting your surprise ready... 🌹
        </p>

        <button
          onClick={onProceed}
          className="w-full py-4 px-6 rounded-full bg-[#C9184A] hover:bg-[#b01440] active:scale-95 text-white font-bold text-base shadow-lg transition-all cursor-pointer"
        >
          Open My Surprise 🎁
        </button>
      </div>
    </div>
  );
};
