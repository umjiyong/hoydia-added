# typescript란

![Untitled](typescript%20df9942b4761f4d1f9cd3ff2c9fb4f53b/Untitled.png)

- JS에 type을 추가하여 확장한 것
- 코드를 실행하기 전 에러를 고치는 데 도움을 준다.
- any brower, any OS, any JS runs. Entirely Open Source
- 'programming Language 언어'
- 'compiled Language'이다. 전통적인 Compiled Language와는 다른 점이 많아 'Transpile'이라는 용어를 사용하기도 한다.
- JS는 'Interpreted Language'이다.
- 인터넷 브라우저는 타입스크립트 자체를 이해하지는 못한다.

![Untitled](typescript%20df9942b4761f4d1f9cd3ff2c9fb4f53b/Untitled%201.png)

![Untitled](typescript%20df9942b4761f4d1f9cd3ff2c9fb4f53b/Untitled%202.png)

그러니 우리는 TypeScript compiler을 설치해야 한다.

# 실행환경

- node js
- browser
- typescript compiler
  - way1: npm으로 설치
    ```bash
    npm i typescript -g
    node_modules/.bin/tsc
    tsc source.ts
    ```
  - way2: visual studio plugin 설치 (2017버전 이후로는 디폴트로 설치되어 있음)
  ![Untitled](typescript%20df9942b4761f4d1f9cd3ff2c9fb4f53b/Untitled%203.png)

# Type annotation

```tsx
let a: string; //a에는 string만 들어올 수 있다
a = "Mark"; //good
a = 39; //error!
function hellog(b: number) {}
hello("Mark"); //error!
```

# Types

## Primitive Types

- 오브젝트와 레퍼런스 형태가 아닌 실제 값을 저장하는 자료형.
- boolean, number, string, symbol(ES2015), null, undefined 가 있다.
- 표현방식
  - literal: true, 'hello', 3.14, null, undefined
  - 래퍼 객체: new Boolean(fale), new String('world'), new Number(42)
  - !! 래퍼 객체로 만들면 `typeof` 로 출력할 때 `object`라고 나온다. 쓰지말기. 키워드 `Type Casing` 참고
  - number 표현방식
  ```tsx
  let decimal: number = 6; //10진수 리터럴
  let hex: number = 0xf00d; //16진수 리터럴
  let binary: number = 0b1010; //2진수 리터럴
  let octal: number = 0o744; //8진수 리터럴
  let NotANumber: number = NaN;
  let underscreNum: number = 1_000_000; //이런 표기도 가능
  ```
  - Template String: backtick(``` )사용
  - symbol
    ECMAScript 2015의 새로 추가된 타입. new Symbol로 사용할 수 없다. Symbol을 함수로 사용해서 symbol 타입을 만들어 낼 수 있다.
    ```tsx
    console.log(Symbol("foo") === Symbol("foo")); //false
    ```
    primitive 타입의 값을 담아서 사용한다. 고유하고 수정불가능한 값으로 만들어 준다. 그래서 주로 접근을 제어하는데 쓰는 경우가 많다.
  - Undfined & Null
    `psconfig` 설정을 하지 않는다면 undefined와 null type은 다른 모든 타입들의 subtypes이다. 컴파일 옵션에서 `--strictNullChecks` 를 사용하면, null과 undefined는 void나 자기 자신들에게만 할당할 수 있다. 이 경우, null과 undefined를 할당할 수 있게 하려면, union type을 이용해야 한다. 아래 사진의 마지막 줄이 union type
    ![Untitled](typescript%20df9942b4761f4d1f9cd3ff2c9fb4f53b/Untitled%204.png)
    - null
      null이라는 값으로 할당된 것을 null이라고 한다. 무언가가 있는데, 사용할 준비가 덜 된 상태이다. null이라는 타입은 null이라는 값만 가질 수 있다. 런타임에서 `typeof` 연산자를 이용해서 알아내면 object이다.
    - undefined
      값을 할당하지 않은 변수는 undefined라는 값을 가진다. 무언가가 아예 준비가 안된 상태이다. object의 property가 없을 때도 undefined이다. 런타임에서 `typeof` 연산자를 이용해서 알아내면 undefined이다.

## Other types

- object
  - TypeScript에서 JavaScript의 오브젝트처럼 사용하고 싶은 경우에는 {}를 사용한다.
  - 인터페이스로 사용할 수 있다.
  - ex)
  ```tsx
  let user: object;

  user = {
    name: "Jane Doe",
    age: 20,
  };
  ```
- array
  - ex)
  ```tsx
  let numArr: number[]; // 방법 1
  numArr = [1, 2, 3];

  let numArr2: Array<string>; // 방법 2
  numArr2 = ["one", "two", "three"];
  ```
- tuple
  - 기존의 자바스크립트에는 존재하지 않는 타입
  - 길이가 고정된 배열
  - 요소마다 자료형이 다를 때에도 사용할 수 있음
  - ex)
    ```tsx
    let b: [string, number];
    b = ["one", 1];

    b[0].toLowerCase(); // 가능
    b[1].toLowerCase(); // Error!
    ```
- any
  - 어떤 자료형도 올 수 있는 경우
  - 컴파일러가 검사하지 않는다는 의미이므로 너무 남용해서는 안된다.
  - ex)
  ```tsx
  let a: any;

  a = 100;
  a = "ez";
  a = true;

  let b: boolean;
  a = b;
  ```
- unknown
  - any처럼 모든 타입이 할당될 수 있다.
  - 차이점은 any를 제외한 다른 타입으로 선언된 변수에 할당될 수 없다.
  - ex)
  ```tsx
  let variable: unknown;

  variable = true;
  variable = 1;
  variable = "string";
  variable = {};

  let anyType: any = variable;

  let booleanType: boolean = variable; // Error
  let numberType: number = variable; // Error
  let stringType: string = variable; // Error
  ```
- never
  - 항상 에러를 반환하거나 영원히 끝나지 않아 절대 반환하지 않는 함수에 사용한다.
  - 빈 배열을 타입으로 지정해 주면 never가 된다.
  - ex)
  ```tsx
  // 항상 오류 발생
  function invalid(message: string): never {
    throw new Error(message);
  }

  // 무한 루프
  function infiniteAnimate(): never {
    while (true) {
      infiniteAnimate();
    }
  }
  ```
- void
  - 아무 것도 return하지 않는 함수일 때 사용
  - 함수의 반환 타입을 명시하는 위치에 쓴다.
  - ex)
  ```tsx
  function coding(msg: string): void {
    console.log(`${msg}`);
  }
  ```
- enum
  - 자바스크립트에 없는 타입
  - 비슷한 값끼리 묶은 열거형
  - enum 자체를 자료형으로 사용할 수 있다. 그 경우 특정 값만 입력 가능
  - ex)
  ```tsx
  enum Os {
  	Window = 3,
  	Ios = 10,
  	Android
  }

  console.log(Os['Ios']) // 10
  console.log(Os['Android'] //11
  ```

# Type System

- 기본 Annotation

```tsx
let num: number = 1;
function foo(num: number): number {
  return num;
}
```

- 타입 추론

```tsx
let car = "bmw"; // car의 type은 자동으로 string
```

# TypeScript Compiler

# Interfaces

- 인터페이스는 일반적으로 타입 체크를 위해 사용되며 변수, 함수, 클래스에 사용할 수 있다.
- 인터페이스는 프로퍼티와 메소드를 가질 수 있다는 점에서 클래스와 유사하나 직접 인스턴스를 생성할 수 없고 모든 메소드는 추상 메소드이다. 단, 추상 클래스의 추상 메소드와 달리 abstract 키워드를 사용하지 않는다.
- ex)
  ```tsx
  // 인터페이스의 정의
  interface Todo {
    id: number;
    content: string;
    completed: boolean;
  }

  // 변수 todo의 타입으로 Todo 인터페이스를 선언하였다.
  let todo: Todo;

  // 변수 todo는 Todo 인터페이스를 준수하여야 한다.
  todo = { id: 1, content: "typescript", completed: false };
  ```
- object와 차이점 및 활용 방법

```tsx
let user: object;

user = {
  name: "Jane Doe",
  age: 20,
};

console.log(user.name); // Error

type Score = "A" | "B" | "C" | "F";

interface User {
  name: string;
  age: number;
  gender?: string; // optional 프로퍼티
  readonly birthYear: number; // readonly 프로퍼티
  [grade: number]: Score;
  // number를 key로 하고 string 값을 가지는 프로퍼티를 여러개 받을 수 있다.
}

let user: User = {
  name: "John Doe",
  age: 30,
  birthYear: 1999,
  1: "A",
  2: "B",
};

user.birthYear = 2000; // Error

console.log(user.name);
```

- 인터페이스 함수도 만들 수 있다.

```tsx
interface Add {
  (num1: number, num2: number): number;
}
const add: Add = function (x, y) {
  return x + y;
};
add(10, 20);
add("10", 20); // Error
```

- 인터페이스로 Class를 정의할 때는 implements 키워드를 사용한다.

```tsx
interface Car {
  color: string;
  wheels: string;
  start(): void;
}

class Bmw implements Car {
  color;
  wheels = 4;
  constructor(color: string) {
    this.color = color;
  }
  start() {
    console.log("붕붕~!");
  }
} // 모두 override 필요

const b = new Bmw("green");
```

- 인터페이스를 확장할 수도 있다.

```tsx
interface Car {
  color: string;
  wheels: string;
  start(): void;
}

interface Toy {
  name: string;
}

interface Benz extends Car {
  door: number;
  stop(): void;
}

interface ToyCar extends Car, Toy {
  price: number;
} // 이런 경우도 가능!
```

## 함수

- 선택적 매개변수를 사용할 수 있다.
- 선택적 매개변수가 필수 매개변수보다 앞에 오면 안된다.
- ex)

```tsx
// 선택적 값
function hello(name?: string) {
	return 'Hello ${name || 'world'}';
}

// 위와 같은 의미
function hello2(name = 'world') {
	return 'Hello ${name}';
}
```

- 여러 타입이 올 수 있는 함수인 경우 or를 사용해도 되지만 오버로드를 사용하자

## 교차 타입

- 두 가지 타입을 모두 가지게 할 수 있다.
- &를 사용한다.
- ex)

```tsx
interface Car {
  name: string;
  start(): void;
}

interface Toy {
  name: string;
  color: string;
  price: number;
}

const toyCar: Toy & Car = {
  name: "타요",
  start() {},
  color: "blue",
  price: 10000,
};
```

# Classes

- 타입스크립트에서 클래스를 선언할 때 멤버 변수는 미리 선언해야 한다. (혹은 public이나 readonly를 붙여야 함)
- 접근 제한자를 지원(public, private, protected)
- static을 사용하면 정적 매개변수를 만들 수 있다.
  - static으로 선언한 맴버변수는 this 대신 클래스 명으로 호출해야 함
- abstract으로 추상 클래스 선언 가능
  - 추상 클래스는 new 사용 불가
  - 상속을 통해서 사용해야 함
- ex)

```tsx
interface Point {
  x: number;
  y: number;
}
class Grid {
  static origin: Point = {
    x: 0,
    y: 0,
  };
  scale: number;
  constructor(scale: number) {
    this.scale = scale;
  }
  calcDistanceFromOrigin(point: Point): number {
    let dx = point.x - Grid.origin.x;
    let dy = point.y - Grid.origin.y;
    return Math.sqrt(dx * dx + (dy * dy) / this.scale);
  }
}
let grid1 = new Grid(1.0); // 1x scale
let grid2 = new Grid(5.0); // 2x scale
const point: Point = {
  x: 10,
  y: 10,
};
console.log(grid1.calcDistanceFromOrigin(point));
console.log(grid2.calcDistanceFromOrigin(point));
```

# Generics

- 선언 시점이 아니라 생성 시점에 타입을 명시하여 하나의 타입만이 아닌 다양한 타입을 사용할 수 있도록 하는 기법
- 클래스나 함수, 인터페이스 등을 다양한 타입으로 재사용 가능
- ex)

```tsx
function getSize<T>(arr: T[]): number {
  return arr.length;
}

const arr1 = [1, 2, 3];
getSize<number>(arr1); // 3

const arr2 = ["a", "b", "c"];
getsize<string>(arr2); // 3

// interface 예시
interface Mobile<T> {
  name: string;
  price: number;
  option: T;
}
```

## Utility Type

- keyof
- Partial
- Required
- Readonly
- Record
- Omit
- 이 외에도 많으니 필요한 경우 찾아서 사용하면 될 듯!
  - [https://typescript-kr.github.io/pages/utility-types.html](https://typescript-kr.github.io/pages/utility-types.html)

## 참고 사이트

- typescript playground: 웹 상에서 타입스크립트를 사용해 볼 수 있다.
  - [https://www.typescriptlang.org/play](https://www.typescriptlang.org/play)
