import React, { useRef, useState } from 'react';
import styled from 'styled-components';

function MusicPlayer() {
  const audioPlayer = useRef();
  // eslint-disable-next-line operator-linebreak
  const music = [
    {
      title: 'I owe you',
      link: 'https://ia903004.us.archive.org/14/items/78rpmCollection1920s1930sPopularMusic/001%20I%20Owe%20You.mp3',
    },
    {
      title: 'Sh-Boom',
      link: 'https://ia801800.us.archive.org/10/items/78_sh-boom_the-chords-keyes-feaster-mcrae-edwards_gbia0267115a/SH-BOOM%20-%20THE%20CHORDS%20-%20Keyes%20-%20Feaster%20-%20McRae.mp3',
    },
    {
      title: 'You talk just like me',
      link: 'https://ia801703.us.archive.org/22/items/78_dats-love-act-1-scene-1_carmen-jones-orchestra-and-carmen-jones-chorus-georg_gbia8001722/03%20-%20YOU%20TALK%20JUST%20LIKE%20MY%20-%20CARLOTTA%20FRANZELL%20and%20LUTHER%20SAXON.mp3',
    },
  ];
  const [musicIndex, setMusicIndex] = useState(0);
  const [currentTime, setCurrentTime] = useState(0);
  const [seekValue, setSeekValue] = useState(0);
  const play = () => {
    audioPlayer.current.play();
  };
  const pause = () => {
    audioPlayer.current.pause();
  };
  const nextSong = () => {
    setMusicIndex((musicIndex + 1) % music.length);
  };
  const prevSong = () => {
    setMusicIndex((musicIndex - 1 + music.length) % music.length);
  };
  const onPlaying = () => {
    setCurrentTime(audioPlayer.current.currentTime);
    setSeekValue(
      (audioPlayer.current.currentTime / audioPlayer.current.duration) * 100,
    );
  };

  return (
    <MusicPlayerWrapper>
      <audio
        src={music[musicIndex].link}
        ref={audioPlayer}
        onTimeUpdate={onPlaying}
      >
        <track default kind="captions" />
      </audio>
      <br />
      <p>
        {`${parseInt(currentTime / 60, 10)}:${String(
          parseInt(currentTime % 60, 10),
        ).padStart(2, '0')}`}
      </p>
      <input
        type="range"
        min="0"
        max="100"
        step="1"
        value={seekValue}
        onChange={(e) => {
          const seekto = audioPlayer.current.duration * (+e.target.value / 100);
          audioPlayer.current.currentTime = seekto;
          setSeekValue(e.target.value);
        }}
      />
      <div>
        <button type="button" onClick={play}>
          play
        </button>
        <button type="button" onClick={pause}>
          pause
        </button>
        <button type="button" onClick={nextSong}>
          next
        </button>
        <button type="button" onClick={prevSong}>
          prev
        </button>
      </div>
    </MusicPlayerWrapper>
  );
}

const MusicPlayerWrapper = styled.div`
  width: 472px;
  height: 170px;
  background: #f2f2f2;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 25px;
`;

export default MusicPlayer;
