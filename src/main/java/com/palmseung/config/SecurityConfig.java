package com.palmseung.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();

        http.csrf().disable();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.logout()
                .logoutUrl("/logout");

        http.authorizeRequests()
                .mvcMatchers("/", "/login", "/sign-up",
                        "/api/login", "/api/logout").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/members").permitAll()
                .anyRequest().authenticated();
    }
}
