/* eslint-disable prefer-destructuring */
/* eslint-disable react/jsx-no-bind */
import React, { useState } from 'react';

import styled from 'styled-components';
import Modal, { ModalProvider, BaseModalBackground } from 'styled-react-modal';
import exit from 'assets/exit.png';
import axios from 'axios';

const StyledModal = Modal.styled`
  width: 50.25rem;
  height: 32.25rem;
  background: #FFFFFF;
  box-shadow: 0rem 0.25rem 0.25rem  rgba(0, 0, 0, 0.25), 0rem 0.125rem 0.25rem  rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(0.25rem );
  border-radius: 2.188rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  opacity: ${(props) => props.opacity};
  transition : all 0.05s ease-in-out;;`;

const Title = styled.span`
  font-weight: 800;
  font-size: 50px;
  justify-content: center;
  align-items: center;
  color: #ff8960;
`;

const GoButton = styled.button`
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  width: 176px;
  height: 60px;
  background: #ffdbac;
  border-radius: 15px;
  border: none;
  color: #ffffff;
  font-size: 30px;
  font-weight: 700;
  -webkit-text-stroke: 1px #ff8960;
  text-shadow: -1px 0px #ff8960, 0px 1px #ff8960, 1px 0px #ff8960,
    0px -1px #ff8960;
  &:hover {
    cursor: pointer;
    background-color: #ff8960;
  }
`;

const Atag = styled.a`
  display: block;
  color: black;
  text-align: center;
  padding: 0.875rem 1rem;
  text-decoration: none;
  &:hover {
    cursor: pointer;
  }
`;

const InputQ = styled.textarea`
  width: 660px;
  height: 240px;
  border: 2px solid #dfba88;
  border-radius: 16px;
  margin: 20px 0 25px 0;
  box-sizing: border-box;
  &:active,
  &:focus {
    outline-color: #ff8960;
  }
  &::placeholder {
    color: #888888;
  }
  font-size: 20px;
  resize: none;
`;

const ExitBtn = styled.img`
  display: flex;
  justify-content: flex-end;
  width: 1.875rem;
  height: 1.875rem;
  margin: 0.938rem;
  margin-left: 46.875rem;
  margin-top: 1rem;
  &:hover {
    cursor: pointer;
  }
`;

const ExitDiv = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 1.875rem;
  height: 1.875rem;
  margin: 0.938rem;
  margin-left: 46.875rem;
  margin-top: 1rem;
`;

const QuestDiv = styled.div`
  font-size: 20px;
`;

function FancyModalButton() {
  const [isOpen, setIsOpen] = useState(false);
  const [opacity, setOpacity] = useState(0);
  const [questionPost, setquestionrPost] = useState('');

  // const randomNum = Math.floor(Math.random() * 5);
  const [num, setNum] = useState(0);

  const answerChange = (event) => {
    const value = event.target.value;
    setquestionrPost(value);
  };
  const questList = [
    '당신이 좋아 하는 영화는 무엇이며 이유를 설명해주세요.',
    '당신이 추천하거나 가고 싶은 여행지는 어디이며 이유를 설명해주세요.',
    '당신이 추천하거나 먹고 싶은 음식은 무엇이며 이유를 설명해주세요.',
    '당신이 좋아하는 계절은 무엇이며 이유를 설명해주세요.',
    '당신이 일기를 쓰고 싶은 이유와 어떤 사람인지 설명해주세요.',
  ];

  function toggleModal() {
    setOpacity(0);
    setIsOpen(!isOpen);
    setNum(Math.floor(Math.random() * 5));
    console.log(isOpen);
  }

  function toggleOn() {
    setOpacity(0);
    setIsOpen(!isOpen);
    setNum(Math.floor(Math.random() * 5));
  }

  function toggleOff() {
    setOpacity(0);
    setIsOpen(!isOpen);
    setquestionrPost('');
  }

  function togglePost() {
    axios({
      headers: {
        'access-token': `${localStorage.getItem('access-token')}`,
      },
      url: 'http://localhost:8080/api/note',
      method: 'POST',
      data: {
        question: `${questList[num]}`,
        answer: `${questionPost}`,
      },
    })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
    setquestionrPost('');
    setOpacity(0);
    setIsOpen(!isOpen);
  }

  function afterOpen() {
    setTimeout(() => {
      setOpacity(1);
    }, 100);
  }

  function beforeClose() {
    return new Promise((resolve) => {
      setOpacity(0);
      setTimeout(resolve, 300);
    });
  }

  return (
    <div>
      <Atag onClick={toggleOn}>감성 페어 찾기</Atag>
      <StyledModal
        isOpen={isOpen}
        afterOpen={afterOpen}
        beforeClose={beforeClose}
        onBackgroundClick={toggleOff}
        onEscapeKeydown={toggleOff}
        opacity={opacity}
        backgroundProps={{ opacity }}
      >
        <ExitDiv onClick={toggleOff}>
          <ExitBtn src={exit} />
        </ExitDiv>
        <Title>Q&A</Title>
        <QuestDiv>{questList[num]}</QuestDiv>
        <InputQ
          type="textarea"
          id="InputQ"
          value={questionPost || ''}
          onChange={answerChange}
          autoFocus
        />
        <GoButton type="button" onClick={togglePost}>
          GO!GO!
        </GoButton>
      </StyledModal>
    </div>
  );
}

const FadingBackground = styled(BaseModalBackground)`
  opacity: ${(props) => props.opacity};
  transition: all 0.3s ease-in-out;
`;

function Modal2() {
  return (
    <div className="Modal">
      <ModalProvider backgroundComponent={FadingBackground}>
        <FancyModalButton />
      </ModalProvider>
    </div>
  );
}

export default Modal2;
