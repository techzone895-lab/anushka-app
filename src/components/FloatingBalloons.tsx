import React, { useMemo } from 'react';

interface BalloonItem {
  id: number;
  emoji: string;
  leftPercent: number;
  duration: number;
  delay: number;
  wobbleDuration: number;
  wobbleAmount: number;
  size: number;
}

export const FloatingBalloons: React.FC = () => {
  const emojis = ['💖', '💕', '✨', '🌸', '🌹', '🎈', '🥰', '💌'];

  const balloons: BalloonItem[] = useMemo(() => {
    return Array.from({ length: 25 }, (_, i) => ({
      id: i,
      emoji: emojis[Math.floor(Math.random() * emojis.length)],
      leftPercent: 4 + Math.random() * 90,
      duration: 5 + Math.random() * 6, // 5s to 11s
      delay: Math.random() * 5, // 0 to 5s
      wobbleDuration: 2 + Math.random() * 2.5, // 2s to 4.5s
      wobbleAmount: 12 + Math.random() * 26, // 12px to 38px
      size: 26 + Math.floor(Math.random() * 28), // 26px to 54px
    }));
  }, []);

  return (
    <div className="pointer-events-none fixed inset-0 z-0 overflow-hidden">
      {balloons.map((b) => (
        <div
          key={b.id}
          className="absolute select-none opacity-80"
          style={{
            left: `${b.leftPercent}%`,
            bottom: '-100px',
            fontSize: `${b.size}px`,
            animation: `balloonFloat ${b.duration}s linear infinite ${b.delay}s`,
          }}
        >
          <div
            style={{
              animation: `balloonWobble ${b.wobbleDuration}s ease-in-out infinite alternate`,
              transform: `translateX(${b.wobbleAmount}px)`,
            }}
          >
            {b.emoji}
          </div>
        </div>
      ))}
      <style>{`
        @keyframes balloonFloat {
          0% {
            transform: translateY(0);
            opacity: 0;
          }
          10% {
            opacity: 0.85;
          }
          90% {
            opacity: 0.85;
          }
          100% {
            transform: translateY(-120vh);
            opacity: 0;
          }
        }
        @keyframes balloonWobble {
          0% {
            transform: translateX(-18px) rotate(-6deg);
          }
          100% {
            transform: translateX(18px) rotate(6deg);
          }
        }
      `}</style>
    </div>
  );
};
