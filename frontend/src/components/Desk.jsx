/* eslint-disable react/self-closing-comp */
import React, { useState, useRef } from 'react';
import styled from 'styled-components';
import post from 'assets/post.png';
import Diary from 'components/DiaryCompo';
import drawer from 'assets/drawer.png';
import { Link } from 'react-router-dom';
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
const Deskcontainer = styled.div`
  display: flex;
  justify-content: space-around;
`;

const DiaryContainer = styled.div``;

// const Diary = styled.img`
//   width: 20%;
//   height: 25%;
//   max-width: 240px;
//   max-height: 300px;
//   // margin: 40px;
// `;

const Drawer = styled.img`
  display: flex;
  bottom: 0;
  width: 100%;
  height: auto;
`;

const DiaryInfo1 = {
  color1: '#4269f5',
  color2: '#a61f3d',
  color3: '#d1aa2a',
  title: '안녕하세요1',
  font: 'Jua',
  fontsize: 24,
};
const DiaryInfo2 = {
  color1: '#34d8eb',
  color2: '#a61f3d',
  color3: '#d1aa2a',
  title: '안녕하세요2',
  font: 'Jua',
  fontsize: 24,
};
const DiaryInfo3 = {
  color1: '#695140',
  color2: '#a61f8d',
  color3: '#d1aa2a',
  title: '안녕하세요3',
  font: 'Jua',
  fontsize: 24,
};

function desk() {
  const [diaryList, setDiaryList] = useState([
    DiaryInfo1,
    DiaryInfo2,
    DiaryInfo3,
  ]);
  const dragItem = useRef();
  const dragOverItem = useRef();
  const dragStart = (e, position) => {
    dragItem.current = position;
  };
  const dragEnter = (e, position) => {
    dragOverItem.current = position;
  };
  const drop = (e) => {
    const copyListItems = [...diaryList];
    const dragItemContent = copyListItems[dragItem.current];
    copyListItems.splice(dragItem.current, 1);
    copyListItems.splice(dragOverItem.current, 0, dragItemContent);
    dragItem.current = null;
    dragOverItem.current = null;
    setDiaryList(copyListItems);
    const visualheight1 = window.visualViewport.height;
    const visualwidth1 = window.visualViewport.width;
    const visualheight2 = window.visualViewport.height * 0.8;
    const visualwidth2 = 0;
    const height = e.clientY;
    const width = e.clientX;
    if (
      visualheight1 >= height &&
      height >= visualheight2 &&
      visualwidth1 >= width &&
      width >= visualwidth2
    ) {
      console.log('성공');
    } else {
      console.log('실패');
    }
  };
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
          <Deskcontainer>
            {diaryList &&
              diaryList.map((item, index) => (
                <DiaryContainer
                  size={2}
                  className="item"
                  key={index}
                  onDragStart={(e) => dragStart(e, index)}
                  onDragEnter={(e) => dragEnter(e, index)}
                  onDragOver={(e) => e.preventDefault()}
                  onDragEnd={drop}
                  draggable
                >
                  <Diary DiaryInfo={item} />
                </DiaryContainer>
              ))}
          </Deskcontainer>
        </Desk>
        <Link to="/drawerPage">
          <Drawer src={drawer} alt="drawer" />
        </Link>
      </Container>
    </div>
  );
}
export default desk;
