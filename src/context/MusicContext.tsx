import React, { createContext, useContext, useEffect, useRef, useState } from 'react';
import { Volume2, VolumeX } from 'lucide-react';

interface MusicContextType {
  isPlaying: boolean;
  isMuted: boolean;
  currentTrack: string;
  trackName: string;
  playTrack: (src: string, name?: string) => void;
  pauseMusic: () => void;
  toggleMute: () => void;
}

const MusicContext = createContext<MusicContextType | undefined>(undefined);

export const MusicProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const [isPlaying, setIsPlaying] = useState<boolean>(false);
  const [isMuted, setIsMuted] = useState<boolean>(false);
  const [currentTrack, setCurrentTrack] = useState<string>('');
  const [trackName, setTrackName] = useState<string>('Romantic BGM');

  useEffect(() => {
    const audio = new Audio();
    audio.loop = true;
    audio.preload = 'auto';
    audioRef.current = audio;

    const handlePlay = () => setIsPlaying(true);
    const handlePause = () => setIsPlaying(false);

    audio.addEventListener('play', handlePlay);
    audio.addEventListener('pause', handlePause);

    return () => {
      audio.removeEventListener('play', handlePlay);
      audio.removeEventListener('pause', handlePause);
      audio.pause();
      audio.src = '';
    };
  }, []);

  const playTrack = (src: string, name: string = 'Romantic Melody') => {
    if (!audioRef.current) return;

    if (currentTrack === src && isPlaying) {
      return;
    }

    setTrackName(name);
    setCurrentTrack(src);

    audioRef.current.src = src;
    audioRef.current.muted = isMuted;

    const playPromise = audioRef.current.play();
    if (playPromise !== undefined) {
      playPromise
        .then(() => setIsPlaying(true))
        .catch(() => {
          setIsPlaying(false);
        });
    }
  };

  const pauseMusic = () => {
    if (audioRef.current) {
      audioRef.current.pause();
      setIsPlaying(false);
    }
  };

  const toggleMute = () => {
    if (!audioRef.current) return;
    const nextMuted = !isMuted;
    setIsMuted(nextMuted);
    audioRef.current.muted = nextMuted;
    if (!isPlaying && !nextMuted) {
      audioRef.current.play().catch(() => {});
    }
  };

  return (
    <MusicContext.Provider
      value={{
        isPlaying,
        isMuted,
        currentTrack,
        trackName,
        playTrack,
        pauseMusic,
        toggleMute,
      }}
    >
      {children}

      <div className="fixed bottom-6 right-6 z-50 flex items-center gap-2">
        <button
          onClick={toggleMute}
          className={`flex h-12 w-12 items-center justify-center rounded-full shadow-lg transition-transform duration-300 hover:scale-110 active:scale-95 cursor-pointer ${
            isMuted ? 'bg-[#590D22] text-white' : 'bg-[#C9184A] text-white animate-pulse'
          }`}
          aria-label={isMuted ? 'Unmute music' : 'Mute music'}
        >
          {isMuted ? <VolumeX className="h-6 w-6" /> : <Volume2 className="h-6 w-6" />}
        </button>
      </div>
    </MusicContext.Provider>
  );
};

export const useMusic = () => {
  const context = useContext(MusicContext);
  if (!context) {
    throw new Error('useMusic must be used within a MusicProvider');
  }
  return context;
};
