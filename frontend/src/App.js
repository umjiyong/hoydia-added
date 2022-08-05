/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from 'pages/loginPage';
import MainPage from 'pages/mainPage';
import DrawerPage from 'pages/DrawerPage'; // eslint-disable-line no-unused-vars
import DiaryEdit from 'pages/diaryEdit';
import KakaoLogin from 'pages/kakaoPage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/mainPage" element={<MainPage />} />
        <Route path="/drawerPage" element={<DrawerPage />} />
        <Route path="/diaryEdit" element={<DiaryEdit />} />
        <Route path="/kakaologin" element={<KakaoLogin />} />
      </Routes>
    </div>
  );
}

export default App;
