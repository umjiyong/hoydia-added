import './App.css';
import styled, { keyframes } from 'styled-components';
import BookImg from 'images/book-img.svg';
import NoteImg from 'images/note-img.png';

function App() {
  return (
    <div className="App">
      <img src={BookImg} alt="" />
      <Note src={NoteImg} alt="" />
    </div>
  );
}

const noteFloating = keyframes`
0% {
  opacity: 1;
  bottom: 250px
}
50% {
  opacity: 0.2;
  bottom: 300px;
}
100% {
  opacity: 1;
  bottom: 250px;
}
`;

const Note = styled.img`
  position: relative;
  bottom: 250px;
  right: 150px;
  animation: ${noteFloating} 1.5s 0s Infinite linear none;
`;

export default App;
