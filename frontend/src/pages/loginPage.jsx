/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable no-unused-vars */
/* eslint-disable react/self-closing-comp */
import React from 'react';
import styled from 'styled-components';
import Logo from 'components/Logo';
import loginDiary from 'assets/loginDiary.png';
import kakaoLogin from 'assets/kakaoLogin.png';
import naverLogin from 'assets/naverLogin.png';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Hoydia = styled.h1`
  margin: 0px;
  // font-family: 'SeoulNamsan';
  font-style: normal;
  font-weight: 400;
  font-size: 96px;
  color: #ff8960;
`;

const Slogan = styled.p`
  margin: 0px;
  // font-family: 'SeoulNamsan';
  font-style: normal;
  font-weight: 400;
  font-size: 30px;
  color: #000000;
`;

const Diary = styled.img`
  max-width: 100%;
  height: auto;
`;

const KakaoBtn = styled.img`
  width: 312px;
  height: 75px;
  border-radius: 100px;
`;

const NaverBtn = styled.img`
  width: 312px;
  height: 75px;
  border-radius: 100px;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const BtnContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 30px;
`;

function loginPage() {
  const navigate = useNavigate();
  const handleSubmit = (event) => {
    event.preventDefault();
    axios.post('http://localhost:8080/user/login', {}).then((res) => {
      console.log(res);
      console.log(res.data['access-token']);
      localStorage.setItem('access-token', res.data['access-token']);
      navigate('/temCreateDiaryPage');
    });
  };

  return (
    <div className="login">
      <Container>
        <Hoydia>HOYDIA</Hoydia>
        <Slogan>감성 페어와 공유하는 당신의 요즈음</Slogan>
        <Logo />
        <BtnContainer>
          <Link to="/mainPage">
            <KakaoBtn src={kakaoLogin} />
          </Link>

          <Link to="/mainPage">
            <NaverBtn src={naverLogin} />
          </Link>
          <form onSubmit={handleSubmit}>
            <input type="submit" />
          </form>
        </BtnContainer>
      </Container>
    </div>
  );
}

export default loginPage;
