# React

# 1. 프로젝트 생성

node.js 설치 필요

```bash
npx create-react-app 프로젝트명
```

- `node_modules` : 라이브러리 코드 보관함
- `public` : html, img 파일 등 static 파일 보관
- `src` :  소스 코드 보관(App.js가 메인)
- `package.json` : 프로젝트 정보
- `package-lock.json` : 의존성 트리 정보

# 2. 기본 문법

### JSX

- 소괄호 안에 html 작성
- return 안에는 무조건 하나의 태그

### state

- 데이터를 보관하는 방법
- useState 사용
- 앞에가 상태 값 뒤에는 상태 변경

```jsx
let [a, setA] = useState("a");
```

### event

- onClick ← 카멜 케이스
- 함수만 들어갈 수 있다.

### react fragment

- 리액트 컴포넌트는 한개만 리턴이 가능해서 최상위 한개의 요소로 묶어야한다.
- 이 때 의미없는 태그가 늘어나는걸 막고 싶으면 react fragment를 사용하면 된다.

```jsx
import { Fragment } from "react";
function Table() {
  return (
    <Fragment>
      <td>Hello</td>
      <td>World</td>
      </Fragment>
  );
}
```

- 줄여서 <>, </>로 사용해도 된다.

# Redux

- 상태관리가 목적
- state를 한군데에서 보관하고 component들이 쉽게 가져다 쓸 수 있다.