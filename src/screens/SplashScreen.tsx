import React, { useEffect, useRef } from 'react';

interface SplashScreenProps {
  onComplete: () => void;
}

export const SplashScreen: React.FC<SplashScreenProps> = ({ onComplete }) => {
  const videoRef = useRef<HTMLVideoElement | null>(null);

  useEffect(() => {
    const video = videoRef.current;
    if (video) {
      video.muted = true; // Required for reliable autoplay across web and emulator webviews
      video.play().catch(() => {
        // If autoplay fails, fallback seamlessly
      });
    }

    // Safety timeout: if video fails to load, end, or stalls, advance after 4.5 seconds
    const timer = setTimeout(() => {
      onComplete();
    }, 4500);

    return () => clearTimeout(timer);
  }, [onComplete]);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black overflow-hidden">
      <video
        ref={videoRef}
        src="/video/zyvraon_intro.mp4"
        autoPlay
        playsInline
        muted
        onEnded={onComplete}
        onError={onComplete}
        className="h-full w-full object-cover"
      />

      <button
        onClick={onComplete}
        className="absolute bottom-8 right-8 z-50 rounded-full bg-white/25 hover:bg-white/40 active:scale-95 px-6 py-2.5 text-sm font-semibold text-white backdrop-blur-md transition-all cursor-pointer shadow-lg"
      >
        Skip Intro ⏭️
      </button>
    </div>
  );
};
