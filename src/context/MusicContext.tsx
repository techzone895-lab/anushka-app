import React, { createContext, useContext, useEffect, useRef, useState, useCallback } from 'react';
import { Volume2, VolumeX, Music } from 'lucide-react';

interface MusicContextType {
  currentTrack: string | null;
  trackTitle: string;
  isMuted: boolean;
  isPlaying: boolean;
  playTrack: (src: string, title?: string) => void;
  pauseAudio: () => void;
  resumeAudio: () => void;
  toggleMute: () => void;
}

const MusicContext = createContext<MusicContextType | null>(null);

export const MusicProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const [currentTrack, setCurrentTrack] = useState<string | null>(null);
  const [trackTitle, setTrackTitle] = useState<string>('');
  const [isMuted, setIsMuted] = useState<boolean>(false);
  const [isPlaying, setIsPlaying] = useState<boolean>(false);
  const userInteractedRef = useRef<boolean>(false);

  // Initialize audio element
  useEffect(() => {
    const audio = new Audio();
    audio.loop = true;
    audio.preload = 'auto';
    audioRef.current = audio;

    const onPlay = () => setIsPlaying(true);
    const onPause = () => setIsPlaying(false);
    audio.addEventListener('play', onPlay);
    audio.addEventListener('pause', onPause);

    // Global click listener to unlock autoplay
    const unlockAutoplay = () => {
      userInteractedRef.current = true;
      if (audioRef.current && audioRef.current.src && audioRef.current.paused && !isMuted) {
        audioRef.current.play().catch(() => {});
      }
      window.removeEventListener('click', unlockAutoplay);
      window.removeEventListener('touchstart', unlockAutoplay);
    };

    window.addEventListener('click', unlockAutoplay);
    window.addEventListener('touchstart', unlockAutoplay);

    return () => {
      audio.removeEventListener('play', onPlay);
      audio.removeEventListener('pause', onPause);
      audio.pause();
      audio.src = '';
      window.removeEventListener('click', unlockAutoplay);
      window.removeEventListener('touchstart', unlockAutoplay);
    };
  }, []);

  const playTrack = useCallback((src: string, title?: string) => {
    if (!audioRef.current) return;
    
    // If already playing this track, don't restart
    if (currentTrack === src && !audioRef.current.paused) {
      return;
    }

    setCurrentTrack(src);
    if (title) setTrackTitle(title);

    audioRef.current.src = src;
    audioRef.current.muted = isMuted;

    audioRef.current.play().then(() => {
      setIsPlaying(true);
    }).catch(() => {
      // Audio autoplay blocked until user interaction
      setIsPlaying(false);
    });
  }, [currentTrack, isMuted]);

  const pauseAudio = useCallback(() => {
    if (audioRef.current) {
      audioRef.current.pause();
      setIsPlaying(false);
    }
  }, []);

  const resumeAudio = useCallback(() => {
    if (audioRef.current && audioRef.current.src) {
      audioRef.current.play().catch(() => {});
    }
  }, []);

  const toggleMute = useCallback(() => {
    if (!audioRef.current) return;
    const nextMuted = !isMuted;
    setIsMuted(nextMuted);
    audioRef.current.muted = nextMuted;
    if (!nextMuted && audioRef.current.paused && audioRef.current.src) {
      audioRef.current.play().catch(() => {});
    }
  }, [isMuted]);

  return (
    <MusicContext.Provider
      value={{
        currentTrack,
        trackTitle,
        isMuted,
        isPlaying,
        playTrack,
        pauseAudio,
        resumeAudio,
        toggleMute,
      }}
    >
      {children}
      {/* Floating Audio FAB matching MainActivity.kt bottom-right FAB */}
      {currentTrack && (
        <div className="fixed bottom-6 right-6 z-50 flex items-center gap-2">
          {isPlaying && !isMuted && (
            <div className="hidden sm:flex items-center gap-1.5 rounded-full bg-white/90 px-3 py-1.5 shadow-md backdrop-blur-sm border border-pink-200">
              <span className="flex h-2 w-2 relative">
                <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-rose-400 opacity-75"></span>
                <span className="relative inline-flex rounded-full h-2 w-2 bg-rose-500"></span>
              </span>
              <span className="text-xs font-semibold text-[#C9184A] max-w-[120px] truncate">
                {trackTitle || 'Romantic Melody'}
              </span>
            </div>
          )}

          <button
            onClick={toggleMute}
            className="flex h-13 w-13 items-center justify-center rounded-full bg-[#C9184A] text-white shadow-lg transition-transform duration-200 hover:scale-110 active:scale-95 hover:shadow-[0_8px_20px_rgba(201,24,74,0.45)] cursor-pointer"
            title={isMuted ? 'Unmute Background Music' : 'Mute Background Music'}
            aria-label={isMuted ? 'Unmute Background Music' : 'Mute Background Music'}
          >
            {isMuted ? (
              <VolumeX className="h-6 w-6 stroke-[2.2]" />
            ) : (
              <Volume2 className="h-6 w-6 stroke-[2.2]" />
            )}
          </button>
        </div>
      )}
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
