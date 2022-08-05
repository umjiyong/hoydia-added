/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import Login from 'pages/loginPage';
import { Route, Routes } from 'react-router-dom';
import MainPage from 'pages/mainPage';
import KakaoLogin from 'pages/kakaoPage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/mainPage" element={<MainPage />} />
        <Route path="/kakaologin" element={<KakaoLogin />} />
      </Routes>
    </div>
  );
}

export default App;
