import React, { useState } from 'react';
import { Sparkles, Heart } from 'lucide-react';
import { HeartsBackground } from '../components/HeartsBackground';
import { SparklesOverlay } from '../components/SparklesOverlay';

interface LoginScreenProps {
  onProceed: () => void;
}

export const LoginScreen: React.FC<LoginScreenProps> = ({ onProceed }) => {
  const [loading, setLoading] = useState(false);

  const handleSignIn = () => {
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
      onProceed();
    }, 700);
  };

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-6 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-hidden">
      <HeartsBackground />
      <SparklesOverlay />

      <div className="relative z-10 w-full max-w-md rounded-[32px] p-8 sm:p-10 text-center glass-card border border-white/80 shadow-2xl">
        <div className="mx-auto mb-6 flex h-20 w-20 items-center justify-center rounded-full bg-[#FFE0E9] shadow-inner">
          <Heart className="h-10 w-10 fill-[#C9184A] text-[#C9184A] animate-heart-pulse" />
        </div>

        <h1 className="font-pacifico text-4xl sm:text-5xl text-[#C9184A] mb-3">
          Hello Anushka ❤️
        </h1>

        <p className="text-sm sm:text-base text-[#8B263E] mb-8 font-medium leading-relaxed">
          A special little world crafted with love, just for you ✨
        </p>

        {loading ? (
          <div className="flex justify-center py-4">
            <div className="h-10 w-10 animate-spin rounded-full border-4 border-pink-200 border-t-[#C9184A]" />
          </div>
        ) : (
          <div className="flex flex-col gap-3.5">
            <button
              onClick={handleSignIn}
              className="w-full py-4 px-6 rounded-full bg-[#C9184A] hover:bg-[#b01440] active:scale-95 text-white font-bold text-base shadow-lg transition-all cursor-pointer flex items-center justify-center gap-2"
              style={{
                boxShadow: '0 8px 24px -4px rgba(201, 24, 74, 0.45)',
              }}
            >
              <span>Sign in with Google</span>
              <Sparkles className="h-4 w-4" />
            </button>

            <button
              onClick={onProceed}
              className="w-full py-3.5 px-6 rounded-full bg-white/80 hover:bg-white text-[#C9184A] font-semibold text-base border border-pink-200 shadow-sm transition-all hover:shadow active:scale-95 cursor-pointer"
            >
              Enter Directly 💖
            </button>
          </div>
        )}
      </div>
    </div>
  );
};
