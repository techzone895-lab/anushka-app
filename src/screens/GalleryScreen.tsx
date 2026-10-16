import React, { useEffect, useState } from 'react';
import { ArrowLeft, Mail, Sparkles, Heart } from 'lucide-react';
import { useMusic } from '../context/MusicContext';
import { ImageViewerModal } from './ImageViewerModal';

interface GalleryScreenProps {
  onBack: () => void;
  onNavigateToLetter: () => void;
}

export const GalleryScreen: React.FC<GalleryScreenProps> = ({ onBack, onNavigateToLetter }) => {
  const { playTrack } = useMusic();
  const [selectedImageIndex, setSelectedImageIndex] = useState<number | null>(null);

  useEffect(() => {
    playTrack('/audio/new_song.mp3', 'Memories Melody 📸');
  }, [playTrack]);

  // The 12 gallery images discovered in drawable/
  const galleryImages = [
    { src: '/images/anushka_1.jpg', caption: 'Precious Smile 💕' },
    { src: '/images/anushka_2.jpg', caption: 'Sweet Moments ✨' },
    { src: '/images/anushka_3.jpg', caption: 'My Favorite Girl 🌹' },
    { src: '/images/anushka_4.jpg', caption: 'Cutest Charm 🌸' },
    { src: '/images/anushka_5.jpg', caption: 'Pure Magic 💖' },
    { src: '/images/anushka_6.jpg', caption: 'Forever Angel 🕊️' },
    { src: '/images/anushka_7.jpg', caption: 'Radiant Glow ✨' },
    { src: '/images/anushka_8.jpg', caption: 'Heart stealer 🥰' },
    { src: '/images/anushka_9.jpg', caption: 'Dreamy Eyes 🌙' },
    { src: '/images/anushka_10.jpg', caption: 'Pure Joy 🎈' },
    { src: '/images/anushka_11.jpg', caption: 'Beautiful Soul 💫' },
    { src: '/images/anushka_12.jpg', caption: 'My Whole World 👑' },
    { src: '/images/anushka_13.jpg', caption: 'Forever & Always 💖' },
  ];

  const handleNextPhoto = () => {
    if (selectedImageIndex !== null) {
      setSelectedImageIndex((selectedImageIndex + 1) % galleryImages.length);
    }
  };

  const handlePrevPhoto = () => {
    if (selectedImageIndex !== null) {
      setSelectedImageIndex(
        (selectedImageIndex - 1 + galleryImages.length) % galleryImages.length
      );
    }
  };

  return (
    <div className="relative min-h-screen w-full flex flex-col bg-[#FFF0F5] pb-24 text-[#4A0E17]">
      {/* Top Bar matching activity_gallery.xml */}
      <header className="sticky top-0 z-30 flex items-center gap-3 bg-[#FFF0F5]/90 px-4 pt-10 pb-3 backdrop-blur-md border-b border-pink-200/50">
        <button
          onClick={onBack}
          className="flex h-11 w-11 shrink-0 items-center justify-center rounded-full bg-white text-[#C9184A] shadow-sm transition-transform hover:scale-105 active:scale-95 cursor-pointer"
          aria-label="Back"
        >
          <ArrowLeft className="h-5 w-5 stroke-[2.5]" />
        </button>

        <div className="flex flex-col">
          <h1 className="text-xl sm:text-2xl font-bold text-[#C9184A] leading-tight flex items-center gap-1.5">
            <span>Our Memories</span>
            <span>💕</span>
          </h1>
          <p className="text-xs sm:text-sm text-[#8B263E] font-medium">
            Every moment with you is precious ✨
          </p>
        </div>
      </header>

      {/* Grid of Images matching RecyclerView 2 columns */}
      <main className="flex-1 p-3 sm:p-5 max-w-4xl mx-auto w-full">
        <div className="grid grid-cols-2 sm:grid-cols-3 gap-3 sm:gap-4">
          {galleryImages.map((img, index) => (
            <div
              key={index}
              onClick={() => setSelectedImageIndex(index)}
              className="group relative cursor-pointer overflow-hidden rounded-2xl bg-white p-1.5 shadow-md transition-all duration-300 hover:-translate-y-1 hover:shadow-xl active:scale-[0.98] border border-pink-100"
            >
              <div className="relative aspect-[3/4] w-full overflow-hidden rounded-xl bg-pink-50">
                <img
                  src={img.src}
                  alt={img.caption}
                  loading="lazy"
                  className="h-full w-full object-cover transition-transform duration-500 group-hover:scale-105"
                  onError={(e) => {
                    // Fallback to Anushka photo if any index has issue
                    (e.currentTarget as HTMLImageElement).src = '/images/anushka_photo.jpg';
                  }}
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/50 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-2">
                  <span className="text-xs font-semibold text-white drop-shadow">
                    {img.caption}
                  </span>
                </div>
              </div>

              <div className="mt-1.5 flex items-center justify-between px-1.5 py-1">
                <span className="text-[11px] sm:text-xs font-semibold text-[#8B263E] truncate">
                  Memory #{index + 1}
                </span>
                <Heart className="h-3.5 w-3.5 fill-[#FF758F] text-[#FF758F]" />
              </div>
            </div>
          ))}
        </div>
      </main>

      {/* Fixed Bottom Bar matching bottomBar in activity_gallery.xml */}
      <div className="fixed bottom-0 inset-x-0 z-40 p-4 bg-gradient-to-t from-[#FFF0F5] via-[#FFF0F5]/90 to-transparent flex items-center justify-center">
        <div className="w-full max-w-md">
          <button
            onClick={onNavigateToLetter}
            className="w-full h-14 rounded-full bg-[#C9184A] hover:bg-[#b01440] active:scale-[0.98] text-white text-base font-bold shadow-xl transition-all flex items-center justify-center gap-2.5 cursor-pointer"
            style={{
              boxShadow: '0 8px 24px -4px rgba(201, 24, 74, 0.45)',
            }}
          >
            <Mail className="h-5 w-5" />
            <span>A Secret Letter For You 💌</span>
          </button>
        </div>
      </div>

      {/* Fullscreen Image Viewer Modal */}
      {selectedImageIndex !== null && (
        <ImageViewerModal
          imageSrc={galleryImages[selectedImageIndex].src}
          imageAlt={galleryImages[selectedImageIndex].caption}
          onClose={() => setSelectedImageIndex(null)}
          onNext={handleNextPhoto}
          onPrev={handlePrevPhoto}
        />
      )}
    </div>
  );
};
