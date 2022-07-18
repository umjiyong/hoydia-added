React는 사용자 정의 코드를 만드는 것이 핵심

### 실습환경 구축

```bash
npx create-react-app .
```

npx를 이용해서 현재 디렉토리에서 create-react-app 이라는 앱을 사용할 수 있도록 함



```bash
npm start
```

 React 개발 환경이 시작되면서 코딩을 할 수 있는 환경이 시작됨 -> 웹브라우저가 뜸



index.js 파일에서 찾고 구동시킴 -> 여러가지 전역적인 설정들이 들어감

`<App/>` 이  UI를 나타내고 App은 index.js가 위치하는 현재 디렉토리안의 App.js에서 왔음

App.js에서 UI를 편집해나감

App.css에 style 코드들이 있음 -> App의 디자인은 App.css 파일을 수정하면 됨



index.js

`<App/>`이라는 태그가 id값이 root인 태그로 렌더링해!

