package com.github.lucasbandeira.libaryapi.config;

import com.github.lucasbandeira.libaryapi.security.CustomUserDetailsService;
import com.github.lucasbandeira.libaryapi.security.SocialLoginSuccessHandler;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity, SocialLoginSuccessHandler successHandler ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(configurer -> configurer.loginPage("/login"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers( "/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usernames/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 ->{
                    oauth2
                        .loginPage("/login")
                        .successHandler(successHandler);
                })
                .build();
    }


    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
