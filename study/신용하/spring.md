1.DI 방식

IoC(제어의 역행)

스프링 컨테이너는 IoC 컨테이너 : 오브젝트에 대한 제어권을 가짐.

scope singleton , prototype

controller     →      service       →     Dao    →     DataSource

@Controller @Service @Repository

(autowired)

<context:component-scan base-package=”  ”>

- applicationContext.xml 에 bean 설정 : application 에서 사용할 spring 자원 설정

2.REST 서비스 전반

@PathVariable 로 {} 안에 있는거 변수로 받아옴

3.Spring MVC ARCHITECTURE 이해

DispatcherServlet : 모든 클라이언트 요청 전달받음. controller, view 연결

HandlerMapping : URL 에 따라 적절한 컨트롤러 선택

Controller : 클라이언트의 요청 처리 후 Model을 호출, 그 결과를  DispatcherServlet에 알려줌.

ModelAndView : Controller가 처리한 데이터 및 화면에 대한 정보를 보유한 객체

ViewResolver : Controller가 리턴한 뷰 이름을 기반으로 view 결정

View : 응답화면을 생성

@RestController

@ResponseBody

@PathVariable

@CrossOrigin

@RequestBody

4.web.xml

web.xml : DispatcherServlet 설정. 최상위 Root ContextLoader 설정

servlet-context.xml : Controller 등록, Controller와 response page 연결을 위한 ViewResolver 설정

- 컨트롤러에서 @RequestMapping(”/guestbook/board”)
    
    @RequestMapping(value=”/guestbook/show” method= RequestMethod.GET )
    
- @RequestParam(value = ”age”, required=false) int age 와 같은 표현 가능.
- @RequestParam(value = ”age”, defaultValue=”25”) int age

5.MyBatis 특징 및 핵심 클래스

SQL을 별도의 파일로 분리해서 관리. parameter mapping 작업 자동.

Java Object와 SQL문 사이의 자동 Mapping 기능 지원.

Mapper Interface (Dao/Repo) 가 바뀜. impl 부분은 빠지고 인터페이스 만으로 사용.

DataSource 객체는 application-context.xml 에 설정.

TransactionManager 빈이 트랜잭션 관리
@Transactional 되어있으면 AOP를 통해 트랜잭션 처리

SqlSessionFactory

tx:annotation-dfiven 해야 @Transactional 사용 가능.

6.root-context/servlet context 구분

root-context.xml에는 sqlSession, jdbc, sqlSessionFactory 등 정의. 비웹.

servlet-context.xml 에는 웹 관련. prefix, suffix 정의와 같은 웹.

7.SpringBoot @SpringBootApplication역할

자동설정을 해준다.

@SpringBootConfiguration : 해당 클래스가 @Bean 메서드가 정의되어 있음을 Spring 컨테이너에 알려준다.

@EnableAutoConfiguration : 외부의존성을 갖는 class들을 스캔해서 Bean으로 등록.

@ComponentScan : @Component가 선언된 클래스들을 찾아 Bean으로 등록. 위에꺼보다 먼저 실행

세가지를 불러준다.
