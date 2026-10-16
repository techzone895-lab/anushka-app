import React, { useState } from 'react';
import { Fingerprint, Lock, ShieldCheck } from 'lucide-react';
import { HeartsBackground } from '../components/HeartsBackground';
import { SparklesOverlay } from '../components/SparklesOverlay';

interface BiometricScreenProps {
  onAuthenticated: () => void;
}

export const BiometricScreen: React.FC<BiometricScreenProps> = ({ onAuthenticated }) => {
  const [authenticating, setAuthenticating] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleFingerprint = () => {
    setAuthenticating(true);
    setTimeout(() => {
      setAuthenticating(false);
      setSuccess(true);
      setTimeout(() => {
        onAuthenticated();
      }, 600);
    }, 1000);
  };

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center p-6 bg-gradient-to-b from-[#FFF0F5] via-[#FFCCD5] to-[#FFF0F5] overflow-hidden">
      <HeartsBackground />
      <SparklesOverlay />

      <div className="relative z-10 w-full max-w-md rounded-[32px] p-8 sm:p-10 text-center glass-card border border-white/80 shadow-2xl">
        <div
          onClick={handleFingerprint}
          className={`mx-auto mb-6 flex h-28 w-28 items-center justify-center rounded-full transition-all duration-300 cursor-pointer ${
            success
              ? 'bg-emerald-100 scale-110 shadow-lg'
              : authenticating
              ? 'bg-pink-100 scale-105 shadow-xl ring-4 ring-pink-300 animate-pulse'
              : 'bg-[#FFE0E9] hover:scale-105 shadow-md active:scale-95'
          }`}
        >
          {success ? (
            <ShieldCheck className="h-14 w-14 text-emerald-600" />
          ) : (
            <Fingerprint
              className={`h-14 w-14 transition-colors ${
                authenticating ? 'text-[#C9184A]' : 'text-[#C9184A]'
              }`}
            />
          )}
        </div>

        <h2 className="text-2xl sm:text-3xl font-bold text-[#C9184A] mb-3">
          Anushka's Secret Space 🔐
        </h2>

        <p className="text-sm sm:text-base text-[#8B263E] mb-8 font-medium leading-relaxed">
          {success
            ? 'Access Granted! Welcome, beautiful ✨'
            : authenticating
            ? 'Verifying Anushka\'s fingerprint... 🌸'
            : 'Touch the sensor or use PIN to enter ✨'}
        </p>

        <div className="flex flex-col gap-3">
          <button
            onClick={handleFingerprint}
            disabled={authenticating || success}
            className="w-full py-4 px-6 rounded-full bg-[#C9184A] hover:bg-[#b01440] active:scale-95 text-white font-bold text-base shadow-lg transition-all cursor-pointer flex items-center justify-center gap-2"
          >
            <Lock className="h-4 w-4" />
            <span>{authenticating ? 'Scanning...' : 'Unlock with Fingerprint'}</span>
          </button>

          <button
            onClick={onAuthenticated}
            className="w-full py-3 px-6 rounded-full bg-white/70 hover:bg-white text-[#8B263E] font-medium text-sm border border-pink-200 transition-all cursor-pointer"
          >
            Skip / Continue Directly ✨
          </button>
        </div>
      </div>
    </div>
  );
};
