import React, { useRef, useState } from 'react';
import styled from 'styled-components';

function MusicPlayer() {
  const audioPlayer = useRef();
  const [music, setMusic] = useState(
    'https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3',
  );
  const [currentTime, setCurrentTime] = useState(0);
  const [seekValue, setSeekValue] = useState(0);
  const play = () => {
    audioPlayer.current.play();
  };
  const pause = () => {
    audioPlayer.current.play();
  };
  const stop = () => {
    audioPlayer.current.stop();
  };
  const onPlaying = () => {
    setCurrentTime(audioPlayer.current.currentTime);
    seekValue(
      (audioPlayer.current.currentTime / audioPlayer.current.duration) * 100,
    );
  };

  return (
    <MusicPlayerWrapper>
      {/* <audio src={music} ref={audioPlayer} onTimeUpdate={onPlaying} /> */}
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
