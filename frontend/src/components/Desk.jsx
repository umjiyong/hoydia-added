/* eslint-disable react/self-closing-comp */
import React, { useState, useRef, useEffect } from 'react';
import styled from 'styled-components';
import post from 'assets/post.png';
import Diary from 'components/DiaryCompo';
import drawer from 'assets/drawer.png';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import AlarmList from './AlarmList';

const Container = styled.div`
  position: relative
  width: 100vw
  height: 100vh
  background-color: #ffca8c;
`;

const Desk = styled.div`
  background-color: #ffca8c;
  height: 72.8vh;
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

const DiaryBtn = styled.div`
  cursor: pointer;
`;

const DiaryContainer = styled.div``;

const Drawer = styled.img`
  display: flex;
  bottom: 0;
  width: 100%;
  height: 20.8vh;
`;

function desk() {
  const userId = window.localStorage.getItem('userId');
  const accessToken = window.localStorage.getItem('access-token');
  const navigate = useNavigate();
  const [diaryList, setDiaryList] = useState([]);
  const DiaryAsync = () => {
    axios({
      method: 'get',
      url: `http://localhost:8080/api/diary/user/${userId}/drawn`,
      headers: {
        'access-token': accessToken,
      },
    }).then((res) => {
      setDiaryList(res.data.data);
      console.log(res);
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
      axios({
        method: 'put',
        url: `http://localhost:8080/api/diary/${dragItemContent.id}`,
        headers: {
          'access-token': accessToken,
        },
        data: {
          drawn: 0,
          title: dragItemContent.title,
        },
      }).then((res) => {
        DiaryAsync();
      });
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
                  <DiaryBtn onClick={() => DiaryDetailBtn(item.id)}>
                    <Diary DiaryInfo={item} />
                  </DiaryBtn>
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
