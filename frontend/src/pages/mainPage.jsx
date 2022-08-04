import React from 'react';
import Desk from 'components/Desk';
import Navbar from 'components/Navbar';

function mainPage() {
  return (
    <div className="main">
      <Navbar />
      <Desk />
    </div>
  );
}

export default mainPage;
