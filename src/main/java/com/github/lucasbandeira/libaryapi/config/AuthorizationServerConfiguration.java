package com.github.lucasbandeira.libaryapi.config;

import com.github.lucasbandeira.libaryapi.security.CustomAuthentication;
import com.github.lucasbandeira.libaryapi.security.CustomUserDetailsService;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public TokenSettings tokenSettings(){
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .refreshTokenTimeToLive(Duration.ofMinutes(60))
                .build();
    }




    @Bean
    public ClientSettings clientSettings(){
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder()
                .tokenEndpoint("/oauth2/token")
                .tokenIntrospectionEndpoint("/oauth2/introspect")
                .tokenRevocationEndpoint("/oauth2/revoke")
                .authorizationEndpoint("/oauth2/authorize")
                .oidcUserInfoEndpoint("/oauth2/userinfo")
                .jwkSetEndpoint("/oauth2/jwks")
                .oidcLogoutEndpoint("/oauth2/logout")
                .build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return context -> {
            var principal = context.getPrincipal();

            if (principal instanceof CustomAuthentication authentication){
                OAuth2TokenType tokenType = context.getTokenType();
                if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)){
                    Collection <GrantedAuthority> authorities = authentication.getAuthorities();
                    List <String> authoritiesList = authorities.stream().map(GrantedAuthority::getAuthority).toList();
                    context.getClaims()
                            .claim("authorities",authoritiesList)
                            .claim("email",authentication.getUsername().getEmail())
                            .build();
                }
            }
        };
    }


}
