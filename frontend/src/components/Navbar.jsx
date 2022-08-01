/* eslint-disable no-unused-vars */
import React from 'react';
import styled from 'styled-components';
import Modal1 from './Modal1';
import Modal2 from './Modal2';
import Modal3 from './Modal3';
import Modal4 from './Modal4';

const UlTag = styled.ul`
  display: flex;
  justify-content: space-between;
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #ffdbac;
`;
const LiTag = styled.li`
  float: left;
`;

const Atag = styled.a`
  display: block;
  color: black;
  text-align: center;
  padding: 0.875rem 0.875rem;
  text-decoration: none;
`;

function Navbar() {
  return (
    <UlTag>
      <LiTag>
        <Atag href="">LOGO</Atag>
      </LiTag>
      <LiTag>
        <Modal1 />
      </LiTag>
      <LiTag>
        <Modal2 />
      </LiTag>
      <LiTag>
        <Modal3 />
      </LiTag>
      <LiTag>
        <Modal4 />
      </LiTag>
    </UlTag>
  );
}

export default Navbar;
