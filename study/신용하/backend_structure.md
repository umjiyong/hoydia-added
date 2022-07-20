# 백엔드 구조

- controller
    
    사용 class에 @Controller 선언
    
    사용자의 입력을 받고 서비스로 전달하는 역할. 
    
    view로 부터 오는 요청에 대하여 get, post 등 매칭하여 service로 전달
    

- service(또는 api)
    
    사용 class에 @Service 선언
    
    비즈니스 로직을 처리
    

- repository
    
    사용 class에 @Repository 선언
    
    실제 DB와 연결되는 영역(DAO)
    
    보통 실행될 쿼리가 들어있음
    

- domain(model 또는 post로 사용하기도 함)
    
    사용 class에 @Entity 선언
    
    테이블의 필드와 매핑되는 영역(DTO)