/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable no-unused-vars */
/* eslint-disable react/self-closing-comp */
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function temCreateDiaryPage() {
  const [inputs, setInputs] = useState({});

  const handleChange = (event) => {
    const [name, value] = [event.target.name, event.target.value];

    setInputs((values) => ({ ...values, [name]: value }));
  };
  const navigate = useNavigate();
  const temporarytoken = localStorage.getItem('access-token');
  const createDiaryHandleSubmit = (event) => {
    event.preventDefault();
    fetch('http://localhost:8080/diary', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'access-token': temporarytoken,
      },
      body: JSON.stringify(inputs),
    }).then((response) => console.log(response));
  };

  return (
    <div className="main">
      <form onSubmit={createDiaryHandleSubmit}>
        <p>diaryColor:</p>
        <input
          type="text"
          name="diaryColor"
          value={inputs.diaryColor || ''}
          onChange={handleChange}
        />

        <p>ownerId:</p>

        <input
          type="text"
          name="ownerId"
          value={inputs.ownerId || ''}
          onChange={handleChange}
        />

        <p>pairId:</p>

        <input
          type="text"
          name="pairId"
          value={inputs.pairId || ''}
          onChange={handleChange}
        />
        <input type="submit" />
      </form>
    </div>
  );
}

export default temCreateDiaryPage;
