React는 사용자 정의 코드를 만드는 것이 핵심



### 실습환경 구축

```bash
npx create-react-app .
```

npx를 이용해서 현재 디렉토리에서 create-react-app 이라는 앱을 사용할 수 있도록 함

![1](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-17%20203044.jpg)

![2](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-17%20203251.jpg)

```bash
npm start
```

 React 개발 환경이 시작되면서 코딩을 할 수 있는 환경이 시작됨 -> 웹브라우저가 뜸

![3](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-17%20203559.jpg)



### 구조 & 수정

![4](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20143806.jpg)

### 

- src > index.js

![5](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20144757.jpg)

index.js 파일에서 찾고 구동시킴 -> 여러가지 전역적인 설정들이 들어감

`<App/>` 이  UI를 나타내고 App은 index.js가 위치하는 현재 디렉토리안의 App.js에서 왔음

App.js에서 UI를 편집해나감

- src > App.js

![6](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20144757.jpg)

- src > App.css

App.css에 style 코드들이 있음 -> App의 디자인은 App.css 파일을 수정하면 됨

![7](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20144922.jpg)



- src > index.js

`<App/>`이라는 태그가 id값이 root인 태그로 렌더링해!

id값이 root인 태그는 어디있을까? -> public > index.html

![8](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20150329.jpg)



![9](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20150458.jpg)



### 배포

```bash
npm run build
```

배포판을 만드는 과정

![10](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20141634.jpg)

build 폴더가 생김

build > index.html  

공백이 없음 배포할 때는 공백이 필요없기 때문에 파일의 용량을 줄이기 위해 최대한으로 줄임



![11](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20142236.jpg)

s옵션을 사용해 실행

s 옵션을 사용하면 사용자가 어떤 경로로 들어오던간에 index.html 파일을 서비스해줌

build 라는 폴더를 지정하면 폴더에 있는 index.html을 서비스 해주겠다는 뜻

serve 는 node.js로 만들어진 어플리케이션

간편하게 실행시키기 위해서는 

```bash
npx serve -s build
```

![12](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20142355.jpg)

build 폴더에 있는 index.html을 서비스하는 웹 서버를 실행함

실제로 서비스에서 사용할 수 있는 버전의 파일이 만들어지고 서비스된 것을 확인할 수 있음

![13](React_3.assets/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-19%20150929.jpg)
