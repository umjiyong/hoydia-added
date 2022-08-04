package com.ssafy.hoydia.config.auth;

import com.ssafy.hoydia.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable() // csrf 방어기능 막기 (Swgger 사용용) (Rest API 사용시에는 사용 안해도 된다)
                .headers().frameOptions().disable().and() // 선택적 (프레임옵션) 헤더 제거 기능
                .authorizeRequests() // 다음으로 올 코드들이 URL별 권한 관리를 할 것이라는 것을 나타냄
//              .antMatchers("/","/css/**","/images/**","/js/**","/webjars/**")
                .antMatchers("/oauth-login","/","/user","/user/**","/diary","/diary/**")
                .permitAll() // 위에 지정된 URL을 전체 열람 권한을 줌
                .antMatchers().hasRole(Role.USER.name())
                .anyRequest().authenticated().and() // 설정 값 이외에는 authenticated 되어야만 사용할 수 있다.
                .logout().logoutSuccessUrl("/").and() // 로그아웃시 "/"로 이동하게 만들어 놓았다.
                .oauth2Login() // 아래코드가 로그인 기능과 관련있음.
                .userInfoEndpoint().userService(customOAuth2UserService).and()// 소셜 로그인 성공 후 이용할 userService 인터페이스의 구현체를 설정한다.
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure").and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")); //Exception 발생시 홈 url을 요청.

    }

}
