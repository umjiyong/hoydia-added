/* eslint-disable no-unused-vars */
import React from 'react';
import styled from 'styled-components';
import Modal1 from 'components/CreateDiaryModal';
import Modal2 from 'components/RandomMatchingQuestionModal';
import Modal3 from 'components/MypageModal';
import Modal4 from 'components/LogoutModal';

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
