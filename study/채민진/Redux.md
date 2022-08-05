### React Data Flow의 이해

Redux   가장 대중적으로 많이 쓰임 

데이터를 사용하는 Component가 하나라면 props를 쓰면 됨 하지만 데이터를 여러 Component에서 쓴다면? -> props 대신 state을 사용함 Component들의 공통 조상에 위치시켜 이것을 참조함 === 끌어올린다

새로운 Component에서 state를 사용한다면? ->  새로운 Component와 가장 가까운 공통 조상을 찾음 또 다시 새로운 Component에서 state를 사용한다면?  -> Redux를 쓰자!



사용자의 입력, codem   push등을 통해 이벤트가 trigger 됨

Action Creator는 action을 생성해서 Store로 dispatch

Store에서 Reducer에게 previousState와 action을 주면서 state 계산을 요청

순수 함수인 Reducer는 연산 결과를 새로운 stte 형태로 Store에 반환함

Redux를 사용하면 멘토스와 콜라를 분리할 수 있음

 