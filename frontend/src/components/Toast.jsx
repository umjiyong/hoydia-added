import styled from 'styled-components';

const Container = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  padding: 20px;
  min-width: 200px;
  transform: translate(-50%, -50%);
  z-index: 3;
  // background: rgba(0, 0, 0, 0.7);
  background: #ff8960;
  color: #fff;
  // color: black;
  border-radius: 10px;
`;

function Toast({ msg = '메세지 없음' }) {
  return (
    <div className="toast">
      <Container>{msg}</Container>
    </div>
  );
}

export default Toast;
