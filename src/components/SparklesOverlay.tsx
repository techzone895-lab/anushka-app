import React, { useEffect, useRef } from 'react';

interface Sparkle {
  x: number;
  y: number;
  size: number;
  phase: number;
  speed: number;
}

export const SparklesOverlay: React.FC = () => {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    let animationFrameId: number;
    let width = (canvas.width = window.innerWidth);
    let height = (canvas.height = window.innerHeight);

    const handleResize = () => {
      if (!canvas) return;
      width = canvas.width = window.innerWidth;
      height = canvas.height = window.innerHeight;
    };

    window.addEventListener('resize', handleResize);

    const sparklesCount = 40;
    const sparkles: Sparkle[] = Array.from({ length: sparklesCount }, () => ({
      x: Math.random(),
      y: Math.random(),
      size: Math.random() * 4 + 1.5,
      phase: Math.random() * 2 * Math.PI,
      speed: Math.random() * 1.5 + 1.0,
    }));

    let startTime = performance.now();

    const render = (now: number) => {
      ctx.clearRect(0, 0, width, height);
      const elapsed = (now - startTime) / 1000;
      const time = elapsed * 0.7;

      sparkles.forEach((sparkle) => {
        const rawAlpha = (Math.sin(time * sparkle.speed + sparkle.phase) + 1) / 2;
        const alpha = Math.max(0, Math.min(1, rawAlpha));
        const driftY = (sparkle.y * height - time * sparkle.speed * 20) % height;
        const finalY = driftY < 0 ? driftY + height : driftY;
        const finalX = sparkle.x * width;
        const radius = sparkle.size * (alpha * 0.5 + 0.5);

        ctx.save();
        ctx.beginPath();
        ctx.arc(finalX, finalY, radius, 0, 2 * Math.PI);
        ctx.fillStyle = `rgba(255, 255, 255, ${alpha * 0.8})`;
        ctx.shadowColor = '#FFCCD5';
        ctx.shadowBlur = radius * 3;
        ctx.fill();

        // Cross sparkle for larger particles
        if (sparkle.size > 3.5 && alpha > 0.5) {
          ctx.strokeStyle = `rgba(255, 255, 255, ${alpha * 0.6})`;
          ctx.lineWidth = 1;
          const arm = radius * 1.8;
          ctx.beginPath();
          ctx.moveTo(finalX - arm, finalY);
          ctx.lineTo(finalX + arm, finalY);
          ctx.moveTo(finalX, finalY - arm);
          ctx.lineTo(finalX, finalY + arm);
          ctx.stroke();
        }
        ctx.restore();
      });

      animationFrameId = requestAnimationFrame(render);
    };

    animationFrameId = requestAnimationFrame(render);

    return () => {
      cancelAnimationFrame(animationFrameId);
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  return (
    <canvas
      ref={canvasRef}
      className="pointer-events-none fixed inset-0 z-10 h-full w-full"
    />
  );
};
