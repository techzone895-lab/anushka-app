import React, { useEffect, useState } from 'react';
import Lottie from 'lottie-react';

export const HeartsBackground: React.FC = () => {
  const [animationData, setAnimationData] = useState<any>(null);

  useEffect(() => {
    fetch('/assets/hearts.json')
      .then((res) => {
        if (!res.ok) throw new Error('Failed to load hearts lottie');
        return res.json();
      })
      .then((data) => setAnimationData(data))
      .catch(() => {});
  }, []);

  if (!animationData) return null;

  return (
    <div className="pointer-events-none fixed inset-0 z-0 opacity-40">
      <Lottie
        animationData={animationData}
        loop={true}
        style={{ width: '100%', height: '100%', objectFit: 'cover' }}
      />
    </div>
  );
};
