# React

## 리액트가 어려운 이유

- 새로운 언어라는 막연함
- 눈에 안들어오는 코드의 난해함
- vue의 경우 html과 비슷하여 접근성이 용이하지만 리액트는 jsx문법으로 작성하기에 조금 난해한 부분이 있다



webpack : 분산되어 있는 것을 합쳐주는 역할을 한다.

babel : 자바스크립트 최신문법 ES6 을 이해못하는 브라우저가 있기 때문에 최신 문법으로 작성된 js 를 번역해주는 것



#### App.js : 모든 소스가 있는 곳

- index.js 파일의 root.render 라는 렌더링 선언, 그 내부의 App라는 커스터마이징된 태그가 컴포넌트.
- App.js가 SPA를 위해 injection 되어있다.
- App.js의 내부 function 등의 내용 수정에 따라 페이지의 내용이 변경.

- React 문법 : 대부분의 Object 표현 방식
- { } 중괄호 활용. ex) let title = ['이름', '전공'] => { title[0] }

## useState

- import { useState } from 'react';

```
[객체, 대체값]
let[name, nameUpdate] = useState['이름1', '이름2'];
=> button onClick {() => { nameUpdate(['이름3', '이름4']) } }

앞의 name은 대표하는 변수명, 뒤의 nameUpdate는 대체할 수 있는 값.
=> 아래의 버튼으로 원래의 이름1, 이름2를 이름3, 이름4로 바꾸는 것이 가능.
```

## component

- ex) function TrComp(props)
- 상위 컴포넌트에서 name과 major 등의 미리 정해둔 변수값을 props를 통해 하위 컴포넌트로 전함.

## DataGrid

- import { DataGrid } from '@mui/x-data-grid';
- json 형태로 만들어둔 값을 <DataGrid rows={rows} columns={columns} /> 등으로 주입.

## DataGrid 응용

- API 활용하여 응용
- ex) <DataGrid rows={rows} columns={columns} rowsPerPageOptions={[13,26,100]} checkboxSelection />

