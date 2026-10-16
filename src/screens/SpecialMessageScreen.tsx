import React, { useEffect } from 'react';
import confetti from 'canvas-confetti';
import { ArrowLeft, Heart } from 'lucide-react';
import { useMusic } from '../context/MusicContext';
import { SparklesOverlay } from '../components/SparklesOverlay';

interface SpecialMessageScreenProps {
  onBack: () => void;
}

export const SpecialMessageScreen: React.FC<SpecialMessageScreenProps> = ({ onBack }) => {
  const { playTrack } = useMusic();

  useEffect(() => {
    playTrack('/audio/messeage_music.mp3', 'A Letter For You 💌');

    confetti({
      particleCount: 50,
      spread: 60,
      origin: { y: 0.25 },
      colors: ['#FF758F', '#C9184A', '#FFCCD5', '#590D22'],
    });
  }, [playTrack]);

  return (
    <div className="relative min-h-screen w-full flex flex-col bg-[#FFF0F5] pb-16 text-[#4A0E17] overflow-y-auto">
      <SparklesOverlay />

      <header className="sticky top-0 z-30 flex items-center justify-between bg-[#FFF0F5]/90 px-4 pt-10 pb-3 backdrop-blur-md border-b border-pink-200/50">
        <div className="flex items-center gap-3">
          <button
            onClick={onBack}
            className="flex h-11 w-11 shrink-0 items-center justify-center rounded-full bg-white text-[#C9184A] shadow-sm transition-transform hover:scale-105 active:scale-95 cursor-pointer"
            aria-label="Back"
          >
            <ArrowLeft className="h-5 w-5 stroke-[2.5]" />
          </button>

          <h1 className="text-xl sm:text-2xl font-bold text-[#C9184A] leading-tight">
            A Letter For You 💌
          </h1>
        </div>

        <span className="text-2xl select-none pr-1">🌹</span>
      </header>

      <main className="relative z-10 flex-1 p-4 sm:p-6 max-w-2xl mx-auto w-full my-4">
        <div
          className="rounded-[28px] bg-white p-7 sm:p-10 shadow-xl border border-pink-100"
          style={{
            boxShadow: '0 20px 40px -15px rgba(255, 117, 143, 0.35)',
          }}
        >
          <div className="text-center mb-6">
            <h2 className="font-pacifico text-2xl sm:text-3xl text-[#C9184A] mb-1">
              My Dearest Anushka ❤️
            </h2>
            <div className="flex items-center justify-center gap-1.5 text-pink-400 mt-2">
              <span className="h-px w-8 bg-pink-300" />
              <Heart className="h-3.5 w-3.5 fill-pink-400 text-pink-400" />
              <span className="h-px w-8 bg-pink-300" />
            </div>
          </div>

          <div className="space-y-4 text-base sm:text-[17px] text-[#4A0E17] leading-relaxed font-normal">
            <p>
              Pata nahi kaise shuru karoon... Jab se tum meri life mein aayi ho, sab kuch aur bhi khoobsurat aur pyara lagne laga hai.
            </p>

            <p>
              Tumhari choti choti baatein, tumhari cute si smile, aur tumhara mere saath hona hi mera poora din bana deta hai. Jab bhi tumse baat hoti hai, aisa lagta hai waqt wahin tham jaye.
            </p>

            <p>
              Mujhe bohot badi badi baatein karna toh nahi aata, par itna dil se keh sakta hoon ki tum mere liye bohot zyada special ho.
            </p>

            <p>
              Maine ye chota sa app sirf tumhare liye banaya hai — taaki jab bhi tum ise dekho, tumhare chehre par ek pyaari si muskaan aa jaye! 😊✨
            </p>

            <p>
              Har ek lamha jo tumhare sath bitaya hai, wo mere liye ek blessing hai. I promise to always cherish you, make you laugh, and stand by you.
            </p>

            <p className="font-medium text-[#8B263E]">
              Thank you for being in my life and making it so beautiful, Anushka! 🌹
            </p>

            <div className="pt-4 text-right">
              <p className="font-semibold text-[#8B263E]">Forever Yours,</p>
              <p className="font-pacifico text-xl text-[#C9184A] mt-1">With all my love ❤️</p>
            </div>
          </div>

          <div className="my-8 flex justify-center">
            <div className="h-1 w-16 rounded-full bg-[#FF758F]" />
          </div>

          <p className="text-center font-serif text-sm sm:text-base italic text-[#C9184A]">
            "You are my favorite feeling ✨"
          </p>
        </div>
      </main>
    </div>
  );
};
