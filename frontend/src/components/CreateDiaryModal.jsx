/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable react/jsx-no-bind */
import React, { useState } from 'react';

import styled from 'styled-components';
import Modal, { ModalProvider, BaseModalBackground } from 'styled-react-modal';
import exit from '../assets/exit.png';

const StyledModal = Modal.styled`
       

  
        width: 50.25rem;
        height: 32.25rem;
        background: #FFFFFF;
        box-shadow: 0rem 0.25rem 0.25rem  rgba(0, 0, 0, 0.25), 0rem 0.125rem 0.25rem  rgba(0, 0, 0, 0.25);
        backdrop-filter: blur(0.25rem );
        border-radius: 2.188rem;
        display: flex;
        align-items: center;
        flex-direction: column;
        opacity: ${(props) => props.opacity};
        transition : all 0.05s ease-in-out;;`;

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
  const Title = styled.span`
    font-family: 'Noto Sans KR';
    font-style: normal;
    font-weight: 700;
    font-size: 50px;
    justify-content: center;
    align-items: center;
    margin-bottom: 6.25rem;
    color: #ff8960;
  `;
  const GoButton = styled.button`
    justify-content: center;
    align-items: center;
    padding: 0.5rem 1rem;

    gap: 0.125rem;

    width: 11rem;
    height: 3.75rem;

    background: #ffdbac;
    border-radius: 0.938rem;
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
  const InputCode = styled.input`
    box-sizing: border-box;
    margin-bottom: 100px;
    padding: 0.5rem 1rem;
    gap: 0.125rem;

    width: 38.563rem;
    height: 5rem;

    background: #ffffff;
    border: 0.125rem solid #dfba88;
    border-radius: 1rem;
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
        <form>
          <InputCode
            type="text"
            placeholder="같이 일기를 만들 친구의 코드를 입력해주세요."
            autoFocus
          />
        </form>
        <GoButton type="button" onClick={toggleModal}>
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
