/* eslint-disable no-alert */
/* eslint-disable key-spacing */
/* eslint-disable quote-props */
/* eslint-disable no-console */
/* eslint-disable camelcase */
/* eslint-disable react/jsx-one-expression-per-line */
/* eslint-disable no-shadow */
/* eslint-disable react/jsx-no-bind */
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Modal, { ModalProvider, BaseModalBackground } from 'styled-react-modal';
import exit from 'assets/exit.png';
import axios from 'axios';

const StyledModal = Modal.styled`
       

  
  width: 59.25rem;
  height: 38rem;
  background: #FFFFFF;
  box-shadow: 0rem 0.25rem 0.25rem  rgba(0, 0, 0, 0.25), 0rem 0.125rem 0.25rem  rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(0.25rem );
  border-radius: 2.188rem;
  display: flex;
  align-items: center;
  flex-direction: column;
  opacity: ${(props) => props.opacity};
  transition : all 0.05s ease-in-out;;`;

const Title = styled.span`
  font-family: 'Noto Sans KR';
  font-style: normal;
  font-weight: 700;
  font-size: 50px;
  justify-content: center;
  align-items: center;
  color: #ff8960;
`;
const SignoutButton = styled.button`
  justify-content: center;
  align-items: center;
  padding: 0.5rem 1rem;
  gap: 0.125rem;

  width: 11rem;
  height: 5rem;

  background: #ff8988;
  border-radius: 15px;
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

const ExitBtn = styled.img`
  display: flex;
  justify-content: flex-end;
  width: 1.875rem;
  height: 1.875rem;
  margin: 0.938rem;
  margin-left: 56.25rem;
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
  margin-left: 56.25rem;
  margin-top: 1rem;
`;

function FancyModalButton() {
  const [user_id, setUser_id] = useState('1');
  const [user_email, setUser_email] = useState('@');
  const [user_nickname, setUser_nickname] = useState('');

  useEffect(() => {
    axios({
      headers: {
        'access-token': `${localStorage.getItem('access-token')}`,
      },
      url: 'http://localhost:8080/api/user',
      method: 'GET',
    })
      .then((res) => {
        setUser_id(res.data.data.id);
        setUser_email(res.data.data.email);
        setUser_nickname(res.data.data.nickname);

        // console.log(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const [isOpen, setIsOpen] = useState(false);
  const [opacity, setOpacity] = useState(0);

  function toggleDelete() {
    alert('정말 회원탈퇴 하시겠습니까?');
    axios({
      headers: {
        'access-token': `${localStorage.getItem('access-token')}`,
      },
      url: 'http://localhost:8080/user/',
      method: 'DELETE',
    })
      .then((res) => {
        console.log(res);
        console.log(user_id);
      })
      .catch((err) => {
        console.log(err);
      });
    window.localStorage.clear();
    setOpacity(0);
    setIsOpen(!isOpen);
  }
  function toggleModal() {
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
      <Atag onClick={toggleModal}>마이페이지</Atag>
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
        <Title>마이페이지</Title>
        <div>
          <p>유저 아이디 (친구 추가 코드) : {user_id}</p>
          <p>유저 이메일 : {user_email}</p>
          <p>유저 닉네임 : {user_nickname}</p>
        </div>
        <Link to="/">
          <SignoutButton type="button" onClick={toggleDelete}>
            회원탈퇴
          </SignoutButton>
        </Link>
      </StyledModal>
    </div>
  );
}

const FadingBackground = styled(BaseModalBackground)`
  opacity: ${(props) => props.opacity};
  transition: all 0.3s ease-in-out;
`;

function Modal3() {
  return (
    <div className="Modal">
      <ModalProvider backgroundComponent={FadingBackground}>
        <FancyModalButton />
      </ModalProvider>
    </div>
  );
}

export default Modal3;
