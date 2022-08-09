/* eslint-disable react/destructuring-assignment */
import React, { useState } from 'react';
import styled from 'styled-components';
import { ReactComponent as DiaryImg } from 'assets/diary.svg';

// const DiaryInfo = {
//   color1: '#4269f5',
//   color2: '#a61f3d',
//   color3: '#d1aa2a',
//   title: 'ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ',
//   font: 'Jua',
//   fontsize: 24,
// };

const Container = styled.div`
  position: relative;
  width: 240px;
  height: 300px;
  top: 30px;
  .plus_icon {
    .first {
      fill: ${(props) => props.color1 || '#FF8960'};
    }
    .circle {
      fill: ${(props) => props.color2 || '#FF703E'};
    }
  }
`;

const Title = styled.p`
  position: absolute;
  top: 60px;
  left: 15px;
  color: ${(props) => props.color3 || 'black'};
  font-family: ${(props) => props.font}, 'sans-serif';
  font-size: ${(props) => props.fontsize - 5}px;
`;

function DiaryCompo(props) {
  return (
    <div className="DiaryCompo">
      <Container
        color1={props.DiaryInfo.color1}
        color2={props.DiaryInfo.color2}
      >
        <Title
          color3={props.DiaryInfo.color3}
          font={props.DiaryInfo.font}
          fontsize={props.DiaryInfo.fontsize}
        >
          {props.DiaryInfo.title}
        </Title>

        <DiaryImg className="plus_icon" />
      </Container>
    </div>
  );
}
export default DiaryCompo;