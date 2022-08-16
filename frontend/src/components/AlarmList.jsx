/* eslint-disable dot-notation */
/* eslint-disable react/destructuring-assignment */
/* eslint-disable array-callback-return */
import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import moment from 'moment';
import axios from 'axios';
import AlarmAnswerModal from 'components/AlarmAnswerModal';
import AlarmMatchingResultModal from './AlarmMatchingResultModal';
import AlarmDiaryArriveModal from './AlarmDiaryArriveModal';

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
  const [noticeList, setnoticeList] = useState();
  const [test, setTest] = useState();
  const [selectName, setSelectName] = useState();
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

  function toggleoff() {
    setOpacity(0);
    setIsOpen(!isOpen);
  }
  function toggleModal(item) {
    setOpacity(0);
    setIsOpen(!isOpen);
    setTest(item.substring(12));

    if (item.includes('매칭중!')) {
      setSelectName(1);
    } else if (item.includes('교환 일기 만들기')) {
      setSelectName(2);
    } else if (item.includes('일기 교환')) {
      setSelectName(3);
    }
  }

  useEffect(() => {
    axios({
      headers: {
        'access-token': `${localStorage.getItem('access-token')}`,
      },
      url: 'http://localhost:8080/api/notice',
      method: 'GET',
    })
      .then((res) => {
        setnoticeList(res.data.data);
        console.log(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  // const b = mainNotice.data.map((item, index) => item.title);
  return (
    <div className="AlarmList">
      <Container>
        <Header>
          <Title>알림함</Title>
          <Date>{moment().format('YYYY. M. D (ddd)')}</Date>
        </Header>
        <div>
          {noticeList &&
            noticeList.map((item, index) => (
              <Detail1 key={index} onClick={() => toggleModal(item.title)}>
                {item['title'].length > 20
                  ? item['title'].substring(0, 4)
                  : item['title']}
              </Detail1>
            ))}
        </div>
        {/* <Detail1 onClick={toggleModal}>Answer1</Detail1> */}
      </Container>
      {selectName === 1 ? (
        <AlarmAnswerModal
          toggleModal={toggleoff}
          afterOpen={afterOpen}
          beforeClose={beforeClose}
          isOpen={isOpen}
          opacity={opacity}
          test={test}
        />
      ) : null}
      {selectName === 2 ? (
        <AlarmMatchingResultModal
          toggleModal={toggleModal}
          afterOpen={afterOpen}
          beforeClose={beforeClose}
          isOpen={isOpen}
          opacity={opacity}
        />
      ) : null}
      {selectName === 3 ? (
        <AlarmDiaryArriveModal
          toggleModal={toggleoff}
          afterOpen={afterOpen}
          beforeClose={beforeClose}
          isOpen={isOpen}
          opacity={opacity}
        />
      ) : null}
    </div>
  );
}

export default AlarmList;
