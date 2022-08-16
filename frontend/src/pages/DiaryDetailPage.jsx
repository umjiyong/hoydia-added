import { useEffect, useState } from 'react';
import Navbar from 'components/Navbar';
import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';
import Diary from 'assets/CreateDiaryBackground.png';
import createpagebtn from 'assets/CreatePageBtn.png';
import sendbtn from 'assets/SendBtn.png';
import editbtn from 'assets/EditBtn.png';
import UpdateBtn from 'assets/UpdateBtn.png';
import KakaoMapModal from 'components/KakaoMapModal';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretLeft, faCaretRight } from '@fortawesome/free-solid-svg-icons';

const MusicPlayer = styled.div`
  width: 300px;
  height: 170px;
`;

const DetailSendBtn = styled.div`
  position: absolute;
  top: 9.8%;
  right: 39%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${sendbtn});
  background-repeat: no-repeat;
  background-size: 100%;
  cursor: pointer;
`;

const DetailCreateBtn = styled.div`
  position: absolute;
  top: 9.8%;
  right: 34%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${createpagebtn});
  background-repeat: no-repeat;
  background-size: 100%;
  cursor: pointer;
`;

const DetailEditBtn = styled.div`
  position: absolute;
  top: 9.8%;
  right: 29%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${editbtn});
  background-repeat: no-repeat;
  background-size: 100%;
  cursor: pointer;
`;
const DetailUpdateBtn = styled.div`
  position: absolute;
  top: 9.8%;
  right: 24%;
  z-index: 2;
  width: 70px;
  height: 47px;
  background-image: url(${UpdateBtn});
  background-repeat: no-repeat;
  background-size: 100%;
  cursor: pointer;
`;

const DetailContainer = styled.div`
  position: absolute;
  bottom: 4%;
  left: 14%;
  background-color: #ff8960;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 35px;
  width: 72%;
  height: 88%;
  padding: 60px 76px 26px 76px;
`;

const MainDiv = styled.div`
  display: grid;
  height: 100%;
  grid-template-columns: 1fr 1fr;
  grid-gap: 0.25rem;
  background-image: url(${Diary});
  background-repeat: no-repeat;
  background-size: cover;
  padding-bottom: 10px;
`;

const LeftDiv = styled.div`
  display: flex;
  height: 100%;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
`;

const RightDiv = styled.div`
  display: flex;
  height: 100%;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
`;
const ImageBox = styled.img`
  margin-top: 50px;
  width: 300px;
  height: 300px;
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
const TitleDiv = styled.div``;
const ContentDiv = styled.div``;

function DetailPage() {
  const userId = window.localStorage.getItem('userId');
  const accessToken = window.localStorage.getItem('access-token');
  const navigate = useNavigate();
  const params = useParams();
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
      url: `http://localhost:8080/api/page/send/${params.pageId}`,
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
      url: `http://localhost:8080/api/page/diary/${params.diaryId}`,
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
      url: `http://localhost:8080/api/page/${params.pageId}`,
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

  useEffect(() => {
    pageListLoading();
    pageLoading();
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
      <DetailSendBtn onClick={sendDiary} />
      <DetailCreateBtn onClick={createDiary} />
      <DetailEditBtn onClick={editDiary} />
      <DetailUpdateBtn onClick={updatePage} />
      <KakaoMapModal propLocation={location} />
      <CaretLeftBtn
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
      </CaretLeftBtn>
      <DetailContainer>
        <MainDiv>
          <LeftDiv>
            <ImageBox src={imageUrl} alt="ImageBox" />
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
              style={{ fontFamily: titleFontStyle, fontSize: titleFontSize }}
            >
              {title}
            </TitleDiv>
            <ContentDiv
              type="text"
              name="content"
              style={{
                fontFamily: contentFontStyle,
                fontSize: contentFontSize,
              }}
            >
              {content}
            </ContentDiv>
          </RightDiv>
        </MainDiv>
      </DetailContainer>
      <CaretRightBtn
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
      </CaretRightBtn>
    </div>
  );
}

export default DetailPage;
