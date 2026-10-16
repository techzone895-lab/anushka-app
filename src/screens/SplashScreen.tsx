import React, { useRef } from 'react';

interface SplashScreenProps {
  onComplete: () => void;
}

export const SplashScreen: React.FC<SplashScreenProps> = ({ onComplete }) => {
  const videoRef = useRef<HTMLVideoElement | null>(null);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black overflow-hidden">
      <video
        ref={videoRef}
        src="/video/zyvraon_intro.mp4"
        autoPlay
        playsInline
        onEnded={onComplete}
        onError={onComplete}
        className="h-full w-full object-cover"
      />

      <button
        onClick={onComplete}
        className="absolute bottom-8 right-8 z-50 rounded-full bg-white/20 px-6 py-2.5 text-sm font-semibold text-white backdrop-blur-md transition-all hover:bg-white/30 cursor-pointer"
      >
        Skip Intro ⏭️
      </button>
    </div>
  );
};
