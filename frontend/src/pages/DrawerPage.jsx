import styled from 'styled-components';
import { useState, useEffect } from 'react';
import ReactLoading from 'react-loading';
import diary from 'assets/diary.png';
import diarytable from 'assets/diaryTable.png';
import Navbar from 'components/Navbar';

const LoaderWrap = styled.div`
  width: 100%;
  height: 80%;
  display: flex;
  justify-content: center;
  text-align: center;
  align-items: center;
`;
const DrawerContainer = styled.div``;

const DiaryContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const Colcontainer = styled.div`
  flex: ${(props) => props.size};
`;
const Diary = styled.img`
  max-width: 240px;
  max-height: 300px;
  // margin: 40px;
`;

const DiaryTable = styled.img`
  display: flex;
  margin-bottom: 10px;
  width: 100%;
  height: 178.3px;
`;

function DrawerPage() {
  const itemList = [1, 2, 3, 4, 5, 6]; // ItemList
  const [target, setTarget] = useState(''); // target
  const [isLoding, setIsLoding] = useState(false); // isloding

  const onIntersect = async ([entry], observer) => {
    if (entry.isIntersecting && !isLoding) {
      observer.unobserve(entry.target);
      setIsLoding(true);
      // 데이터를 가져오는 부분
      setIsLoding(false);
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    let observer;
    if (target) {
      // callback 함수, option
      observer = new IntersectionObserver(onIntersect, {
        threshold: 0.4,
      });
      observer.observe(target); // 타겟 엘리먼트 지정
    }
    return () => observer && observer.disconnect();
  }, [target]);

  return (
    <div className="App">
      <Navbar />
      {itemList.map((item) => (
        <div className="Item" key={item}>
          <DrawerContainer>
            <DiaryContainer>
              <Colcontainer size={1}> </Colcontainer>
              <Colcontainer size={2}>
                <Diary src={diary} alt="diary" />
              </Colcontainer>
              <Colcontainer size={1}> </Colcontainer>
              <Colcontainer size={2}>
                <Diary src={diary} alt="diary" />
              </Colcontainer>
              <Colcontainer size={1}> </Colcontainer>
              <Colcontainer size={2}>
                <Diary src={diary} alt="diary" />
              </Colcontainer>
              <Colcontainer size={1}> </Colcontainer>
            </DiaryContainer>
            <DiaryTable src={diarytable} alt="diarytable" />
          </DrawerContainer>
        </div>
      ))}
      {isLoding ? (
        <LoaderWrap>
          <ReactLoading type="spin" color="#A593E0" />
        </LoaderWrap>
      ) : (
        ''
      )}
      <div ref={setTarget}> </div>
    </div>
  );
}

export default DrawerPage;
