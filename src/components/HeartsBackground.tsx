import React, { useEffect, useState } from 'react';
import Lottie from 'lottie-react';

export const HeartsBackground: React.FC = () => {
  const [animationData, setAnimationData] = useState<any>(null);

  useEffect(() => {
    fetch('/assets/hearts.json')
      .then((res) => res.json())
      .then((data) => setAnimationData(data))
      .catch((err) => {
        console.warn('Lottie hearts fetch failed, using fallback CSS hearts', err);
      });
  }, []);

  return (
    <div className="pointer-events-none fixed inset-0 z-0 flex items-center justify-center overflow-hidden opacity-60">
      {animationData ? (
        <Lottie
          animationData={animationData}
          loop={true}
          autoplay={true}
          className="h-full w-full object-cover"
        />
      ) : (
        <div className="absolute inset-0 flex items-center justify-center">
          <div className="relative h-full w-full">
            {Array.from({ length: 15 }).map((_, i) => (
              <span
                key={i}
                className="absolute text-3xl opacity-75 animate-bounce"
                style={{
                  left: `${(i * 19) % 95}%`,
                  top: `${(i * 23) % 90}%`,
                  animationDuration: `${2 + (i % 3)}s`,
                }}
              >
                💕
              </span>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};
