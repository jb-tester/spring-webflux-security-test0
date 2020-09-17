package com.mytests.spring.webflux.security.test0;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * *
 * <p>Created by irina on 17.09.2020.</p>
 * <p>Project: spring-webflux-security-test0</p>
 * *
 */
@Configuration
public class SimpleRouter {

    @Bean
    public RouterFunction<ServerResponse> simple() {
        return route(GET("/common2/{pv2}"),
                req -> ok().body(fromValue("this page is available if the login name matches the path variable: "+req.pathVariable("pv2")))) ;
    }

    @Bean
    public RouterFunction<ServerResponse> simple2() {
        return route(GET("/common3"),
                req -> ok().body(fromValue("this is the third common area for everyone"))) ;
    }
}
