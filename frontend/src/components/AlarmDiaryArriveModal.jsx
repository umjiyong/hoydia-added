/* eslint-disable react/jsx-no-bind */
import React from 'react';

import styled from 'styled-components';
import Modal, { ModalProvider, BaseModalBackground } from 'styled-react-modal';
import exit from 'assets/exit.png';

const StyledModal = Modal.styled`
  width: 600px;
  height 400px;
  background: #FFFFFF;
  box-shadow: 0rem 0.25rem 0.25rem  rgba(0, 0, 0, 0.25), 0rem 0.125rem 0.25rem  rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(0.25rem );
  border-radius: 2.188rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around
  opacity: ${(props) => props.opacity};
  transition : all 0.05s ease-in-out;
  `;

const Title = styled.span`
  font-style: normal;
  font-weight: 700;
  font-size: 50px;
  justify-content: center;
  align-items: center;
  color: #ff8960;
`;

const ExitBtn = styled.img`
  // display: inline-flex;
  // justify-content: flex-end;
  width: 1.875rem;
  height: 1.875rem;
  margin: 24px;
  // margin: 0.938rem;
  // margin-left: 46.875rem;
  // margin-top: 1rem;
  &:hover {
    cursor: pointer;
  }
`;

const ExitDiv = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 600px;
  // margin: 0.938rem;
  // margin-left: 46.875rem;
  // margin-top: 1rem;
`;

const Message = styled.p`
  margin: 40px 0px 30px 0px;
  font-size: 20px;
`;

function FancyModalButton({
  toggleModal,
  afterOpen,
  beforeClose,
  isOpen,
  opacity,
}) {
  return (
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
      <Title>일기장 도착!</Title>
      <Message>000과의 교환 일기가 도착하였습니다.</Message>
    </StyledModal>
  );
}

const FadingBackground = styled(BaseModalBackground)`
  opacity: ${(props) => props.opacity};
  transition: all 0.3s ease-in-out;
`;

function AlarmDiaryArriveModal(props) {
  return (
    <div className="Modal">
      <ModalProvider backgroundComponent={FadingBackground}>
        <FancyModalButton {...props} />
      </ModalProvider>
    </div>
  );
}

export default AlarmDiaryArriveModal;
