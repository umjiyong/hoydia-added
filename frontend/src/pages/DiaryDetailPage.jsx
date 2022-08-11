import React from 'react';
import Navbar from 'components/Navbar';
import styled from 'styled-components';
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';
import Diary from 'assets/CreateDiaryBackground.png';
import kakaomapbtn from 'assets/KakaoMapBtn.png';
import createpagebtn from 'assets/CreatePageBtn.png';
import sendbtn from 'assets/SendBtn.png';
import editbtn from 'assets/EditBtn.png';
import exitbtn from 'assets/ExitBtn.png';

const MusicPlayer = styled.div`
  width: 300px;
  height: 170px;
`;

const DetailKakaoBtn = styled.div`
  position: absolute;
  top: 55%;
  left: 12.7%;
  z-index: 2;
  width: 72px;
  height: 118px;
  background-image: url(${kakaomapbtn});
  background-repeat: no-repeat;
  background-size: 100%;
`;

const DetailSendBtn = styled.div`
  position: absolute;
  top: 9.2%;
  right: 34%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${sendbtn});
  background-repeat: no-repeat;
  background-size: 100%;
`;

const DetailCreateBtn = styled.div`
  position: absolute;
  top: 9.2%;
  right: 29%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${createpagebtn});
  background-repeat: no-repeat;
  background-size: 100%;
`;

const DetailEditBtn = styled.div`
  position: absolute;
  top: 9.2%;
  right: 24%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${editbtn});
  background-repeat: no-repeat;
  background-size: 100%;
`;
const DetailExitBtn = styled.div`
  position: absolute;
  top: 9.2%;
  right: 19%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${exitbtn});
  background-repeat: no-repeat;
  background-size: 100%;
`;

const DetailContainer = styled.div`
  position: absolute;
  bottom: 5%;
  left: 12.5%;
  background-color: #ff8960;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 35px;
  width: 75.15%;
  height: 87%;
  padding: 60px 76px 46px 76px;
`;

const MainDiv = styled.div`
  display: grid;
  height: 100%;
  grid-template-columns: 1fr 1fr;
  grid-gap: 0.25rem;
  background-image: url(${Diary});
  background-repeat: no-repeat;
  background-size: cover;
  padding-bottom: 10px;
`;

const LeftDiv = styled.div`
  display: flex;
  height: 100%;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
`;

const RightDiv = styled.div`
  display: flex;
  height: 100%;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
`;
const ImageBox = styled.img`
  margin-top: 50px;
  width: 300px;
  height: 300px;
`;
const TitleDiv = styled.div``;
const ContentDiv = styled.div``;

function DetailPage() {
  const ImageUrl =
    'data:image/jpeg;base64,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';
  return (
    <div className="diaryDetailPage">
      <Navbar />
      <DetailCreateBtn />
      <DetailSendBtn />
      <DetailEditBtn />
      <DetailExitBtn />
      <DetailKakaoBtn />
      <DetailContainer>
        <MainDiv>
          <LeftDiv>
            <ImageBox src={ImageUrl} alt="ImageBox" />
            <MusicPlayer>
              <AudioPlayer
                // autoPlay
                loop
                onPlay={(e) => console.log('onPlay')}
                volume={0.1}
                src="https://hanzluo.s3-us-west-1.amazonaws.com/music/ziyounvshen.mp3"
                showJumpControls={false}
              />
            </MusicPlayer>
          </LeftDiv>
          <RightDiv>
            <TitleDiv>제목</TitleDiv>
            <ContentDiv>내용</ContentDiv>
          </RightDiv>
        </MainDiv>
      </DetailContainer>
    </div>
  );
}

export default DetailPage;
