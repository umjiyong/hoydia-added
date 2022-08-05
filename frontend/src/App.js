/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from 'pages/loginPage';
import MainPage from 'pages/mainPage';
<<<<<<< HEAD
import DrawerPage from 'pages/DrawerPage'; // eslint-disable-line no-unused-vars
=======
import DiaryEdit from 'pages/diaryEdit';
>>>>>>> 2350d2451dc4b843084cd04e524d5d735bc7cd68

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/mainPage" element={<MainPage />} />
<<<<<<< HEAD
        <Route path="/drawerPage" element={<DrawerPage />} />
=======
        <Route path="/diaryEdit" element={<DiaryEdit />} />
>>>>>>> 2350d2451dc4b843084cd04e524d5d735bc7cd68
      </Routes>
    </div>
  );
}

export default App;
