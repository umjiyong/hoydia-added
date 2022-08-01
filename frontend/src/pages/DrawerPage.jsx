import React from 'react';
import styled from 'styled-components';
import Navbar from 'components/Navbar';

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 30px;
`;

function drawerPage() {
  return (
    <div className="drawer">
      <Navbar />
      <Content> 내용 </Content>
    </div>
  );
}

export default drawerPage;
