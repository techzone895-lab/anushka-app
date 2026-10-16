import React, { useEffect, useState } from 'react';
import Lottie from 'lottie-react';

interface GlowingButtonProps {
  onClick: () => void;
  children: React.ReactNode;
  className?: string;
}

export const GlowingButton: React.FC<GlowingButtonProps> = ({
  onClick,
  children,
  className = '',
}) => {
  const [animationData, setAnimationData] = useState<any>(null);

  useEffect(() => {
    fetch('/assets/button_glitter_blink.json')
      .then((res) => {
        if (!res.ok) throw new Error('Failed to load lottie');
        return res.json();
      })
      .then((data) => setAnimationData(data))
      .catch(() => {});
  }, []);

  return (
    <div className="relative inline-block group">
      <button
        onClick={onClick}
        className={`relative z-10 flex items-center justify-center gap-2 px-8 py-4 rounded-full font-bold text-white text-base sm:text-lg shadow-xl transition-all duration-300 hover:scale-105 active:scale-95 cursor-pointer bg-gradient-to-r from-[#C9184A] via-[#FF4D6D] to-[#590D22] hover:shadow-[0_0_30px_rgba(201,24,74,0.6)] ${className}`}
        style={{
          boxShadow: '0 10px 30px -5px rgba(201, 24, 74, 0.55)',
        }}
      >
        {children}
      </button>

      {animationData && (
        <div className="pointer-events-none absolute inset-0 z-20 flex items-center justify-center overflow-hidden rounded-full scale-125">
          <Lottie
            animationData={animationData}
            loop={true}
            style={{ width: '100%', height: '100%' }}
          />
        </div>
      )}
    </div>
  );
};
