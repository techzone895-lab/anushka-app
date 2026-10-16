import React, { useRef, useEffect } from 'react';

interface SplashScreenProps {
  onComplete: () => void;
}

export const SplashScreen: React.FC<SplashScreenProps> = ({ onComplete }) => {
  const videoRef = useRef<HTMLVideoElement | null>(null);

  useEffect(() => {
    const video = videoRef.current;
    if (!video) return;

    video.play().catch((err) => {
      console.warn('Autoplay with sound failed, attempting muted video:', err);
      video.muted = true;
      video.play().catch(() => {
        // If video fails entirely, move to next screen after delay
        setTimeout(onComplete, 2000);
      });
    });
  }, [onComplete]);

  return (
    <div className="relative min-h-screen w-full bg-black flex items-center justify-center overflow-hidden">
      <video
        ref={videoRef}
        src="/video/zyvraon_intro.mp4"
        className="h-full w-full object-contain"
        playsInline
        autoPlay
        onEnded={onComplete}
        onError={() => {
          console.warn('Video failed, proceeding');
          onComplete();
        }}
      />

      {/* Skip button */}
      <button
        onClick={onComplete}
        className="absolute top-6 right-6 z-20 rounded-full bg-white/20 px-4 py-2 text-xs font-semibold uppercase tracking-wider text-white backdrop-blur-md transition-all hover:bg-white/30 active:scale-95 cursor-pointer"
      >
        Skip Intro ⏩
      </button>
    </div>
  );
};
