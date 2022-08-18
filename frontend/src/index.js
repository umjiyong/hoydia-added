/* eslint-disable import/order */
/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import ReactDOM from 'react-dom/client';
import 'index.css';
import App from 'App';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';

// axios.defaults.baseURL = 'http://i7a103.p.ssafy.io:8080/api';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
);
