import React, { useState } from 'react';
import { HeartsBackground } from '../components/HeartsBackground';
import { SparklesOverlay } from '../components/SparklesOverlay';

interface AnushkaCheckScreenProps {
  onYesClicked: () => void;
}

export const AnushkaCheckScreen: React.FC<AnushkaCheckScreenProps> = ({ onYesClicked }) => {
  const [showJoke, setShowJoke] = useState(false);

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-6 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-hidden">
      <HeartsBackground />
      <SparklesOverlay />

      <div className="relative z-10 w-full max-w-md rounded-[32px] p-8 sm:p-10 text-center glass-card border border-white/80 shadow-2xl">
        <div className="mx-auto mb-6 flex h-24 w-24 items-center justify-center rounded-full bg-[#FFE0E9] shadow-inner text-4xl animate-bounce">
          💖
        </div>

        <h2 className="text-2xl sm:text-3xl font-bold text-[#C9184A] mb-3 leading-snug">
          Kya aap sach me meri Anushka ho? 🙈❤️
        </h2>

        <p className="text-sm sm:text-base text-[#8B263E] mb-8 font-medium">
          A secret surprise is waiting only for the prettiest girl ✨
        </p>

        <div className="flex flex-col gap-3.5">
          <button
            onClick={onYesClicked}
            className="w-full py-4 px-6 rounded-full bg-[#C9184A] hover:bg-[#b01440] active:scale-95 text-white font-bold text-base sm:text-lg shadow-lg transition-all cursor-pointer"
            style={{
              boxShadow: '0 8px 24px -4px rgba(201, 24, 74, 0.45)',
            }}
          >
            Yes, tumhari Anushka! 🥰
          </button>

          <button
            onClick={() => setShowJoke(true)}
            className="w-full py-3.5 px-6 rounded-full bg-white/80 hover:bg-white text-[#C9184A] font-semibold text-base border border-pink-200 shadow-sm transition-all cursor-pointer"
          >
            Nahi, koi aur hoon 😜
          </button>
        </div>

        {showJoke && (
          <div className="mt-6 p-4 rounded-2xl bg-pink-100/80 border border-pink-200 text-sm text-[#8B263E] animate-fadeIn">
            <p className="font-semibold mb-1">Jhooth mat bolo! 😉</p>
            <p>Itni cute aur pyaari ladki toh bas meri Anushka hi hai! Jaldi se Yes dabao! 🌹❤️</p>
          </div>
        )}
      </div>
    </div>
  );
};
