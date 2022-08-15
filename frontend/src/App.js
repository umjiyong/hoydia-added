/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
import React, { useState, useEffect } from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from 'pages/loginPage';
import MainPage from 'pages/mainPage';
import DrawerPage from 'pages/DrawerPage'; // eslint-disable-line no-unused-vars
import DiaryEdit from 'pages/diaryEdit';
import KakaoLogin from 'pages/kakaoPage';
import CreateDiary from 'pages/createDiary';
import DiaryDetailPage from 'pages/DiaryDetailPage';
import UpdatePage from 'pages/UpdatePage';

function App() {
  // const [islogIn, setIslogIn] = useState(false);
  // useEffect(() => {
  //   const accessToken = window.localStorage.getItem('access-token');
  //   if (accessToken) {
  //     setIslogIn(true);
  //   }
  // });
  // console.log(islogIn);
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/mainpage" element={<MainPage />} />
        <Route path="/drawerpage" element={<DrawerPage />} />
        <Route path="/diaryedit/:diaryId" element={<DiaryEdit />} />
        <Route path="/kakaologin" element={<KakaoLogin />} />
        <Route path="/creatediary/:diaryId" element={<CreateDiary />} />
        <Route path="/updatepage/:diaryId/:pageId" element={<UpdatePage />} />
        <Route
          path="/diaryDetailPage/:diaryId/:pageId"
          element={<DiaryDetailPage />}
        />
      </Routes>
    </div>
  );
}

export default App;
