/* eslint-disable react/jsx-indent */
/* eslint-disable react/jsx-closing-tag-location */
/* eslint-disable react/self-closing-comp */
/* eslint-disable no-console */
/* eslint-disable react/jsx-one-expression-per-line */
/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import styled from 'styled-components';
import Navbar from 'components/Navbar';
import Diary from 'assets/CreateDiaryBackground.png';
import FontMenu from 'components/FontMenu';

const MainDiv = styled.div`
  display: flex;
  // justify-content: space-between;
  align-items: center;
  margin-top: 100px;
  margin-left: 70px;
  // margin-right: 50px;
  //   background-image: url(${Diary});
  // width: 100%;
  // height: auto;
`;

const LeftDiv = styled.div`
  display: flex;

  flex-direction: column;

  gap: 50px;
  width: 50%;
`;

const RightDiv = styled.div`
  display: flex;
  margin-left: 120px;
  flex-direction: column;

  gap: 50px;
  width: 50%;
`;

const Img = styled(Diary)`
  width: 600px;
  height: 600px;
`;

const Container = styled.img`
  position: absolute;
  // top: 50px;
  // left: 200px;
  width: 100%;
  height: 93.5%;
  padding: 30px;
  z-index: -1;
  background-color: #ffca8c;
`;

// const Background = styled.div`
//   background-color: black;
// `;

// const ContainerBox = styled.div`
//   text-align: center;
// `;

const InputContent = styled.textarea`
  box-sizing: border-box;

  padding: 0.5rem 1rem;
  gap: 0.125rem;

  width: 600px;
  height: 300px;

  background: #ffffff;
  border: 2px solid #dfba88;
  border-radius: 1rem;
  resize: none;
`;

const InputTitle = styled.input`
  box-sizing: border-box;

  padding: 0.5rem 1rem;
  gap: 0.125rem;

  width: 400px;
  height: 40px;

  background: #ffffff;
  border: 2px solid #dfba88;
  border-radius: 1rem;
  font-family: ${(props) => props.fontName};
`;

const Test = styled.div`
  font-size: 10px;
`;

function diaryEdit() {
  const [inputs, setInputs] = useState({});

  const handleChange = (event) => {
    const [name, value] = [event.target.name, event.target.value];

    setInputs((values) => ({ ...values, [name]: value }));
  };

  const [fontName, setfontName] = useState('');

  const parentFunction = (data) => {
    setfontName(data);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(inputs);
  };
  return (
    <div className="diaryEdit">
      <Navbar />

      <Container src={Diary} alt="Diary" />
      <MainDiv>
        <LeftDiv>
          <div>이미지 파일</div>
          <div>
            폰트
            <Test>
              <FontMenu parentFunction={parentFunction} />
            </Test>
          </div>

          <div>카카오</div>
          <div>음악</div>
        </LeftDiv>

        <RightDiv>
          <form onSubmit={handleSubmit}>
            <p>일기장 제목</p>
            <InputTitle
              maxLength={30}
              type="text"
              name="title"
              value={inputs.title || ''}
              onChange={handleChange}
              style={{ fontFamily: fontName, fontSize: 20 }}
            />

            <p>일기장 내용</p>

            <InputContent
              type="textarea"
              name="content"
              value={inputs.content || ''}
              onChange={handleChange}
              style={{ fontFamily: fontName, fontSize: 20 }}
            />

            <br></br>
            <input type="submit" />
          </form>
        </RightDiv>
      </MainDiv>
    </div>
  );
}

export default diaryEdit;
