/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
import React, { useState, useEffect } from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from 'pages/loginPage';
import MainPage from 'pages/mainPage';
import DrawerPage from 'pages/DrawerPage'; // eslint-disable-line no-unused-vars
import DiaryEdit from 'pages/diaryEdit';
import KakaoLogin from 'pages/kakaoPage';
import DiaryDetailPage from 'pages/DiaryDetailPage';
import axios from 'axios';

function App() {
  const [islogIn, setIslogIn] = useState(false);
  const JWT_EXPIRE_TIME = 1 * 3600 * 1000;

  const onSilentRefresh = () => {
    const header = window.localStorage.getItem('access-token');
    const res = axios.post('http://localhost:8080/auth/refresh', header);
    window.localStorage.setItem('access-token', res.data['access-token']);
    window.localStorage.setItem('userId', res.data.userId);
  };
  setTimeout(onSilentRefresh, JWT_EXPIRE_TIME - 60000);

  useEffect(() => {
    const accessToken = window.localStorage.getItem('access-token');
    if (accessToken) {
      setIslogIn(true);
    }
  });
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/mainPage" element={<MainPage />} />
        <Route path="/drawerPage" element={<DrawerPage />} />
        <Route path="/diaryEdit" element={<DiaryEdit />} />
        <Route path="/kakaologin" element={<KakaoLogin />} />
        <Route path="/diaryDetailPage" element={<DiaryDetailPage />} />
      </Routes>
    </div>
  );
}

export default App;
