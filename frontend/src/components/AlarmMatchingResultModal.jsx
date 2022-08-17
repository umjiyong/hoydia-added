/* eslint-disable react/jsx-no-bind */
import React from 'react';
import styled from 'styled-components';
import Modal, { ModalProvider, BaseModalBackground } from 'styled-react-modal';
import exit from 'assets/exit.png';
import pairHedgehogs from 'assets/pairHedgehogs.png';

const StyledModal = Modal.styled`
  width: 600px;
  height 410px;
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
  font-weight: 800;
  font-size: 50px;
  justify-content: center;
  align-items: center;
  color: #ff8960;
`;

const ExitDiv = styled.div`
  width: 1.875rem;
  height: 1.875rem;
  margin: 24px 24px 0px 545px;
`;

const ExitBtn = styled.img`
  width: 1.875rem;
  height: 1.875rem;
  &:hover {
    cursor: pointer;
  }
`;

const Mascot = styled.img`
  width: 300px;
  height: auto;
`;

const Message = styled.p`
  margin: 20px 0px 30px 0px;
  font-size: 20px;
`;

function FancyModalButton({
  toggleModal,
  afterOpen,
  beforeClose,
  isOpen,
  opacity,
  propsContent,
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
      <Title>매칭결과</Title>
      <Mascot src={pairHedgehogs} />
      <Message>{propsContent}</Message>
    </StyledModal>
  );
}

const FadingBackground = styled(BaseModalBackground)`
  opacity: ${(props) => props.opacity};
  transition: all 0.3s ease-in-out;
`;

function AlarmMatchingResultModal(props) {
  return (
    <div className="Modal">
      <ModalProvider backgroundComponent={FadingBackground}>
        <FancyModalButton {...props} />
      </ModalProvider>
    </div>
  );
}

export default AlarmMatchingResultModal;
