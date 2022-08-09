/* eslint-disable react/self-closing-comp */
import React, { useState } from 'react';
import styled from 'styled-components';
import post from 'assets/post.png';
import Diary from 'components/DiaryCompo';
import drawer from 'assets/drawer.png';
import AlarmList from './AlarmList';

const Container = styled.div`
  position: relative
  width: 100vw
  height: 100vh
  background-color: #ffca8c;
`;

const Desk = styled.div`
  background-color: #ffca8c;
  height: calc(1683 / 1920 * 100vh);
`;

const PostDiv = styled.div`
  display: flex;
  justify-content: flex-end;
  // margin-bottom: auto;
`;

const Post = styled.img`
  width: 15%;
  height: 10%;
  max-width: 150px;
  max-height: 100px;
  margin-top: 90px;
  margin-right: 40px;
`;

const DiaryContainer = styled.div`
  display: flex;
  justify-content: space-around;
  margin: 5px;
`;

// const Diary = styled.img`
//   width: 20%;
//   height: 25%;
//   max-width: 240px;
//   max-height: 300px;
//   // margin: 40px;
// `;

const Drawer = styled.img`
  position: absolute;
  bottom: 0;
  max-width: 100%;
  height: auto;
`;

const DiaryInfo = {
  color1: '#4269f5',
  color2: '#a61f3d',
  color3: '#d1aa2a',
  title: '안녕하세요',
  font: 'Jua',
  fontsize: 24,
};

function desk() {
  const [openDrop, setOpenDrop] = useState(false);
  const openAlarm = () => {
    setOpenDrop((prevState) => !prevState);
  };
  return (
    <div className="desk">
      <Container>
        <Desk>
          {openDrop ? <AlarmList /> : null}
          <PostDiv onClick={openAlarm}>
            <Post src={post} alt="post" />
          </PostDiv>
          <DiaryContainer>
            <Diary DiaryInfo={DiaryInfo} />
            <Diary DiaryInfo={DiaryInfo} />
            <Diary DiaryInfo={DiaryInfo} />
          </DiaryContainer>
        </Desk>
        <Drawer src={drawer} alt="drawer" />
      </Container>
    </div>
  );
}
export default desk;
