import React, { useMemo } from 'react';

const EMOJIS = ['💖', '💕', '✨', '🌸', '🌹', '🎈', '🥰', '💌'];

export const FloatingBalloons: React.FC = () => {
  const balloons = useMemo(() => {
    return Array.from({ length: 22 }).map((_, i) => ({
      id: i,
      emoji: EMOJIS[Math.floor(Math.random() * EMOJIS.length)],
      left: `${Math.random() * 94}%`,
      delay: `${Math.random() * 5}s`,
      duration: `${6 + Math.random() * 8}s`,
      size: `${28 + Math.random() * 32}px`,
    }));
  }, []);

  return (
    <div className="pointer-events-none fixed inset-0 z-0 overflow-hidden">
      {balloons.map((b) => (
        <span
          key={b.id}
          className="absolute select-none opacity-80 animate-balloon"
          style={{
            left: b.left,
            fontSize: b.size,
            animationDuration: b.duration,
            animationDelay: b.delay,
            animationIterationCount: 'infinite',
            animationTimingFunction: 'linear',
            top: '100%',
          }}
        >
          {b.emoji}
        </span>
      ))}
      <style>{`
        @keyframes balloonFloat {
          0% {
            transform: translateY(0) rotate(0deg);
            opacity: 0;
          }
          10% {
            opacity: 0.85;
          }
          90% {
            opacity: 0.85;
          }
          100% {
            transform: translateY(-120vh) rotate(20deg);
            opacity: 0;
          }
        }
        .animate-balloon {
          animation-name: balloonFloat;
        }
      `}</style>
    </div>
  );
};
