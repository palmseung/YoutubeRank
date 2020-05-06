package com.palmseung.config;

import com.palmseung.members.support.JwtAuthenticationFilter;
import com.palmseung.members.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

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

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.logout()
                .logoutUrl("/logout");

        http.authorizeRequests()
                .mvcMatchers("/", "/login", "/sign-up",
                        "/api/login", "/api/logout",
                        "/api/members/login", "/api/youtube/**").permitAll()
                .mvcMatchers("/api/members/my-info/**").hasRole("USER")
                .mvcMatchers(HttpMethod.POST, "/api/members").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/members").hasRole("USER")
                .anyRequest().authenticated();

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);
    }
}