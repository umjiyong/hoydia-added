/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import styled from 'styled-components';
import { ReactComponent as DiaryImg } from 'assets/diary.svg';
import { SketchPicker } from 'react-color';

const Container = styled.div`
  color: blue;
  width: 388px;
  height: 500px;
  .plus_icon {
    .first {
      fill: ${(props) => props.color1 || '#FF8960'};
    }
    .circle {
      fill: ${(props) => props.color2 || 'FF703E'};
    }
  }
`;

function diaryEdit() {
  const [colorHex1, setColorHex1] = useState('');
  const [colorHex2, setColorHex2] = useState('');
  return (
    <div>
      <Container color1={colorHex1} color2={colorHex2}>
        <DiaryImg className="plus_icon" />
      </Container>

      <SketchPicker color={colorHex1} onChange={(e) => setColorHex1(e.hex)} />

      <SketchPicker color={colorHex2} onChange={(e) => setColorHex2(e.hex)} />
    </div>
  );
}

export default diaryEdit;
