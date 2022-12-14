package org.zerock.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ZerockUserService zerockUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // URL 접근 처리
        http.authorizeHttpRequests()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/img/**").permitAll()
            .antMatchers("/boards/list").permitAll()
            .antMatchers("/boards/view").permitAll()
            .antMatchers("/replies/**").permitAll()
            .antMatchers("/boards/register").hasAnyRole("BASIC","MANAGER","ADMIN")
            .anyRequest().authenticated();

        // 로그인 처리
        http.formLogin()
            .loginPage("/login")
            .successHandler(new LoginSuccessHandler())
            .permitAll();

        // Remember-Me 인증 처리
        http.rememberMe()
            .key("zerock")
            .userDetailsService(zerockUserService)
            .tokenRepository(getJDBCRepository())
            .tokenValiditySeconds(60*60*24);

        // 로그아웃 처리
        http.logout()
            .logoutUrl("/logout")   // 로그아웃을 특정한 페이지에서 처리
            .logoutSuccessUrl("/login?logout") // 로그아웃 성공시 login으로 이동
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true);



        // 접근 권한이 없는 경우 페이지 처리
        http.exceptionHandling().accessDeniedPage("/accessDenied");

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("build Auth global...");

        // 인증매니저가 PasswordEncoder를 사용할것이라는 것을 명시
        auth.userDetailsService(zerockUserService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
