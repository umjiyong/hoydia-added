/* eslint-disable react/jsx-indent */
/* eslint-disable react/jsx-closing-tag-location */
/* eslint-disable react/self-closing-comp */
/* eslint-disable no-console */
/* eslint-disable react/jsx-one-expression-per-line */
/* eslint-disable no-unused-vars */
/* eslint implicit-arrow-linebreak: ["error", "beside"] */
import React, { useState } from 'react';
import styled from 'styled-components';
import Navbar from 'components/Navbar';
import Diary from 'assets/CreateDiaryBackground.png';
import FontMenu from 'components/FontMenu';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Map, MapMarker } from 'react-kakao-maps-sdk';

const MainDiv = styled.div`
  display: flex;
  align-items: center;
  margin-left: 70px;
`;

const LeftDiv = styled.div`
  display: flex;
  flex-direction: column;
  gap: 50px;
  width: 50%;
`;

const RightDiv = styled.div`
  display: flex;
  margin-left: 140px;
  flex-direction: column;
  width: 50%;
`;

const Container = styled.img`
  position: absolute;
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
  &:active,
  &:focus {
    outline-color: #ff8960;
  }
  font-size: 20px;
`;

const InputTitle = styled.input`
  box-sizing: border-box;

  padding: 0.5rem 1rem;
  gap: 0.125rem;

  width: 600px;
  height: 40px;

  background: #ffffff;
  border: 2px solid #dfba88;
  border-radius: 1rem;
  font-family: ${(props) => props.fontName};
  &:active,
  &:focus {
    outline-color: #ff8960;
  }
  font-size: 20px;
`;

const FileImage = styled.img`
  width: 400px;
  height: 300px;
`;

const NamingDiv = styled.span``;

const SelectDiv = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-right: 50px;
  &:hover {
    cursor: pointer;
  }
  gap: 90px;
  margin-top: 50px;
`;

const ShowDiv = styled.div`
  margin-top: 100px;
`;

const LeftmainDiv = styled.div`
  position: absolute;

  top: 100px;
`;

const TitleDiv = styled.div`
  margin-top: 60px;
`;

const GoButton = styled.input`
  padding: 8px 16px;
  width: 150px;
  height: 60px;
  background: #ffdbac;
  border-radius: 15px;
  border: none;
  color: #ffffff;
  font-size: 30px;
  font-weight: 700;
  -webkit-text-stroke: 1px #ff8960;
  text-shadow: -1px 0px #ff8960, 0px 1px #ff8960, 1px 0px #ff8960,
    0px -1px #ff8960;
  &:hover {
    cursor: pointer;
    background-color: #ff8960;
  }
`;

const ButtonDiv = styled.div`
  margin-left: 450px;
  margin-top: 25px;
`;

const ContentDiv = styled.div`
  margin-top: 40px;
`;

const ImgDiv = styled.div`
  height: 200px;
  width: 50px;
  margin-bottom: 100px;
`;

function createPage() {
  const userId = window.localStorage.getItem('userId');
  const accessToken = window.localStorage.getItem('access-token');
  const [inputs, setInputs] = useState({});
  const [fileImage, setFileImage] = useState();
  const [fileImageView, setFileImageView] = useState();
  const [filebutton, setFilebutton] = useState(false);
  const [fontbutton, setFontbutton] = useState(false);
  const [mapbutton, setMapbutton] = useState(false);
  const [musicbutton, setMusicbutton] = useState(false);
  const [fontName, setfontName] = useState('');
  const [position, setPosition] = useState({
    lat: 37.553181930643554,
    lng: 126.97290711826425,
  });
  const params = useParams();
  const navigate = useNavigate();

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

  const toggleFile = (event) => {
    setFilebutton(!filebutton);
    setFontbutton(false);
    setMapbutton(false);
    setMusicbutton(false);
  };

  const toggleFont = (event) => {
    setFontbutton(!fontbutton);
    setFilebutton(false);
    setMapbutton(false);
    setMusicbutton(false);
  };

  const toggleMap = (event) => {
    setMapbutton(!mapbutton);
    setFilebutton(false);
    setFontbutton(false);
    setMusicbutton(false);
  };

  const toggleMusic = (event) => {
    setMusicbutton(!musicbutton);
    setFilebutton(false);
    setFontbutton(false);
    setMapbutton(false);
  };

  function handleSubmit(event) {
    event.preventDefault();

    axios({
      method: 'post',
      url: 'http://localhost:8080/api/page',
      headers: {
        'access-token': accessToken,
      },
      data: {
        bgmPath: 'string',
        content: inputs.content,
        contentFontSize: '20',
        contentFontStyle: fontName,
        diaryId: params.diaryId,
        locationx: position.lat,
        locationy: position.lng,
        title: inputs.title,
        titleFontSize: '20',
        titleFontStyle: fontName,
      },
    }).then((res) => {
      console.log(res);
      const url = `http://localhost:8080/api/page/image/${res.data.id}`;
      const formData = new FormData();
      formData.append('file', fileImage);
      formData.append('fileName', fileImage.name);

      const config = {
        headers: {
          'content-type': 'multipart/form-data',
        },
      };
      axios.put(url, formData, config).then((response) => {
        console.log(response);
      });
      navigate(`/diaryDetailPage/${params.diaryId}/${res.data.id}`);
    });
  }

  return (
    <div className="diaryEdit">
      <Navbar />

      <Container src={Diary} alt="Diary" />
      <form onSubmit={handleSubmit}>
        <MainDiv>
          <LeftDiv>
            <LeftmainDiv>
              <p>원하는 작업을 선택해주세요</p>

              <SelectDiv>
                <NamingDiv onClick={toggleFile}>
                  사진파일 업로드 &nbsp;{' '}
                </NamingDiv>
                <NamingDiv onClick={toggleFont}>폰트 선택 &nbsp; </NamingDiv>
                <NamingDiv onClick={toggleMap}>위치 선택 &nbsp; </NamingDiv>
                <NamingDiv onClick={toggleMusic}>음악 선택 &nbsp; </NamingDiv>
              </SelectDiv>
            </LeftmainDiv>
            <ShowDiv>
              {filebutton ? (
                <div>
                  <ImgDiv>
                    <FileImage alt="image" src={fileImageView} />
                  </ImgDiv>
                  <input
                    type="file"
                    name="imgUpload"
                    accept="image/*"
                    onChange={(e) => {
                      saveFileImage(e);
                      saveFileImageView(e);
                    }}
                  />
                </div>
              ) : null}

              {fontbutton ? <FontMenu parentFunction={parentFunction} /> : null}

              {mapbutton ? (
                <Map // 지도를 표시할 Container
                  center={{
                    // 지도의 중심좌표
                    lat: position.lat,
                    lng: position.lng,
                  }}
                  style={{
                    width: '70%',
                    height: '300px',
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
              ) : null}

              {musicbutton ? (
                <div>
                  <p>음악</p>
                </div>
              ) : null}
            </ShowDiv>
          </LeftDiv>

          <RightDiv>
            <TitleDiv>
              <p>일기장 제목</p>
              <InputTitle
                maxLength={30}
                type="text"
                name="title"
                value={inputs.title || ''}
                onChange={handleChange}
                style={{ fontFamily: fontName, fontSize: 20 }}
              />
            </TitleDiv>

            <ContentDiv>
              <p>일기장 내용</p>

              <InputContent
                type="textarea"
                name="content"
                value={inputs.content || ''}
                onChange={handleChange}
                style={{ fontFamily: fontName, fontSize: 20 }}
              />
            </ContentDiv>
            <ButtonDiv>
              <GoButton type="submit" value="저장" />
            </ButtonDiv>
          </RightDiv>
        </MainDiv>
      </form>
    </div>
  );
}

export default createPage;
