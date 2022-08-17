/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable no-unused-vars */
/* eslint-disable react/self-closing-comp */
import React from 'react';
import styled from 'styled-components';
import Logo from 'components/Logo';
import kakaoLogin from 'assets/kakao_login.png';
import { GoogleLogin, GoogleOAuthProvider } from '@react-oauth/google';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import mainBack from 'assets/Mainbackground.png';

const Main = styled.img`
  position: absolute;
  width: 1536px; //빡빡하게 맞춰야함 안에 컴포 움직이면 필수 수정
  height: 778px;
  z-index: -1;
`;
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
  margin-bottom: 30px;
`;

const KakaoBtn = styled.img`
  width: 300px;
  height: 45px;
  border-radius: 100px;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
`;

const BtnContainer = styled.div`
  display: flex;
  // flex-direction: column;
  gap: 120px;
  margin-top: 30px;
`;

function loginPage() {
  const navigate = useNavigate();
  const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
  const REDIRECT_URI = 'http://localhost:3000/kakaoLogin';
  const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
  return (
    <div className="login">
      <Container>
        <Main src={mainBack} />
        <Hoydia>HOYDIA</Hoydia>
        <Slogan>감성 페어와 공유하는 당신의 요즈음</Slogan>
        <div style={{ marginLeft: '90px' }}>
          <Logo />
        </div>
        <BtnContainer>
          <GoogleOAuthProvider
            clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID}
          >
            <GoogleLogin
              onSuccess={(credentialResponse) => {
                const authRequest = {
                  accessToken: credentialResponse.credential,
                };
                const async = async () => {
                  try {
                    const res = await axios.post('/auth/google', authRequest);
                    window.localStorage.setItem(
                      'access-token',
                      res.data['access-token'],
                    );
                    window.localStorage.setItem('userId', res.data.userId);
                    const JWT_EXPIRE_TIME = 1 * 3600 * 1000;

                    const onSilentRefresh = () => {
                      const header =
                        window.localStorage.getItem('access-token');
                      const response = axios.post('/auth/refresh', header);
                      window.localStorage.setItem(
                        'access-token',
                        response.data['access-token'],
                      );
                      window.localStorage.setItem(
                        'userId',
                        response.data.userId,
                      );
                    };
                    setTimeout(onSilentRefresh, JWT_EXPIRE_TIME - 60000);
                    navigate('/mainPage');
                  } catch (e) {
                    console.error(e);
                    navigate('/');
                  }
                };
                async();
              }}
              onError={() => {
                console.log('Login Failed');
              }}
              width="300"
              theme="filled_blue"
              shape="circle"
            />
          </GoogleOAuthProvider>
          <a href={KAKAO_AUTH_URI}>
            <KakaoBtn src={kakaoLogin} />
          </a>
        </BtnContainer>
      </Container>
    </div>
  );
}

export default loginPage;
