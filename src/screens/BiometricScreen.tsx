import React, { useState } from 'react';
import { Fingerprint, Lock, ShieldCheck, Sparkles } from 'lucide-react';

interface BiometricScreenProps {
  onAuthenticated: () => void;
}

export const BiometricScreen: React.FC<BiometricScreenProps> = ({ onAuthenticated }) => {
  const [isScanning, setIsScanning] = useState(false);
  const [authSuccess, setAuthSuccess] = useState(false);

  const handleScan = () => {
    setIsScanning(true);
    setTimeout(() => {
      setIsScanning(false);
      setAuthSuccess(true);
      setTimeout(() => {
        onAuthenticated();
      }, 700);
    }, 1200);
  };

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-4 bg-[#0a0507] text-white overflow-hidden">
      {/* Ambient pink glowing orbs */}
      <div className="absolute top-1/4 -left-20 h-72 w-72 rounded-full bg-pink-600/20 blur-[100px]" />
      <div className="absolute bottom-1/4 -right-20 h-72 w-72 rounded-full bg-rose-600/20 blur-[100px]" />

      <div className="relative z-10 w-full max-w-sm rounded-[32px] bg-gradient-to-b from-[#1c0f16] to-[#12070e] p-8 border border-white/10 shadow-2xl text-center">
        <div className="mx-auto mb-6 flex h-16 w-16 items-center justify-center rounded-2xl bg-[#C9184A]/20 border border-[#C9184A]/40 text-[#FF758F]">
          {authSuccess ? (
            <ShieldCheck className="h-9 w-9 text-emerald-400 animate-scale-up" />
          ) : (
            <Lock className="h-8 w-8 text-[#FF758F]" />
          )}
        </div>

        <h2 className="text-2xl font-bold tracking-tight text-white mb-2 flex items-center justify-center gap-2">
          <span>Anushka's Secret Space</span>
          <span>🔐</span>
        </h2>

        <p className="text-sm text-pink-200/80 mb-8 leading-relaxed">
          Use your fingerprint or device PIN to enter ✨
        </p>

        {/* Biometric Sensor Area */}
        <div className="my-8 flex flex-col items-center justify-center">
          <button
            onClick={handleScan}
            disabled={isScanning || authSuccess}
            className={`group relative flex h-28 w-28 items-center justify-center rounded-full border-2 transition-all duration-300 cursor-pointer ${
              authSuccess
                ? 'border-emerald-400 bg-emerald-500/20 text-emerald-300'
                : isScanning
                ? 'border-pink-400 bg-pink-500/20 text-pink-300 scale-105 shadow-[0_0_30px_rgba(255,117,143,0.5)]'
                : 'border-pink-500/40 bg-pink-900/20 text-pink-400 hover:border-pink-400 hover:bg-pink-900/30 active:scale-95'
            }`}
          >
            {isScanning && (
              <span className="absolute inset-0 rounded-full border-2 border-pink-400 animate-ping opacity-60" />
            )}
            <Fingerprint className={`h-14 w-14 transition-transform duration-300 ${isScanning ? 'animate-pulse scale-110' : ''}`} />
          </button>

          <span className="mt-4 text-xs font-medium tracking-wide uppercase text-pink-300/80">
            {authSuccess
              ? 'Access Granted 💖'
              : isScanning
              ? 'Verifying Anushka...'
              : 'Touch Sensor to Unlock'}
          </span>
        </div>

        {/* Instant Unlock Button */}
        <div className="mt-6 flex flex-col gap-3">
          <button
            onClick={handleScan}
            disabled={isScanning || authSuccess}
            className="w-full h-12 rounded-2xl bg-gradient-to-r from-[#C9184A] to-[#FF4D6D] text-white text-sm font-semibold tracking-wide shadow-md transition-all hover:brightness-110 active:scale-95 flex items-center justify-center gap-2 cursor-pointer"
          >
            <Sparkles className="h-4 w-4" />
            <span>Verify Biometrics</span>
          </button>

          <button
            onClick={onAuthenticated}
            className="text-xs text-pink-300/70 hover:text-pink-200 transition-colors py-1 cursor-pointer"
          >
            Bypass / Skip verification
          </button>
        </div>
      </div>
    </div>
  );
};
