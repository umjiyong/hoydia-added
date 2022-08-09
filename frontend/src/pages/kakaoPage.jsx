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
        console.log(res.data['access-token']);
        window.localStorage.setItem('token', res.data['access-token']);
        navigate('/mainPage');
      } catch (e) {
        console.error(e);
        navigate('/');
      }
    })();
  }, []);
};

export default Kakao;
