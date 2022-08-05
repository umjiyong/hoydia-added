import React from 'react';
import Desk from 'components/Desk';
import Navbar from 'components/Navbar';
import Drawer from 'components/Drawer';
import { Link } from 'react-router-dom';

function mainPage() {
  return (
    <div className="main">
      <Navbar />
      <Desk />
      <Link to="/drawerPage">
        <Drawer />
      </Link>
    </div>
  );
}

export default mainPage;
