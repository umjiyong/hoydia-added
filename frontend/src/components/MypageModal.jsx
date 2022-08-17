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
import { CopyToClipboard } from 'react-copy-to-clipboard';
import { BiCopy } from 'react-icons/bi';

const StyledModal = Modal.styled`
  width: 50.25rem;
  height: 30rem;
  background: #FFFFFF;
  box-shadow: 0rem 0.25rem 0.25rem  rgba(0, 0, 0, 0.25), 0rem 0.125rem 0.25rem  rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(0.25rem );
  border-radius: 2.188rem;
  display: flex;
  align-items: center;
  flex-direction: column;
  opacity: ${(props) => props.opacity};
  transition : all 0.05s ease-in-out;;
  `;

const Title = styled.span`
  font-weight: 800;
  font-size: 50px;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  color: #ff8960;
`;
// const Line = styled.hr`
//   border: 1px solid red;
// `;

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

const UserInfo = styled.div`
  font-size: 22px;
`;

const AddFriends = styled.div`
  display: flex;
`;

const UserEmail = styled.p`
  margin-bottom: 40px;
`;

const EditNickname = styled.div`
  display: flex;
`;

const UserNickname = styled.p`
  margin: 12px 5px 0px 0px;
`;

const InputDiv = styled.div`
  text-align: center;
  margin: auto;
`;

const InputNickname = styled.input`
  width: 200px;
  height: 50px;
  background: #ffffff;
  border: 0.125rem solid #dfba88;
  border-radius: 1rem;
  font-size: 2zpx;
  &:active,
  &:focus {
    outline-color: #ff8960;
  }
  &::placeholder {
    color: #888888;
  }
`;

const EditButton = styled.button`
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  margin-left: 20px;
  width: 110px;
  height: 50px;
  background: #ffdbac;
  border-radius: 15px;
  border: none;
  color: #ffffff;
  font-size: 20px;
  font-weight: 700;
  // text-shadow: -1px 0px #ff8960, 0px 1px #ff8960, 1px 0px #ff8960,
  //   0px -1px #ff8960;
  // -webkit-text-stroke: 1px #ff8960;
  &:hover {
    cursor: pointer;
    background-color: #ff8960;
  }
`;

const UnregisterDiv = styled.div`
  display: flex;
  gap: 20px;
  margin-top: 40px;
`;

const Unregister = styled.p`
  font-size: 20px;
`;

const SignoutButton = styled.button`
  justify-content: center;
  align-items: center;
  margin-top: 5px;
  padding: 8px 16px;
  width: 140px;
  height: 50px;
  background: #ff8988;
  border-radius: 15px;
  border: none;
  color: #ffffff;
  font-size: 20px;
  font-weight: 700;
  // -webkit-text-stroke: 1px #d43e3c;
  &:hover {
    cursor: pointer;
    background: #d43e3c;
  }
`;

function FancyModalButton() {
  const [user_id, setUser_id] = useState('1');
  const [user_email, setUser_email] = useState('@');
  const [user_nickname, setUser_nickname] = useState('');
  const [change_nickname, setChange_nickname] = useState('');

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
        setChange_nickname(res.data.data.nickname);

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
      url: 'http://localhost:8080/api/user/',
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

  const handleChange = (event) => {
    setChange_nickname(event.target.value);
  };

  function toggleUpdate() {
    axios({
      headers: {
        'access-token': `${localStorage.getItem('access-token')}`,
      },
      url: `http://localhost:8080/api/user/${user_id}`,
      method: 'PUT',
      data: {
        nickname: change_nickname,
      },
    })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
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
        <UserInfo>
          <AddFriends>
            <p>친구 추가 코드 : {user_id}</p>
            <CopyToClipboard text={user_id}>
              <BiCopy
                size="35px"
                cursor="pointer"
                style={{ margin: '10px 0px 0px 7px' }}
              />
            </CopyToClipboard>
          </AddFriends>
          <UserEmail>유저 이메일 : {user_email}</UserEmail>
          <EditNickname>
            <UserNickname>유저 닉네임 :</UserNickname>
            <InputDiv>
              <InputNickname value={change_nickname} onChange={handleChange} />
            </InputDiv>
            <EditButton onClick={toggleUpdate}>저장</EditButton>
          </EditNickname>
        </UserInfo>
        <UnregisterDiv>
          <Unregister>정말로 오이디아를 떠나시나요?</Unregister>
          <Link to="/">
            <SignoutButton type="button" onClick={toggleDelete}>
              회원탈퇴
            </SignoutButton>
          </Link>
        </UnregisterDiv>
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
