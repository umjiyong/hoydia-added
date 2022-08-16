import styled from 'styled-components';
import React, { useState, useEffect, useRef } from 'react';
import Diary from 'components/DiaryCompo';
import Navbar from 'components/Navbar';
import Toast from 'components/Toast';
import { useNavigate } from 'react-router-dom';
import floatingbutton from 'assets/floatingButton.png';
import axios from 'axios';

const DrawerContainer = styled.div`
  background-color: F6F6F6;
`;

const DiaryContainer = styled.div`
  align-items: center;
`;

const Colcontainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 40px 0 0 55px;
  width: 300px;
  height: 400px;
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

const DiaryBtn = styled.div`
  margin: 0 0 90px 0;
`;

function DrawerPage() {
  const userId = localStorage.getItem('userId');
  const accessToken = localStorage.getItem('access-token');
  const navigate = useNavigate();
  const [list, setList] = useState([]);
  const [ToastStatus, setToastStatus] = useState(false);
  const handleToast = () => {
    setToastStatus(true);
  };
  const DiaryAsync = () => {
    axios({
      method: 'get',
      url: `http://localhost:8080/api/diary/user/${userId}/notdrawn`,
      headers: {
        'access-token': accessToken,
      },
    }).then((res) => {
      console.log(res);
      setList(res.data.data);
    });
  };
  const DiaryDetailBtn = (diaryId) => {
    axios({
      method: 'get',
      url: `http://localhost:8080/api/page/diary/${diaryId}`,
      headers: {
        'access-token': accessToken,
      },
    }).then((res) => {
      if (res.data.data[0]) {
        const pageId = res.data.data[0].id;
        navigate(`/diaryDetailPage/${diaryId}/${pageId}`);
      } else {
        navigate(`/diaryDetailPage/${diaryId}/1`);
      }
    });
  };
  useEffect(() => {
    DiaryAsync();
    if (ToastStatus) {
      setTimeout(() => setToastStatus(false), 2000);
    }
  }, []);
  const dragItem = useRef();
  const dragOverItem = useRef();
  const dragStart = (e, position) => {
    dragItem.current = position;
  };
  const dragEnter = (e, position) => {
    dragOverItem.current = position;
  };
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
      console.log(dragItemContent);
      axios({
        method: 'PUT',
        url: `http://localhost:8080/api/diary/${dragItemContent.id}`,
        headers: {
          'access-token': accessToken,
        },
        data: {
          drawn: 1,
          title: dragItemContent.title,
          buttonColor: dragItemContent.buttonColor,
          diaryColor: dragItemContent.diaryColor,
          fontColor: dragItemContent.fontColor,
          fontSize: dragItemContent.fontSize,
          fontStyle: dragItemContent.fonStyle,
        },
      })
        .then((res) => {
          console.log(res);
          DiaryAsync();
        })
        .catch((err) => {
          handleToast();
          console.log(err);
        });
    }
  };

  return (
    <div className="App">
      <Navbar />
      <DrawerContainer>
        <DiaryContainer
          style={{
            display: 'grid',
            gridTemplateRows: '1fr',
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
                <DiaryBtn onClick={() => DiaryDetailBtn(item.id)}>
                  <Diary DiaryInfo={item} />
                </DiaryBtn>
              </Colcontainer>
            ))}
        </DiaryContainer>
        <FloatingBtn
          onClick={() => navigate('/mainpage')}
          src={floatingbutton}
        />
        {ToastStatus && <Toast msg="DESK의 자리가 부족합니다." />}
      </DrawerContainer>
    </div>
  );
}

export default React.memo(DrawerPage);
