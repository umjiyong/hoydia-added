import React from 'react';
import Navbar from 'components/Navbar';
import styled from 'styled-components';
import detailcover from 'assets/detailcover.svg';
import detailcreatepagebtn from 'assets/detailcreatepagebtn.png';
import detailkakaomapbtn from 'assets/detailkakaomapbtn.png';
import detailpaper from 'assets/detailpaper.png';
import detailsendbtn from 'assets/detailsendbtn.png';
import detailspring from 'assets/detailspring.png';
import detailsteel from 'assets/detailsteel.png';
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';

const MusicPlayer = styled.div`
  position: absolute;
  width: 472px;
  height: 170px;
  //   left: 360px;
  //   top: 770px;
`;

const Detailcover = styled.img`
  position: absolute;
  left: 241px;
  top: 103px;
`;
const Detailcreatepagebtn = styled.img`
  position: absolute;
  left: 1337.73px;
  top: 125.74px;
`;
const Detailkakaomapbtn = styled.img``;
const Detailpaper = styled.img`
  position: absolute;
  left: 315px;
  top: 167px;
`;
const Detailsendbtn = styled.img`
  position: absolute;
  left: 1214px;
  top: 127px;
`;
const Detailspring = styled.img`
  position: absolute;
  left: 880px;
  top: 257px;
`;
const Detailsteel = styled.img`
  position: absolute;
  left: 906px;
  top: 134px;
`;

const playlist = [
  { src: 'https://hanzluo.s3-us-west-1.amazonaws.com/music/ziyounvshen.mp3' },
  { src: 'https://hanzluo.s3-us-west-1.amazonaws.com/music/wuyuwuqing.mp3' },
  { src: 'https://hanzluo.s3-us-west-1.amazonaws.com/music/suipian.mp3' },
];

function DetailPage() {
  const [currentTrack, setTrackIndex] = React.useState(0);
  const handleClickNext = () => {
    console.log('click next');
    // setTrackIndex((currentTrack) =>
    //   currentTrack < playlist.length - 1 ? currentTrack + 1 : 0,
    // );
  };

  const handleEnd = () => {
    console.log('end');
    // setTrackIndex((currentTrack) =>
    //   currentTrack < playlist.length - 1 ? currentTrack + 1 : 0,
    // );
  };
  return (
    <div className="DetailPage">
      <Navbar />

      <Detailcover src={detailcover} />
      <Detailpaper src={detailpaper} />
      <Detailcreatepagebtn src={detailcreatepagebtn} />
      <Detailkakaomapbtn src={detailkakaomapbtn} />
      <Detailsendbtn src={detailsendbtn} />
      <Detailspring src={detailspring} />
      <Detailsteel src={detailsteel} />
      <MusicPlayer>
        <AudioPlayer
          autoPlay
          onPlay={(e) => console.log('onPlay')}
          volume={0.5}
          src={playlist[currentTrack].src}
          showSkipControls
          onClickNext={handleClickNext}
          onEnded={handleEnd}
        />
      </MusicPlayer>
    </div>
  );
}

export default DetailPage;
