package com.toy.chanygram.config;

import com.toy.chanygram.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/images/**", "/subscribe/**", "/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2DetailsService);
    }
}
