/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable react/jsx-no-bind */
import React, { useState } from 'react';

import styled from 'styled-components';
import Modal, { ModalProvider, BaseModalBackground } from 'styled-react-modal';
import { Link } from 'react-router-dom';

const StyledModal = Modal.styled`
  width: 500px;
  height: 200px;
  background: #FFFFFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25), 0px 2px 4px rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(4px);
  border-radius: 35px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  opacity: ${(props) => props.opacity};
  `;

const Title = styled.span`
  font-weight: 800;
  font-size: 30px;
  justify-content: center;
  align-items: center;
  color: #ff8960;
`;

const YesButton = styled.button`
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  width: 176px;
  height: 60px;
  background: #ffdbac;
  border-radius: 15px;
  border: none;
  color: #ffffff;
  font-size: 18px;
  font-weight: 700;
  &:hover {
    cursor: pointer;
    background-color: #ff8960;
  }
`;

const NoButton = styled.button`
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  width: 176px;
  height: 60px;
  background: #ff8988;
  border-radius: 15px;
  border: none;
  color: #ffffff;
  font-size: 20px;
  font-weight: 700;
  &:hover {
    cursor: pointer;
    background: #d43e3c;
  }
`;

const Atag = styled.a`
  display: block;
  color: black;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  &:hover,
  &:active {
    cursor: pointer;
    color: #fff;
    background-color: #ff8960;
    border-radius: 10px;
  }
`;

const BtnDiv = styled.div`
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
  gap: 30px;
  flex-direction: row;
`;

function FancyModalButton() {
  const [isOpen, setIsOpen] = useState(false);
  const [opacity, setOpacity] = useState(0);

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

  function deleteLocal() {
    window.localStorage.clear();
  }
  return (
    <div>
      <Atag onClick={toggleModal}>로그아웃</Atag>
      <StyledModal
        isOpen={isOpen}
        afterOpen={afterOpen}
        beforeClose={beforeClose}
        onBackgroundClick={toggleModal}
        onEscapeKeydown={toggleModal}
        opacity={opacity}
        backgroundProps={{ opacity }}
      >
        <Title>로그아웃 하시겠습니까?</Title>

        <BtnDiv>
          <Link to="/">
            <YesButton type="button" onClick={(toggleModal, deleteLocal)}>
              네 로그아웃 합니다
            </YesButton>
          </Link>
          <NoButton type="button" onClick={toggleModal}>
            아니요
          </NoButton>
        </BtnDiv>
      </StyledModal>
    </div>
  );
}

const FadingBackground = styled(BaseModalBackground)`
  opacity: ${(props) => props.opacity};
  transition: all 0.3s ease-in-out;
`;

function Modal4() {
  return (
    <div className="Modal">
      <ModalProvider backgroundComponent={FadingBackground}>
        <FancyModalButton />
      </ModalProvider>
    </div>
  );
}

export default Modal4;
