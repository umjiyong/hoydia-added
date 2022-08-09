import { useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Kakao = () => {
  const code = new URL(window.location.href).searchParams.get('code');
  console.log(code);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const res = await axios.post(
          `http://localhost:8080/auth/kakao?code= + ${code}`,
        );
        window.localStorage.setItem('access-token', res.data['access-token']);
        window.localStorage.setItem('userId', res.data.userId);
        navigate('/mainPage');
      } catch (e) {
        console.error(e);
        navigate('/');
      }
    })();
  }, []);
};

export default Kakao;
