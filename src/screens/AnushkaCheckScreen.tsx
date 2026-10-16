import React, { useEffect, useState } from 'react';
import { HeartsBackground } from '../components/HeartsBackground';
import { useMusic } from '../context/MusicContext';

interface AnushkaCheckScreenProps {
  onYesClicked: () => void;
}

export const AnushkaCheckScreen: React.FC<AnushkaCheckScreenProps> = ({ onYesClicked }) => {
  const { playTrack } = useMusic();
  const [toastMessage, setToastMessage] = useState<string | null>(null);

  useEffect(() => {
    playTrack('/audio/dummy_bgm.mp3', 'Anushka BGM 🎵');
  }, [playTrack]);

  const handleNoClick = () => {
    setToastMessage('Arey itni pyaari aur khubsurat toh bas meri Anushka hi hai! Jhooth mat bolo! 😉❤️');
    setTimeout(() => {
      setToastMessage(null);
    }, 4500);
  };

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-4 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] via-[#FFD1DC] to-[#FFF0F5] overflow-hidden">
      <HeartsBackground />

      {/* Floating Toast Notification */}
      {toastMessage && (
        <div className="fixed top-8 z-50 max-w-sm mx-auto px-6 py-4 rounded-2xl bg-[#590D22] text-white text-sm font-medium shadow-2xl border border-pink-400/40 text-center animate-bounce">
          {toastMessage}
        </div>
      )}

      <div className="relative z-10 w-full max-w-md mx-auto">
        <div
          className="rounded-[32px] p-7 sm:p-9 text-center glass-card"
          style={{
            background: 'rgba(255, 255, 255, 0.88)',
            border: '1.5px solid rgba(255, 255, 255, 0.8)',
            boxShadow: '0 20px 40px -15px rgba(255, 117, 143, 0.45)',
          }}
        >
          {/* Pulsing Heart Circle */}
          <div className="mx-auto mb-6 flex h-20 w-20 items-center justify-center rounded-full bg-[#FFE0E9] shadow-inner text-4xl animate-heart-pulse">
            💖
          </div>

          <h2 className="font-pacifico text-2xl sm:text-3xl text-[#C9184A] leading-snug mb-3">
            Kya aap sach me meri Anushka ho? 🙈❤️
          </h2>

          <p className="text-sm sm:text-base text-[#8B263E] font-medium leading-relaxed mb-8 max-w-xs mx-auto">
            A secret surprise is waiting only for the prettiest girl ✨
          </p>

          <div className="flex flex-col gap-3.5">
            <button
              onClick={onYesClicked}
              className="w-full h-14 rounded-3xl bg-[#C9184A] hover:bg-[#b01440] active:scale-[0.98] text-white text-[17px] font-bold shadow-lg transition-all flex items-center justify-center gap-2 cursor-pointer"
              style={{
                boxShadow: '0 8px 20px -4px rgba(201, 24, 74, 0.45)',
              }}
            >
              <span>Yes, tumhari Anushka! 🥰</span>
            </button>

            <button
              onClick={handleNoClick}
              className="w-full h-14 rounded-3xl border-2 border-[#FF758F] text-[#C9184A] bg-white/70 hover:bg-white active:scale-[0.98] text-[16px] font-semibold transition-all shadow-sm hover:shadow cursor-pointer"
            >
              <span>Nahi, koi aur hoon 😜</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
