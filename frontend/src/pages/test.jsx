/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080/user';

function signUp() {
  const [inputs, setInputs] = useState({});

  const handleChange = (event) => {
    const [name, value] = [event.target.name, event.target.value];

    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(inputs);
    axios.post('http://localhost:8080/user/login', inputs).then((res) => {
      console.log(res);
      console.log(res.data['access-token']);
      localStorage.setItem('access-token', res.data['access-token']);
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <p>Email:</p>
      <input
        type="text"
        name="email"
        value={inputs.email || ''}
        onChange={handleChange}
      />

      <p>name:</p>

      <input
        type="text"
        name="name"
        value={inputs.name || ''}
        onChange={handleChange}
      />

      <p>platform /GOOGLE, NAVER, KAKAO/:</p>

      <input
        type="text"
        name="platform"
        value={inputs.platform || ''}
        onChange={handleChange}
      />

      <p>role /USER/:</p>

      <input
        type="text"
        name="role"
        value={inputs.role || ''}
        onChange={handleChange}
      />

      <input type="submit" />
    </form>
  );
}

export default signUp;
