import styled from 'styled-components';
import React, { useState, useEffect, useRef } from 'react';
import Diary from 'components/DiaryCompo';
import Navbar from 'components/Navbar';
import Toast from 'components/Toast';
import { useNavigate } from 'react-router-dom';
import floatingbutton from 'assets/floatingButton.png';
import axios from 'axios';

const DrawerContainer = styled.div``;

const DiaryContainer = styled.div``;

const Colcontainer = styled.div`
  display: flex;
  justify-content: center;
  margin: 20px;
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

const DiaryBtn = styled.div``;

const userId = window.localStorage.getItem('userId');
const accessToken = window.localStorage.getItem('access-token');

function DrawerPage() {
  const navigate = useNavigate();
  const [list, setList] = useState([]);
  const [ToastStatus, setToastStatus] = useState(false);
  const handleToast = () => {
    setToastStatus(true);
  };
  const DiaryAsync = async () => {
    try {
      axios({
        method: 'get',
        url: `http://localhost:8080/diary/user/${userId}/notdrawn`,
        headers: {
          'access-token': accessToken,
        },
      }).then((res) => {
        setList(res.data.data);
      });
    } catch (e) {
      setList([]);
    }
  };
  const DiaryDetailBtn = (diaryId) => {
    axios({
      method: 'get',
      url: `http://localhost:8080/page/diary/${diaryId}`,
      headers: {
        'access-token': accessToken,
      },
    }).then((res) => {
      const pageId = res.data.data[0].id;
      navigate(`/diaryDetailPage/${diaryId}/${pageId}`);
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
      axios({
        method: 'PUT',
        url: `http://localhost:8080/diary/${dragItemContent.id}`,
        headers: {
          'access-token': accessToken,
        },
        data: {
          drawn: 1,
          title: dragItemContent.title,
        },
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          handleToast();
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
                <DiaryBtn onClick={() => DiaryDetailBtn(item.id)}>
                  <Diary DiaryInfo={item} />
                </DiaryBtn>
              </Colcontainer>
            ))}
        </DiaryContainer>
        <FloatingBtn src={floatingbutton} />
        {ToastStatus && <Toast msg="DESK의 자리가 부족합니다." />}
      </DrawerContainer>
    </div>
  );
}

export default React.memo(DrawerPage);
