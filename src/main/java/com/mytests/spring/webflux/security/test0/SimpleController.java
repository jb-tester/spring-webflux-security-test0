package com.mytests.spring.webflux.security.test0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * *
 * <p>Created by irina on 17.09.2020.</p>
 * <p>Project: spring-webflux-security-test0</p>
 * *
 */
@RestController
public class SimpleController {


    @Autowired
    private SecuredService securedService;

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/admin")
    public Mono<String> admin(Mono<Principal> principal) {

        return principal
                .map(Principal::getName)
                .map(name -> "admin "+name);
    }

    @GetMapping("/work/{pv1}")
    public Mono<String> work(@PathVariable String pv1, Mono<Principal> principal ) {

        return principal
                .map(Principal::getName)
                .map(name -> name+ " work with "+pv1);
    }
    
    @GetMapping("/common1")
    public Mono<String> common1(Mono<Principal> principal) {

        return principal
                .map(Principal::getName)
                .map(name -> "this is the first common area for everyone including "+name);
    }
    
    @GetMapping("/methods")
    Mono<String> testSecuredMethod() {
        return  securedService.userMethod();
    }
    
}
