package com.mytests.spring.webflux.security.test0;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**
 * *
 * <p>Created by irina on 17.09.2020.</p>
 * <p>Project: spring-webflux-security-test0</p>
 * *
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SimpleSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers( "/admin")
                .hasAuthority("ROLE_ADMIN")
                .pathMatchers("/work/**").hasRole("USER")
                .pathMatchers("/common1").hasAnyRole("USER","ADMIN")
                .pathMatchers("/common3").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .pathMatchers("/common2/{pv2}").access(this::matchUser)
                .anyExchange()
                .permitAll()
                .and()
                .formLogin()
                .and()
                .csrf()
                .disable()
                .build();
    }

    
    

    private Mono<AuthorizationDecision> matchUser(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {
        return authenticationMono
                .map( a -> authorizationContext.getVariables().get("pv2").equals(a.getName()))
                .map( granted -> new AuthorizationDecision(granted));
    }
    

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("guest")
                .password("password")
                //.password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("password")
                //.password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
         UserDetails me = User
                 .withUsername("irina")
                 .password("jolt")
                 //.password(passwordEncoder().encode("jolt"))
                 .roles("USER","ADMIN")
                 .build();
        return new MapReactiveUserDetailsService(user, admin, me);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
