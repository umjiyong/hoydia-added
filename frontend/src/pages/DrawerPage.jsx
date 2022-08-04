import styled from 'styled-components';
import React, { useState, useEffect, useRef } from 'react';
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
  const [itemList, setItemList] = useState([1, 2, 3, 4, 5, 6]); // ItemList
  const [list, setList] = useState([
    <Diary src={diary} alt="diary1" />,
    <Diary src={diary} alt="diary2" />,
    <Diary src={diary} alt="diary3" />,
    <Diary src={diary} alt="diary4" />,
    <Diary src={diary} alt="diary5" />,
  ]);
  const [target, setTarget] = useState(''); // target
  const [isLoding, setIsLoding] = useState(false); // isloding
  const dragItem = useRef();
  const dragOverItem = useRef();
  const dragStart = (e, position) => {
    dragItem.current = position;
    console.log(e.type);
  };
  const dragEnter = (e, position) => {
    dragOverItem.current = position;
    console.log(e.type);
  };
  const drop = (e) => {
    const copyListItems = [...list];
    const dragItemContent = copyListItems[dragItem.current];
    copyListItems.splice(dragItem.current, 1);
    copyListItems.splice(dragOverItem.current, 0, dragItemContent);
    dragItem.current = null;
    dragOverItem.current = null;
    setList(copyListItems);
    console.log(e.type);
  };
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
      <DrawerContainer
        style={{
          display: 'grid',
          gridTemplateRows: '1fr ',
          gridTemplateColumns: '1fr 1fr 1fr',
        }}
      >
        {list &&
          list.map((item, index) => (
            <Colcontainer
              size={2}
              className="Item"
              key={index}
              onDragStart={(e) => dragStart(e, index)}
              onDragEnter={(e) => dragEnter(e, index)}
              onDragOver={(e) => e.preventDefault()}
              onDragEnd={drop}
              draggable
            >
              {item}
              {/* <DrawerContainer>
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
            </DrawerContainer> */}
            </Colcontainer>
          ))}
        {isLoding ? (
          <LoaderWrap>
            <ReactLoading type="spin" color="#A593E0" />
          </LoaderWrap>
        ) : (
          ''
        )}
        <div ref={setTarget}> </div>
      </DrawerContainer>
    </div>
  );
}

export default DrawerPage;
