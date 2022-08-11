import styled from 'styled-components';
import React, { useState, useEffect, useRef } from 'react';
import Diary from 'components/DiaryCompo';
import Navbar from 'components/Navbar';
import floatingbutton from 'assets/floatingButton.png';
import axios from 'axios';

const LoaderWrap = styled.div`
  width: 100%;
  height: 80%;
  display: flex;
  justify-content: center;
  text-align: center;
  align-items: center;
`;
const DrawerContainer = styled.div``;

const DiaryContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const Colcontainer = styled.div`
  flex: ${(props) => props.size};
`;

const FloatingBtn = styled.img`
  position: fixed; //포인트!
  line-height: 63px;
  bottom: 40px; //위치
  right: 40px; //위치
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
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

function DrawerPage() {
  const userId = window.localStorage.getItem('userId');
  const accessToken = window.localStorage.getItem('access-token');

  const [list, setList] = useState([DiaryInfo1, DiaryInfo2, DiaryInfo3]);
  const dragItem = useRef();
  const dragOverItem = useRef();
  const dragStart = (e, position) => {
    dragItem.current = position;
  };
  const dragEnter = (e, position) => {
    dragOverItem.current = position;
  };
  // const dragfloating = () => {
  //   axios.put(`http://localhost:8080/diary/${diaryId}`, )
  // };
  const drop = (e) => {
    const copyListItems = [...list];
    const dragItemContent = copyListItems[dragItem.current];
    copyListItems.splice(dragItem.current, 1);
    copyListItems.splice(dragOverItem.current, 0, dragItemContent);
    dragItem.current = null;
    dragOverItem.current = null;
    setList(copyListItems);
    const visualheight1 = window.visualViewport.height - 40;
    const visualwidth1 = window.visualViewport.width - 40;
    const visualheight2 = window.visualViewport.height - 140;
    const visualwidth2 = window.visualViewport.width - 140;
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
  const consoletest = () => {
    console.log(1);
  };
  useEffect(() => {
    axios({
      method: 'get',
      url: `http://localhost:8080/diary/user/${userId}`,
      headers: {
        'access-token': accessToken,
      },
    }).then((res) => console.log(res));
  });
  return (
    <div className="App">
      <Navbar />
      <DrawerContainer>
        <DiaryContainer
          style={{
            display: 'grid',
            gridTemplateRows: '1fr ',
            gridTemplateColumns: '1fr 1fr 1fr ',
          }}
        >
          {list &&
            list.map((item, index) => (
              <Colcontainer
                size={2}
                className="Item"
                key={index}
                onDragStart={(e) => dragStart(e, index)}
                onDragEnter={(e) => dragEnter(e, index)}
                onDragOver={(e) => e.preventDefault()}
                onDragEnd={drop}
                draggable
              >
                <Diary DiaryInfo={item} />
              </Colcontainer>
            ))}
        </DiaryContainer>
        <FloatingBtn src={floatingbutton} />
      </DrawerContainer>
    </div>
  );
}

export default DrawerPage;
