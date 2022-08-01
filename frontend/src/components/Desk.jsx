/* eslint-disable react/self-closing-comp */
import React from 'react';
import styled from 'styled-components';
import post from 'assets/post.png';
import diary from 'assets/diary.png';
import drawer from 'assets/drawer.png';

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
  margin: 30px;
`;

const Diary = styled.img`
  width: 20%;
  height: 25%;
  max-width: 240px;
  max-height: 300px;
  // margin: 40px;
`;

const Drawer = styled.img`
  position: absolute;
  bottom: 0;
  max-width: 100%;
  height: auto;
`;

function desk() {
  return (
    <div className="desk">
      <Container>
        <Desk>
          <PostDiv>
            <Post src={post} alt="post" />
          </PostDiv>
          <DiaryContainer>
            <Diary src={diary} alt="diary" />
            <Diary src={diary} alt="diary" />
            <Diary src={diary} alt="diary" />
          </DiaryContainer>
        </Desk>
        <Drawer src={drawer} alt="drawer" />
      </Container>
    </div>
  );
}
export default desk;
