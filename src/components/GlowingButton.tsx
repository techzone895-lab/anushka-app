import React, { useEffect, useState } from 'react';
import Lottie from 'lottie-react';

interface GlowingButtonProps {
  onClick: () => void;
  children: React.ReactNode;
  className?: string;
  disabled?: boolean;
}

export const GlowingButton: React.FC<GlowingButtonProps> = ({
  onClick,
  children,
  className = '',
  disabled = false,
}) => {
  const [glitterData, setGlitterData] = useState<any>(null);

  useEffect(() => {
    fetch('/assets/button_glitter_blink.json')
      .then((res) => res.json())
      .then((data) => setGlitterData(data))
      .catch(() => {
        // Silent fallback to pure CSS shimmer
      });
  }, []);

  return (
    <div className={`relative inline-block ${className}`}>
      <button
        onClick={onClick}
        disabled={disabled}
        className={`group relative flex items-center justify-center gap-2 overflow-hidden rounded-full bg-gradient-to-r from-[#C9184A] to-[#590D22] px-7 py-3.5 text-base font-bold text-white shadow-xl transition-all duration-300 hover:scale-105 hover:shadow-[0_10px_25px_rgba(201,24,74,0.5)] active:scale-95 disabled:pointer-events-none disabled:opacity-60 cursor-pointer ${
          !disabled ? 'animate-[pulse_2.2s_ease-in-out_infinite]' : ''
        }`}
        style={{
          boxShadow: '0 10px 25px -4px rgba(201, 24, 74, 0.45), 0 4px 10px -2px rgba(201, 24, 74, 0.25)',
        }}
      >
        <span className="relative z-10 flex items-center gap-2">
          {children}
        </span>

        {/* Shimmer overlay */}
        <div className="absolute inset-0 -translate-x-full bg-gradient-to-r from-transparent via-white/20 to-transparent transition-transform duration-1000 group-hover:translate-x-full" />

        {/* Glitter Lottie overlay if available */}
        {glitterData && (
          <div className="pointer-events-none absolute inset-0 z-0 opacity-40 mix-blend-screen">
            <Lottie
              animationData={glitterData}
              loop={true}
              autoplay={true}
              className="h-full w-full object-cover"
            />
          </div>
        )}
      </button>
    </div>
  );
};
