import React from 'react';
import styled from 'styled-components';
import moment from 'moment';

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
`;

const Date = styled.div`
  font-weight: bold;
`;

function AlarmList() {
  return (
    <div className="AlarmList">
      <Container>
        <Header>
          <Title>알림함</Title>
          <Date>{moment().format('YYYY. M. D (ddd)')}</Date>
        </Header>
      </Container>
    </div>
  );
}
export default AlarmList;
