/* eslint-disable prefer-destructuring */
/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable react/jsx-no-bind */
import React, { useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';
import Modal, { ModalProvider, BaseModalBackground } from 'styled-react-modal';
import exit from 'assets/exit.png';
import hedgehogs from 'assets/hedgehogs.png';

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
  justify-content: space-around
  opacity: ${(props) => props.opacity};
  transition : all 0.05s ease-in-out;;`;

const Mascot = styled.img`
  width: 400px;
  height: auto;
`;

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
  &:hover,
  &:active {
    cursor: pointer;
    color: #fff;
    background-color: #ff8960;
    border-radius: 10px;
  }
`;

const InputCode = styled.input`
  box-sizing: border-box;
  margin-bottom: 20px;
  padding: 0.5rem 1rem;
  width: 600px;
  height: 60px;
  background: #ffffff;
  border: 0.125rem solid #dfba88;
  border-radius: 1rem;
  &:active,
  &:focus {
    outline-color: #ff8960;
  }
  &::placeholder {
    color: #888888;
  }
  font-size: 20px;
`;

const ExitBtn = styled.img`
  width: 1.875rem;
  height: 1.875rem;
  &:hover {
    cursor: pointer;
  }
`;

const ExitDiv = styled.div`
  width: 1.875rem;
  height: 1.875rem;
  margin: 24px 24px 0px 750px;
`;

function FancyModalButton() {
  const [isOpen, setIsOpen] = useState(false);
  const [opacity, setOpacity] = useState(0);
  const [friendPost, setfriendPost] = useState();

  const friendChange = (event) => {
    const value = event.target.value;
    setfriendPost(value);
  };

  function toggleModal() {
    setOpacity(0);
    setIsOpen(!isOpen);
    setfriendPost('');
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

  function togglePost() {
    axios({
      headers: {
        'access-token': `${localStorage.getItem('access-token')}`,
      },
      url: '/diary',
      method: 'POST',
      data: {
        buttonColor: '',
        diaryColor: '',
        pairId: `${friendPost}`,
      },
    })
      .then((res) => {})
      .catch((err) => {});
    setOpacity(0);
    setIsOpen(!isOpen);
    setfriendPost('');
  }

  return (
    <div>
      <Atag onClick={toggleModal}>친구랑 일기 만들기</Atag>
      <StyledModal
        isOpen={isOpen}
        afterOpen={afterOpen}
        beforeClose={beforeClose}
        onBackgroundClick={toggleModal}
        onEscapeKeydown={toggleModal}
        opacity={opacity}
        backgroundProps={{ opacity }}
      >
        <ExitDiv onClick={toggleModal}>
          <ExitBtn src={exit} />
        </ExitDiv>
        <Title>친구랑 일기 만들기</Title>
        <Mascot src={hedgehogs} />
        <form>
          <InputCode
            type="text"
            placeholder="같이 일기를 만들 친구의 코드를 입력해주세요."
            autoFocus
            value={friendPost || ''}
            onChange={friendChange}
          />
        </form>
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

function Modal1() {
  return (
    <div className="Modal">
      <ModalProvider backgroundComponent={FadingBackground}>
        <FancyModalButton />
      </ModalProvider>
    </div>
  );
}

export default Modal1;
