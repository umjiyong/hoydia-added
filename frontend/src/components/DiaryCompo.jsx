/* eslint-disable react/destructuring-assignment */
import React, { useState } from 'react';
import styled from 'styled-components';
import { ReactComponent as DiaryImg } from 'assets/diary.svg';

const Container = styled.div`
  position: relative;
  width: 240px;
  height: 300px;
  cursor: pointer;
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
  top: 30px;
  left: 15px;
  color: ${(props) => props.color3 || 'black'};
  font-family: ${(props) => props.font}, 'sans-serif';
  font-size: ${(props) => props.fontsize - 5}px;
`;

function DiaryCompo(props) {
  return (
    <div className="DiaryCompo">
      <Container
        color1={props.DiaryInfo.diaryColor}
        color2={props.DiaryInfo.buttonColor}
      >
        <Title
          color3={props.DiaryInfo.fontColor}
          font={props.DiaryInfo.fontStyle}
          fontsize={props.DiaryInfo.fontSize}
        >
          {props.DiaryInfo.title}
        </Title>

        <DiaryImg className="plus_icon" />
      </Container>
    </div>
  );
}
export default DiaryCompo;
