import React, { useState } from 'react';
import { HeartsBackground } from '../components/HeartsBackground';

interface LoginScreenProps {
  onProceed: () => void;
}

export const LoginScreen: React.FC<LoginScreenProps> = ({ onProceed }) => {
  const [isLoading, setIsLoading] = useState(false);

  const handleGoogleSignIn = () => {
    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
      onProceed();
    }, 1200);
  };

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-4 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-hidden">
      <HeartsBackground />

      <div className="relative z-10 w-full max-w-md mx-auto">
        <div
          className="rounded-[32px] p-8 sm:p-10 text-center glass-card"
          style={{
            background: 'rgba(255, 255, 255, 0.88)',
            border: '1.5px solid rgba(255, 255, 255, 0.8)',
            boxShadow: '0 20px 40px -15px rgba(255, 117, 143, 0.45)',
          }}
        >
          {/* Logo / Heart badge */}
          <div className="mx-auto mb-6 flex h-20 w-20 items-center justify-center rounded-full bg-[#FFE0E9] shadow-inner text-4xl animate-bounce">
            💖
          </div>

          <h1 className="font-pacifico text-3xl sm:text-4xl text-[#C9184A] leading-tight mb-3">
            Hello Anushka ❤️
          </h1>

          <p className="text-sm sm:text-base text-[#8B263E] font-medium leading-relaxed mb-8 max-w-xs mx-auto">
            A special little world crafted with love, just for you ✨
          </p>

          {isLoading ? (
            <div className="flex flex-col items-center justify-center py-6 gap-3">
              <div className="h-10 w-10 animate-spin rounded-full border-4 border-[#C9184A] border-t-transparent" />
              <p className="text-sm font-semibold text-[#C9184A] animate-pulse">
                Signing in as Anushka... 🌹
              </p>
            </div>
          ) : (
            <div className="flex flex-col gap-3.5">
              <button
                onClick={handleGoogleSignIn}
                className="w-full h-14 rounded-3xl bg-[#C9184A] hover:bg-[#b01440] active:scale-[0.98] text-white text-[17px] font-bold shadow-lg transition-all flex items-center justify-center gap-3 cursor-pointer"
                style={{
                  boxShadow: '0 8px 20px -4px rgba(201, 24, 74, 0.45)',
                }}
              >
                <svg className="h-5 w-5" viewBox="0 0 24 24">
                  <path
                    fill="#EA4335"
                    d="M12 5c1.6 0 3 .6 4.1 1.6l3.1-3.1C17.3 1.7 14.8 1 12 1 7.5 1 3.7 3.6 1.9 7.3l3.7 2.9C6.5 7.4 9 5 12 5z"
                  />
                  <path
                    fill="#4285F4"
                    d="M23.5 12.3c0-.8-.1-1.6-.2-2.3H12v4.5h6.5c-.3 1.5-1.1 2.8-2.4 3.7l3.7 2.9c2.2-2 3.7-5 3.7-8.8z"
                  />
                  <path
                    fill="#FBBC05"
                    d="M5.6 14.8c-.2-.7-.4-1.5-.4-2.3s.1-1.6.4-2.3L1.9 7.3C.7 9.7 0 12.3 0 15s.7 5.3 1.9 7.7l3.7-2.9z"
                  />
                  <path
                    fill="#34A853"
                    d="M12 23c3.2 0 6-1.1 8-3l-3.7-2.9c-1.1.7-2.5 1.2-4.3 1.2-3 0-5.5-2.4-6.4-5.6L1.9 15.6C3.7 19.3 7.5 23 12 23z"
                  />
                </svg>
                <span>Sign in with Google</span>
              </button>

              <button
                onClick={onProceed}
                className="w-full h-14 rounded-3xl border-2 border-[#FF758F] text-[#C9184A] bg-white/70 hover:bg-white active:scale-[0.98] text-[16px] font-semibold transition-all shadow-sm hover:shadow cursor-pointer"
              >
                Enter Directly 💖
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
