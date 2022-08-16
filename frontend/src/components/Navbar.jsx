/* eslint-disable no-unused-vars */
import React from 'react';
import styled from 'styled-components';
import Modal1 from 'components/CreateDiaryModal';
import Modal2 from 'components/RandomMatchingQuestionModal';
import Modal3 from 'components/MypageModal';
import Modal4 from 'components/LogoutModal';
import Hoydia from 'assets/Hoydia.png';

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
  font-weight: 700;
  font-size: 20px;
`;

const Atag = styled.a`
  display: block;
  text-align: center;W
`;

const LogoImg = styled.img`
  width: 110px;
  height: auto;
  margin: 3px 0px 0px 15px;
  &:hover {
    cursor: pointer;
    transform: scale(1.3);
  }
`;

function Navbar() {
  return (
    <UlTag>
      <LiTag>
        <Atag href="/mainPage">
          <LogoImg src={Hoydia} />
        </Atag>
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
