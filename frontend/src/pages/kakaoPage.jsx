import { useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Kakao = () => {
  const code = new URL(window.location.href).searchParams.get('code');
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const res = await axios.post(
          `http://i7a103.p.ssafy.io:8080/api/auth/kakao?code= + ${code}`,
        );
        window.localStorage.setItem('access-token', res.data['access-token']);
        window.localStorage.setItem('userId', res.data.userId);
        const JWT_EXPIRE_TIME = 1 * 3600 * 1000;

        const onSilentRefresh = () => {
          const header = window.localStorage.getItem('access-token');
          const response = axios.post(
            'http://i7a103.p.ssafy.io:8080/api/auth/refresh',
            header,
          );
          window.localStorage.setItem(
            'access-token',
            response.data['access-token'],
          );
          window.localStorage.setItem('userId', response.data.userId);
        };
        setTimeout(onSilentRefresh, JWT_EXPIRE_TIME - 60000);
        navigate('/mainPage');
      } catch (e) {
        navigate('/');
      }
    })();
  }, []);
};

export default Kakao;
