// import { useEffect } from 'react';
// import axios from 'axios';

const Kakao = () => {
  const code = new URL(window.location.href).searchParams.get('code');
  console.log(code);

  //   useEffect(() => {
  //     axios.post('http://localhost:8080/user/login', code).then((res) => {
  //       console.log(res);
  //     });
  //   });
};

export default Kakao;
