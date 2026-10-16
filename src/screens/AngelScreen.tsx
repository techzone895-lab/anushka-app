import React, { useEffect } from 'react';
import confetti from 'canvas-confetti';
import { ArrowLeft, Images } from 'lucide-react';
import { SparklesOverlay } from '../components/SparklesOverlay';
import { GlowingButton } from '../components/GlowingButton';
import { useMusic } from '../context/MusicContext';

interface AngelScreenProps {
  onBack: () => void;
  onNavigateToGallery: () => void;
}

export const AngelScreen: React.FC<AngelScreenProps> = ({ onBack, onNavigateToGallery }) => {
  const { playTrack } = useMusic();

  useEffect(() => {
    playTrack('/audio/bg_audio.mp3', 'Angel Melody 🕊️');

    confetti({
      particleCount: 60,
      spread: 70,
      origin: { y: 0.15 },
      colors: ['#FF758F', '#FFD166', '#FFCCD5', '#C9184A'],
    });
  }, [playTrack]);

  return (
    <div className="relative min-h-screen w-full flex flex-col items-center justify-between p-4 sm:p-6 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-y-auto">
      <SparklesOverlay />

      {/* Top Bar with Back Button */}
      <div className="relative z-20 w-full max-w-md flex items-center justify-between pt-2">
        <button
          onClick={onBack}
          className="flex h-11 w-11 items-center justify-center rounded-full bg-white/85 text-[#C9184A] shadow-md transition-all hover:bg-white hover:scale-105 active:scale-95 cursor-pointer"
          aria-label="Go back"
        >
          <ArrowLeft className="h-5 w-5" />
        </button>
        <span className="text-xl">✨</span>
      </div>

      {/* Main Content */}
      <div className="relative z-10 w-full max-w-md mx-auto my-auto flex flex-col items-center text-center py-4">
        <h2 className="font-pacifico text-3xl sm:text-4xl text-[#C9184A] mb-5">
          My Angel Anushka ✨
        </h2>

        {/* Featured Portrait of Anushka */}
        <div
          className="relative mb-6 w-full max-w-[280px] sm:max-w-[320px] rounded-[28px] overflow-hidden border-4 border-white shadow-2xl transition-transform duration-500 hover:scale-[1.02]"
          style={{
            boxShadow: '0 20px 40px -10px rgba(255, 117, 143, 0.45)',
          }}
        >
          <img
            src="/images/anushka_photo.jpg"
            alt="My Angel Anushka"
            className="w-full aspect-[3/4] object-cover bg-pink-100"
            onError={(e) => {
              // Fallback if image path has issue
              (e.currentTarget as HTMLImageElement).src = '/images/anushka_1.jpg';
            }}
          />
          <div className="absolute inset-0 bg-gradient-to-t from-black/20 via-transparent to-transparent pointer-events-none" />
        </div>

        {/* Romantic Message Card */}
        <div
          className="w-full rounded-[24px] p-5 sm:p-6 glass-card mb-6"
          style={{
            background: 'rgba(255, 255, 255, 0.88)',
            border: '1.5px solid rgba(255, 255, 255, 0.8)',
            boxShadow: '0 12px 28px -8px rgba(255, 117, 143, 0.3)',
          }}
        >
          <p className="text-sm sm:text-base text-[#4A0E17] font-semibold leading-relaxed">
            In a world full of ordinary things, you are my favorite masterpiece. Thank you for being in my life! 🌹❤️
          </p>
        </div>

        {/* Action Button */}
        <GlowingButton onClick={onNavigateToGallery}>
          <span>Our Memories & Gallery 📸</span>
          <Images className="h-5 w-5" />
        </GlowingButton>
      </div>

      <div className="h-6" />
    </div>
  );
};
