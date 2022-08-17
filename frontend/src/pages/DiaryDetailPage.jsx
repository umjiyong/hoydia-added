import { useEffect, useState } from 'react';
import Navbar from 'components/Navbar';
import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';
import DiaryPaper from 'assets/DiaryPaper.png';
import createpagebtn from 'assets/CreatePageBtn.png';
import sendbtn from 'assets/SendBtn.png';
import editbtn from 'assets/EditBtn.png';
import updatebtn from 'assets/UpdateBtn.png';
import KakaoMapModal from 'components/KakaoMapModal';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretLeft, faCaretRight } from '@fortawesome/free-solid-svg-icons';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ButtonDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  position: absolute;
  top: 64px;
  left: 950px;
`;

const DetailSendBtn = styled.img`
  // position: absolute;
  // top: 9.8%;
  // right: 39%;
  z-index: 2;
  width: 68px;
  height: 40px;
  cursor: pointer;
`;

const DetailCreateBtn = styled.img`
  // display: flex;
  // justify-content
  // position: absolute;
  // top: 9.8%;
  // right: 34%;
  z-index: 2;
  width: 68px;
  height: 40px;
  cursor: pointer;
`;

const DetailEditBtn = styled.img`
  // position: absolute;
  // top: 9.8%;
  // right: 29%;
  z-index: 2;
  width: 68px;
  height: 40px;
  cursor: pointer;
`;

const DetailUpdateBtn = styled.img`
  // position: absolute;
  // top: 9.8%;
  // right: 24%;
  // z-index: 2;
  width: 68px;
  height: 40px;
  cursor: pointer;
`;

const DiaryContainer = styled.div`
  position: relative;
  background-color: #ff8960;
  width: 1200px;
  height: 650px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 35px;
  margin: 20px; 0px; 20px; 0px;
  z-index: -2;
  // padding: 60px 76px 26px 76px;
`;

const DiaryPaperImg = styled.img`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  // display: grid;
  // height: 100%;
  // grid-template-columns: 1fr 1fr;
  // grid-gap: 0.25rem;
  // background-image: url(${DiaryPaper});
  width: 1050px;
  height: 640px;
  z-index: -1;
  // background-repeat: no-repeat;
  // background-size: cover;
  // padding-bottom: 10px;
`;

const MainDiv = styled.div`
  display: flex;
  height: 600px;
`;

const LeftDiv = styled.div`
  // background-color: red;
  width: 50%;
  height: 650px;
  position: relative;
  display: flex;
  // height: 100%;
  flex-direction: column;
  align-items: center;
  gap: 60px;
  // justify-content: space-around;
  margin-top: 30px;
  margin-left: 15px;
`;

const RightDiv = styled.div`
  // background-color: blue;
  width: 50%;
  height: 500px;
  // position: relative;
  // display: flex;
  // height: 100%;
  // flex-direction: column;
  // justify-content: space-around;
  // align-items: center;
  margin-top: 95px;
  margin-left: 15px;
`;

const ImageBox = styled.img`
  // position: absolute;
  // top: 10%;
  // left: 18%;
  margin-top: 65px;
  width: 400px;
  height: 300px;
  // border: solid 1px red;
`;

const MusicPlayer = styled.div`
  width: 400px;
  height: 180px;
`;

const CaretLeftBtn = styled.div`
  position: absolute;
  width: 48px;
  height: 96px;
  top: 45%;
  left: 8%;
  cursor: pointer;
`;

const CaretRightBtn = styled.div`
  position: absolute;
  width: 48px;
  height: 96px;
  top: 45%;
  right: 8%;
  cursor: pointer;
`;

const TitleDiv = styled.textarea`
  margin-left: 70px;
  margin-bottom: 10px;
  width: 400px;
  height: 50px;
  border: 2px solid #dfba88;
  border-radius: 16px;
  resize: none;
  &:active,
  &:focus {
    outline-color: #ff8960;
  }
  line-height: 50px;
  padding-left: 10px;
  overflow: hidden;
`;

const ContentDiv = styled.textarea`
  margin-left: 70px;
  width: 400px;
  height: 400px;
  border: 2px solid #dfba88;
  border-radius: 16px;
  margin-top: 5px;
  resize: none;
  &:active,
  &:focus {
    outline-color: #ff8960;
  }
  padding-left: 10px;
  padding-top: 20px;
`;

function DetailPage() {
  const userId = window.localStorage.getItem('userId');
  const accessToken = window.localStorage.getItem('access-token');
  const navigate = useNavigate();
  const params = useParams();
  const [diaryColor, setDiaryColor] = useState();
  const [pageList, setPageList] = useState([]);
  const [title, setTitle] = useState();
  const [titleFontStyle, setTitleFontStyle] = useState();
  const [titleFontSize, setTitleFontSize] = useState();
  const [content, setContent] = useState();
  const [contentFontStyle, setContentFontStyle] = useState();
  const [contentFontSize, setContentFontSize] = useState();
  const [bgmPath, setBgmPath] = useState();
  const [imageUrl, setImageUrl] = useState();
  const [location, setLocation] = useState({
    locationx: '',
    locationy: '',
  });
  const [overLeft, setOverLeft] = useState(false);
  const [overRight, setOverRight] = useState(false);

  const sendDiary = () => {
    axios({
      method: 'put',
      url: `/page/send/${params.pageId}`,
      headers: {
        'access-token': accessToken,
      },
      data: {
        diaryId: params.diaryId,
      },
    }).then((res) => {
      console.log(res);
      navigate('/mainpage');
    });
  };
  const createDiary = () => {
    navigate(`/createDiary/${params.diaryId}`);
  };
  const editDiary = () => {
    navigate(`/diaryEdit/${params.diaryId}`);
  };
  const updatePage = () => {
    navigate(`/updatepage/${params.diaryId}/${params.pageId}`);
  };
  const pageListLoading = () => {
    axios({
      method: 'get',
      url: `/page/diary/${params.diaryId}`,
      headers: {
        'access-token': accessToken,
      },
    })
      .then((res) => {
        setPageList(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  const pageLoading = () => {
    axios({
      method: 'get',
      url: `/page/${params.pageId}`,
      headers: {
        'access-token': accessToken,
      },
    })
      .then((res) => {
        setTitle(res.data.data.title);
        setTitleFontStyle(res.data.data.titleFontStyle);
        setTitleFontSize(res.data.data.titleFontSize);
        setContent(res.data.data.content);
        setContentFontStyle(res.data.data.contentFontStyle);
        setContentFontSize(res.data.data.contentFontSize);
        setBgmPath(res.data.data.bgmPath);
        setImageUrl(res.data.data.imgPath);
        setLocation({
          locationx: res.data.data.locationx,
          locationy: res.data.data.locationy,
        });
      })
      .catch((res) => {});
  };

  const diaryLoading = () => {
    axios({
      method: 'get',
      url: `/diary/${params.diaryId}`,
      headers: {
        'access-token': accessToken,
      },
    })
      .then((res) => {
        setDiaryColor(res.data.data.diaryColor);
      })
      .catch((res) => {});
  };

  useEffect(() => {
    pageListLoading();
    pageLoading();
    diaryLoading();
  }, []);

  const pageOverLeft = () => {
    pageList.forEach((item, idx) => {
      if (item.id === params.pageId && pageList.length - 1 > idx) {
        navigate(`/diarydetailpage/${params.diaryId}/${pageList[idx + 1].id}`);
        window.location.reload();
      }
    }, []);
  };
  const pageOverRight = () => {
    pageList.forEach((item, idx) => {
      if (item.id === params.pageId && pageList.length - 1 > idx >= 0) {
        navigate(`/diarydetailpage/${params.diaryId}/${pageList[idx - 1].id}`);
        window.location.reload();
      }
    }, []);
  };

  return (
    <div className="diaryDetailPage">
      <Navbar />
      <KakaoMapModal propLocation={location} />
      <Container>
        <ButtonDiv>
          <DetailSendBtn onClick={sendDiary} src={sendbtn} alt="sendbtn" />
          <DetailCreateBtn
            onClick={createDiary}
            s
            src={createpagebtn}
            alt="createpagebtn"
          />
          <DetailUpdateBtn
            onClick={updatePage}
            src={updatebtn}
            alt="updatebtn"
          />
          <DetailEditBtn onClick={editDiary} src={editbtn} alt="editbtn" />
        </ButtonDiv>
        {/* <CaretLeftBtn
          onClick={() => {
            pageOverLeft();
          }}
          onMouseOver={() => setOverLeft(true)}
          onMouseLeave={() => setOverLeft(false)}
          >
          <FontAwesomeIcon
          size="6x"
          icon={faCaretLeft}
          style={overLeft ? { color: '#FF8960' } : { color: '#FFDBAC' }}
          />
        </CaretLeftBtn> */}
        <DiaryContainer style={{ backgroundColor: diaryColor }}>
          <DiaryPaperImg src={DiaryPaper} alt="DiaryPaper" />
          <MainDiv>
            <LeftDiv>
              <ImageBox src={imageUrl} onerror="this.style.display='none'" />
              <MusicPlayer>
                <AudioPlayer
                  // autoPlay
                  loop
                  onPlay={(e) => console.log('onPlay')}
                  volume={0.1}
                  src="https://hoydia-bucket.s3.ap-northeast-2.amazonaws.com/test/%EB%85%B9%EC%9D%8C_1660283408316.m4a"
                  showJumpControls={false}
                />
              </MusicPlayer>
            </LeftDiv>
            <RightDiv>
              <TitleDiv
                type="text"
                name="title"
                value={title}
                readOnly
                style={{ fontFamily: titleFontStyle, fontSize: titleFontSize }}
              >
                {/* {title} */}
                {/* 일기장 */}
              </TitleDiv>
              <ContentDiv
                type="text"
                name="content"
                value={content}
                readOnly
                style={{
                  fontFamily: contentFontStyle,
                  fontSize: contentFontSize,
                }}
              >
                {/* 일기장 내용~! */}
                {/* {content} */}
              </ContentDiv>
            </RightDiv>
          </MainDiv>
        </DiaryContainer>
        {/* <CaretRightBtn
          onClick={() => {
            pageOverRight();
          }}
          onMouseOver={() => setOverRight(true)}
          onMouseLeave={() => setOverRight(false)}
        >
          <FontAwesomeIcon
            size="6x"
            icon={faCaretRight}
            style={overRight ? { color: '#FF8960' } : { color: '#FFDBAC' }}
          />
        </CaretRightBtn> */}
      </Container>
    </div>
  );
}

export default DetailPage;
