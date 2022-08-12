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
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

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

const userId = window.localStorage.getItem('userId');
const accessToken = window.localStorage.getItem('access-token');

function diaryEdit() {
  const [inputs, setInputs] = useState({});
  const [file, setFile] = useState();
  const [fontName, setfontName] = useState('');
  const params = useParams();
  const navigate = useNavigate();

  const handleChange = (event) => {
    const [name, value] = [event.target.name, event.target.value];

    setInputs((values) => ({ ...values, [name]: value }));
  };

  function fileHandleChange(event) {
    setFile(event.target.files[0]);
  }

  const parentFunction = (data) => {
    setfontName(data);
  };

  function fileSubmit(event) {
    event.preventDefault();
    const url = 'http://localhost:8080/file/upload';
    const formData = new FormData();
    formData.append('file', file);
    const config = {
      headers: {
        'content-type': 'multipart/form-data',
      },
    };
    axios.post(url, formData, config).then((response) => {
      console.log(response.data);
    });
  }

  function pageSubmit(event) {
    event.preventDefault();
    axios({
      method: 'post',
      url: 'http://localhost:8080/page',
      headers: {
        'access-token': accessToken,
      },
      data: {
        bgmPath: 'string',
        content: `${inputs.content}`,
        contentFont: 'string',
        contentFontSize: 'string',
        contentFontStyle: 'string',
        diaryId: params.diaryId,
        location: 'string',
        title: `${inputs.title}`,
        titleFont: 'string',
        titleFontSize: 'string',
        titleFontStyle: 'string',
      },
    });
  }
  const handleSubmit = (event) => {
    event.preventDefault();
    fileSubmit(event);
    pageSubmit(event);
    navigate('diaryDetailPage');
  };
  return (
    <div className="diaryEdit">
      <Navbar />

      <Container src={Diary} alt="Diary" />
      <form onSubmit={handleSubmit}>
        <MainDiv>
          <LeftDiv>
            <h1>React File Upload</h1>
            <input
              type="file"
              name="file"
              value={file || ''}
              onChange={fileHandleChange}
            />
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
          </RightDiv>
          <input type="submit" />
        </MainDiv>
      </form>
    </div>
  );
}

export default diaryEdit;
