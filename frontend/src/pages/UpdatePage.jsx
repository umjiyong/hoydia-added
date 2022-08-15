/* eslint-disable react/jsx-indent */
/* eslint-disable react/jsx-closing-tag-location */
/* eslint-disable react/self-closing-comp */
/* eslint-disable no-console */
/* eslint-disable react/jsx-one-expression-per-line */
/* eslint-disable no-unused-vars */
/* eslint implicit-arrow-linebreak: ["error", "beside"] */
import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from 'components/Navbar';
import Diary from 'assets/CreateDiaryBackground.png';
import FontMenu from 'components/FontMenu';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Map, MapMarker } from 'react-kakao-maps-sdk';

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

const FileImage = styled.img``;

const userId = window.localStorage.getItem('userId');
const accessToken = window.localStorage.getItem('access-token');

function updatePage() {
  const [inputs, setInputs] = useState({});
  const [fileImage, setFileImage] = useState();
  const [fileImageView, setFileImageView] = useState();
  const [fontName, setfontName] = useState('');
  const [position, setPosition] = useState({
    lat: 37.553181930643554,
    lng: 126.97290711826425,
  });
  const params = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState();
  const [titleFontStyle, setTitleFontStyle] = useState();
  const [titleFontSize, setTitleFontSize] = useState();
  const [content, setContent] = useState();
  const [contentFontStyle, setContentFontStyle] = useState();
  const [contentFontSize, setContentFontSize] = useState();
  const [bgmPath, setBgmPath] = useState();
  const [location, setLocation] = useState();

  const saveFileImage = (event) => {
    setFileImage(event.target.files[0]);
  };

  const saveFileImageView = (e) => {
    setFileImageView(URL.createObjectURL(e.target.files[0]));
  };

  const handleChange = (event) => {
    const [name, value] = [event.target.name, event.target.value];
    setInputs((values) => ({ ...values, [name]: value }));
  };

  const parentFunction = (data) => {
    setfontName(data);
  };

  function fileSubmit(event) {
    event.preventDefault();
    const url = 'http://localhost:8080/file/upload?category=image';
    const formData = new FormData();
    if (fileImage) {
      formData.append('file', fileImage);
      formData.append('fileName', fileImage.name);
    }
    const config = {
      headers: {
        'content-type': 'multipart/form-data',
      },
    };
    axios.post(url, formData, config).then((response) => {});
  }

  function pageSubmit(event) {
    event.preventDefault();
    axios({
      method: 'put',
      url: `http://localhost:8080/page/${params.pageId}`,
      headers: {
        'access-token': accessToken,
      },
      data: {
        bgmPath: 'string',
        content: inputs.content,
        contentFontSize: '20',
        contentFontStyle: fontName,
        diaryId: params.diaryId,
        location: position.lat,
        title: inputs.title,
        titleFontSize: '20',
        titleFontStyle: fontName,
      },
    }).then();
  }
  const handleSubmit = (event) => {
    event.preventDefault();
    fileSubmit(event);
    pageSubmit(event);
    navigate(`/diaryDetailPage/${params.diaryId}/${params.pageId}`);
  };

  useEffect(() => {
    axios({
      method: 'get',
      url: `http://localhost:8080/page/${params.pageId}`,
      headers: {
        'access-token': accessToken,
      },
    })
      .then((res) => {
        console.log(res);
        setfontName(res.data.data.titleFontstyle);
        setInputs({
          title: res.data.data.title,
          content: res.data.data.content,
        });
        // setTitle(res.data.data.title);
        // setTitleFontStyle(res.data.data.titleFontStyle);
        setTitleFontSize(res.data.data.titleFontSize);
        // setContent(res.data.data.content);
        // setContentFontStyle(res.data.data.contentFontStyle);
        setContentFontSize(res.data.data.contentFontSize);
        setBgmPath(res.data.data.bgmPath);
        setLocation(res.data.data.location);
      })
      .catch((res) => {});
  }, []);
  return (
    <div className="diaryEdit">
      <Navbar />

      <Container src={Diary} alt="Diary" />
      <form onSubmit={handleSubmit}>
        <MainDiv>
          <LeftDiv>
            <h1>File Upload</h1>
            <FileImage alt="image" src={fileImageView} />
            <input
              type="file"
              name="imgUpload"
              accept="image/*"
              onChange={(e) => {
                saveFileImage(e);
                saveFileImageView(e);
              }}
            />
            <div>
              폰트
              <Test>
                <FontMenu parentFunction={parentFunction} />
              </Test>
            </div>

            <Map // 지도를 표시할 Container
              center={{
                // 지도의 중심좌표
                lat: 37.553181930643554,
                lng: 126.97290711826425,
              }}
              style={{
                width: '100%',
                height: '450px',
              }}
              level={3} // 지도의 확대 레벨
              onClick={(_t, mouseEvent) => {
                setPosition({
                  lat: mouseEvent.latLng.getLat(),
                  lng: mouseEvent.latLng.getLng(),
                });
              }}
            >
              {position && <MapMarker position={position} />}
            </Map>
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
              style={{
                fontFamily: fontName,
                fontSize: 20,
              }}
            />

            <br></br>
          </RightDiv>
          <input type="submit" />
        </MainDiv>
      </form>
    </div>
  );
}

export default updatePage;
