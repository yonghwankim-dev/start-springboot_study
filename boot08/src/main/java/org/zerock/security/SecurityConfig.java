package org.zerock.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    ZerockUserService zerockUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/guest/**").permitAll()
                .antMatchers("/manager/**").hasAnyRole("MANAGER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN");

        http.exceptionHandling().accessDeniedPage("/accessDenied");
        http.formLogin().loginPage("/login").permitAll();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll();

        // 커스텀 인증 사용
        http.userDetailsService(zerockUserService);

        return http.build();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        메모리 기반 인증매니저 생성
//        auth.inMemoryAuthentication()
//                .withUser("manager")
//                .password(passwordEncoder().encode("1234"))
//                .roles("MANAGER");
//    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
