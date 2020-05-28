package com.palmseung.common.config;

import com.palmseung.members.jwt.JwtAuthenticationFilter;
import com.palmseung.members.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.formLogin()
                .loginPage("/");

        http.logout()
                .logoutSuccessUrl("/");

        http.authorizeRequests()
                .antMatchers("/h2-console/*").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/resources/**").permitAll()
                .mvcMatchers("/api/members/my-info/**").hasRole("USER")
                .mvcMatchers(HttpMethod.GET,"/").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/members/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/members/**").hasRole("USER")
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/admin").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/admin/**")
                .antMatchers("/admin/css/**")
                .antMatchers("/admin/js/**");
    }
}