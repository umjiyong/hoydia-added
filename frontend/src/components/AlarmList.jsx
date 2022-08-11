import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import moment from 'moment';
import axios from 'axios';
// import AlarmAnswerModal from 'components/AlarmAnswerModal';
import AlarmMatchingResultModal from './AlarmMatchingResultModal';
// import AlarmDiaryArriveModal from './AlarmDiaryArriveModal';

const Container = styled.div`
  width: 30%;
  height: 300px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #ffffff;
  border: 2px solid #dfba88;
  // box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25), 0px 2px 4px rgba(0, 0, 0, 0.25);
  // backdrop-filter: blur(4px)
  border-radius: 35px;
  // animation: fadein 0.6s;
  padding: 20px;
  z-index: 1;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Title = styled.p`
  margin: 0px;
  font-weight: 700;
`;

const Date = styled.div`
  font-weight: 700;
`;

const Detail1 = styled.a`
  display: block;
  color: black;
  text-align: center;
  padding: 0.875rem 1rem;
  text-decoration: none;
  &:hover {
    cursor: pointer;
  }
`;

function AlarmList() {
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

  useEffect(() => {
    axios({
      headers: {
        'access-token': `${localStorage.getItem('access-token')}`,
      },
      url: 'http://localhost:8080/notice',
      method: 'GET',
    })
      .then((res) => {
        console.log(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  });
  return (
    <div className="AlarmList">
      <Container>
        <Header>
          <Title>알림함</Title>
          <Date>{moment().format('YYYY. M. D (ddd)')}</Date>
        </Header>
        <Detail1 onClick={toggleModal}>Answer1</Detail1>
      </Container>
      {/* <AlarmAnswerModal
        toggleModal={toggleModal}
        afterOpen={afterOpen}
        beforeClose={beforeClose}
        isOpen={isOpen}
        opacity={opacity}
      /> */}
      <AlarmMatchingResultModal
        toggleModal={toggleModal}
        afterOpen={afterOpen}
        beforeClose={beforeClose}
        isOpen={isOpen}
        opacity={opacity}
      />
      {/* <AlarmDiaryArriveModal
        toggleModal={toggleModal}
        afterOpen={afterOpen}
        beforeClose={beforeClose}
        isOpen={isOpen}
        opacity={opacity}
      /> */}
    </div>
  );
}
export default AlarmList;
