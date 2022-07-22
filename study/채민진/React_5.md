### props

html 태그는 src를 가지고 있음 src가 뭐냐에 따라 이미지가 달라지고 width에 따라 이미지의 크기가 달라짐 -> img 태그는 입력값을 가지고 있음(속성)

리액트는 속성을 PROP이라고 부름



App함수의 Header 태그에 title값을 주고 여기 입력산 값이 Header 함수에 반영이 되도록 하고싶음

Header 함수의 첫번째 파라미터로 props를 넣음 이름은 아무거나 가능하지만 props로!

console log로 찍어보면 props에는 객체가 들어있음 이 객체에는 title이 REACT라고 되어있음

이 REACT라는 text를 얻기위해 props.title을 찍으면 REACT가 출력됨

WEB이라는 글자 대신 중괄호를 사용해 props.title을 넣음

중괄호와 중괄호 사이의 문자는 일반적인 문자열이 아니라 표현식으로 취급되기 때문에 해석되어서 나오고 반영이 됨 

Nav도 list를 하드코딩 하는 것이 아니라 props를 줘서 만들고싶음

-> topics라는 변수는 함수안에서 바뀌지 않기 때문에 const를 사용함

const를 사용하면 뒤에서 topics의 값을 바꿀 수 없기 때문에 코드가 튼튼해짐

cf. const는 재할당, 재선언 불가

topics의 값은 배열로 만들어 줌 정보가 여러개이기 때문에 각각의 정보는 제목과 본문 그리고 id값이 있음 이 모든걸 객체에 넣어줌

이 topics를 Nav의 prop으로 전달함  "topics" <- 이렇게하면 단순한 문자열로 전달되기 때문에 {} 중괄호로 감싸줌

Nav 함수에서 topics를 받으려면 첫번째 파라미터 props가 필요함

const lis 라고 하고 배열을 만들어주고 각각의 태그를 배열에 담아줌

배열에 있는 내용들을 풀어헤치려면 {lis}로 배열을 가져다주면 리액트에 의해 원소들을 하나씩 꺼내서 배치시켜줌

props에 topics에 전달된 값을 받아서 배열에 담아줘야함 -> for문 사용



console 창에 다음과 같은 warning이 나옴

각각의 li 태그, 동적으로 만들어주는 이 태그들은 각자 key라고 하는 prop을 가지고 있어야하며 그 반복문 안에서는 unique , 고유해야한다 -> 반복문안에 key 값을 줘야함

리액트는 자동으로 생성한 태그의 경우 리액트는 이 태그들을 추적해야 하는데 추적하는데 근거가 필요하고 리액트에게 key라고 하는 약속된 prop을 부여함으로써 리액트가 성능을 높이고 정확한 동작을 하는데 협조함



### 이벤트

Header 컴포넌트에 이벤트 기능 넣기 onChangeMode라는 prop의 값으로 함수를 전달함

Header 컴포넌트 안에서 링크를 클릭하면 컴포넌트가 함수를 호출해서 Header를 클릭했을 때 이벤트를 실행시킴

Header 함수의 header 태그에 onClick 이벤트를 걸어줌

cf. 여기있는 a 태그는 순수한 html과 같지 않음 리액트가 최종적으로 브라우저가 이해할 수 있는 html로 컨버팅해줌 

함수를 써줌 a 태그를 클릭했을 때 함수가 호출 됨 페이지 리로더가 일어나지 않도록 하기 위해서 event 객체를 첫번째 파라미터로 들어감 이벤트 객체는 이벤트 상황을 제어할 수 있는 여러가지 기능이 들어가 있음 event.preventDefault a 태그가 동작하는 기본 동작을 prevent 방지해줌 클릭해도 리로더가 일어나지 않음

props로 전달된 onChangeMode가 가리키는 함수를 호출하기 위해 props.onChangeMode() 해줌

코드가 너무 길어지니까 arrow function으로 바꿔줌

Navigation html을 클릭했을 때 경고창에 1, css를 클릭했을 때 경고창에 2, javascript을 클릭했을 때 경고창에 3이 뜨게하려면?

onChangeMode라는 함수를 만들어주고 첫번째 파라미터로 id값이 들어오고 경고창에 id를 넣어줌 

Nav 함수의 nav 태그에 onClick 이벤트를 걸어줌

파라미터에 event를 넣어주는데 파라미터가 하나니까 괄호 생략 가능

a 태그를 클릭했을 때 동작하지 않도록 하기 위해서 event.preventDefault() 함수 호출

onChangeMode를 호출하는데 onChangeMode는 id 값을 필요로 하기 때문에 id값을 부여해줌

a 태그의 id 속성을 얻어내기 위해서 event 객체를 사용함 event 객체가 가지고 있는 target  여기서 target은 event를 유발시킨 태그를 가리킴 -> a 태그! -> a 태그가 가지고 있는 id 값을 가지고 오고 싶다면 뒤에 .id를 붙여줌



### state

리액트 컴포넌트는 입력과 출력이 있음

입력은 Prop prop을 통해 입력된 데이터를 우리가 만든 컴포넌트 함수가 처리해서 return 값을 만들면 그 return 값이 새로운 UI가 됨 

Prop과 함께 컴포넌트 함수를 다시 실행해서 새로운 return 값을 만드는게 State

Prop, State 둘다 이 값이 변경되면 새로운 return 값을 만들어서 UI를 만듦

Prop: 컴포넌트를 사용하는 외부자를 위한 데이터

State: 컴포넌트를 만드는 내부자를 위한 데이터

 

mode라는 지역변수를 선언함

 mode의 값을 바꿔도 App 함수는 다시 실행되지 않기 때문에 return 값에는 변화가 없음

 mode의 값이 바뀌면 컴포넌트 함수가 새로 실행되면서 새로운 return 값이 만들어지고 그 return 값이 UI에 반영되는것을 원함 -> state를 사용하자

import를 하고 useState라는 hook을 사용함

hook은 리액트에서 제공하는 기본적인 함수

mode를 state 즉, 상태로 업그레이드 시킴 useState -> 상태를 만드는거 이 상태가 return이 되는데  return된 결과를 _mode라고 이름을 붙이고 console.log로 찍음 _mode의 값은 0번째 원소는 WELCOME 1번째 원소는 함수 useState는 배열을 return하고 그 배열의 0번째 원소는 상태의 값을 읽을 때 사용함 1번째 데이터는 그 상태의 값을 변경할 때 사용하는 함수

const mode = _mode[0] 이 mode의 값을 통해 상태의 값을 읽을 수 있고 const setMode = _mode[1]은 1번째 원소인 setMode를 통해 mode의 값을 바꿀 수 있다는 규칙이 있음

useState의 인자는 state의 초기값

state의 값은 0번째 인덱스의 값으로 읽음 state를 바꿀 때는 1번째 인덱스의 값, 함수로 바꿈

코드가 복잡하니 아래와 같이 작성함



값을 바꿀 때는 setMode를 사용함 mode의 값이 setMode로 인해서 READ로 바뀌면 App 컴포넌트가 다시 실행됨 useState가 mode의 값을 Read로 세팅해주고 content가 바뀌고 화면에 렌더링 됨



선택한 글을 누르면 해당 내용이 보이도록 하기 위해서

READ일 때 content를 결정하는 부분의 값을 바꿔주면 됨

어떤 글을 선택했는지 state로 만들어줘야함

const[id, setId] = useState(null) null로 해서 초기값이 없음 

Nav를 클릭했을 때 바뀌기 때문에 setId 값을 세팅하고 _id로 바꿔줌  Nav 컴포넌트를 클릭했을 때 id 값이 바뀌면 컴포넌트가 새로 실행되면서 새로운 id 값이 지정됨 

topics의 값중에 우리가 선택한 id와 일치하는 원소를 찾아서 title과 body의 값으로 세팅해주면 됨 ->  반복문 이용 title과 body의 값을  초기화 시켜주고 일치할 때 title과 body의 값 세팅 content도 맞춰서 수정해줌



클릭해도 아무것도 일어나지 않음 console.log를 찍어서 디버깅을 해보면 

topics의 id는 바뀌었지만 id state는 그대로

왜? id 값은 setId에서 오고 setId는 _id _id는 Nav 안에 있음 Nav 내부를 보면 event target의 id를 통해서 id를 알아내는데 id는 a 태그에 들어있음 우리가 입력한 값은 숫자였지만 태그의 속성으로 넘기면서 문자가 됨  그래서 문자가 된 데이터를 끌고오면 event.target.id는 문자열 id가 됨 이걸 숫자로 컨버팅 해주면 됨