/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import styled from 'styled-components';

const FirstP = styled.p`
  font-family: 'Nanum Pen Script', sans-serif;
  font-size: 20px;
  &:hover {
    cursor: pointer;
  }
`;

const SecondP = styled.p`
  font-family: 'Black Han Sans', sans-serif;
  font-size: 20px;
  &:hover {
    cursor: pointer;
  }
`;

const ThirdP = styled.p`
  font-family: 'Jua', sans-serif;
  font-size: 20px;
  &:hover {
    cursor: pointer;
  }
`;
const FourthP = styled.p`
  font-family: 'Cute Font', sans-serif;
  font-size: 20px;
  &:hover {
    cursor: pointer;
  }
`;
const FifthP = styled.p`
  font-family: sans-serif;
  font-size: 20px;
  &:hover {
    cursor: pointer;
  }
`;

function fontMenu({ parentFunction }) {
  const togglefont1 = (event) => {
    parentFunction('Nanum Pen Script');
  };

  const togglefont2 = (event) => {
    parentFunction('Black Han Sans');
  };

  const togglefont3 = (event) => {
    parentFunction('Jua');
  };

  const togglefont4 = (event) => {
    parentFunction('Cute Font');
  };

  const togglefont5 = (event) => {
    parentFunction('sans-serif');
  };

  return (
    <div className="fontMenu">
      <FirstP onClick={togglefont1}>나만의 일기장 12345 My Diary</FirstP>
      <SecondP onClick={togglefont2}>나만의 일기장 12345 My Diary</SecondP>
      <ThirdP onClick={togglefont3}>나만의 일기장 12345 My Diary</ThirdP>
      <FourthP onClick={togglefont4}>나만의 일기장 12345 My Diary</FourthP>
      <FifthP onClick={togglefont5}>나만의 일기장 12345 My Diary</FifthP>
    </div>
  );
}
export default fontMenu;
