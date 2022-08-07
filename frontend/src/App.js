/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
import React, { useState, useMemo, createContext } from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from 'pages/loginPage';
import MainPage from 'pages/mainPage';
import DrawerPage from 'pages/DrawerPage'; // eslint-disable-line no-unused-vars
import DiaryEdit from 'pages/diaryEdit';
import KakaoLogin from 'pages/kakaoPage';
import DiaryDetailPage from 'pages/DiaryDetailPage';

const UserValueContext = createContext();
const UserActionContext = createContext();

function UserProvider({ children }) {
  const [user, setUser] = useState({
    userId: '',
    nickname: '',
  });
  const actions = useMemo(
    () => ({
      login() {
        setUser((prev) => true);
      },
      logout() {
        setUser((prev) => false);
      },
    }),
    [],
  );
  return (
    <UserActionContext.Provider value={actions}>
      <UserValueContext.Provider value={user}>
        {children}
      </UserValueContext.Provider>
    </UserActionContext.Provider>
  );
}
function App() {
  return (
    <UserProvider>
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
    </UserProvider>
  );
}

export default App;
