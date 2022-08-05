/* eslint-disable react/self-closing-comp */
import React from 'react';
import styled from 'styled-components';
import drawer from 'assets/drawer.png';

const Container = styled.div`
  position: relative
  width: 100vw
  height: 100vh
  background-color: #ffca8c;
`;

const Drawer = styled.img`
  position: absolute;
  bottom: 0;
  max-width: 100%;
  height: auto;
`;

function draweri() {
  return (
    <div className="drawer">
      <Container>
        <Drawer src={drawer} alt="drawer" />
      </Container>
    </div>
  );
}
export default draweri;
