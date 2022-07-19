## TEAM Member

| 김현열 | 유진 | 채민진 | 엄지용 | 신용하 | 김태경 |
| :----: | :--: | :----: | :----: | :----: | :----: |
|  INFJ  | INTP |  INFJ  |  INFP  |  INFP  |        |





## Prototype

[figma](https://www.figma.com/file/862k6I37QBj2VbhGci50wR/Hoydia?node-id=0%3A1)



## 개발 컨벤션



## FE

**변수** : Camel-case(lower) (ex. myDiary)

**메소드** : Camel-case(lower) (ex. myDiary)

**상수** : snake_case(upper) (ex. MAX_VALUE)

**폴더, 파일** : kebab-case (ex. my-diary)

**className, id** : kebab-case (ex. my-diary)



## BE

**함수, 변수** : Camel-case (lower) (ex. myDiary)

**DB** : Snake-case (lower) (ex. my_diary)

객체 : Pascal-case (ex. MyDiary)



## commit

- 커밋 메시지 제목 + 본문으로 작성하기
- 제목과 본문은 빈 행으로 구분
- 어떻게 보다는 **무엇**과 **왜**에 초점을 두기
- 제목 형식
  - 영어로 작성 통일
  - 명령문으로, 과거형 사용X
  - 끝에는 마침표를 넣지 않음
  - 50글자 이내로 제한

```
feat : 새로운 기능에 대한 커밋
fix : build 관련 파일 수정에 대한 커밋
build : 빌드 관련 파일에 대한 커밋
chore : 그 외 자잘한 수정에 대한 커밋
ci : CI 관련 설정 수정에 대한 커밋
docs : 문서 수정에 대한 커밋
style : 코드 스타일 혹은 포맷 등에 관한 커밋
refactor : 코드 리팩토링에 대한 커밋
test : 테스트 코드 수정에 대한 커밋
```

- 본문
  - 최대한 자세하게 추가된 기능 모두 작성
  - 본문의 각 행은 72글자 내로 제한

ex)

```
feat: create login DB table

db에 저장할 id, password 등 테이블 작성
```





