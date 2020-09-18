package com.mytests.spring.webflux.security.test0;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SecuredService {

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Mono<String> userMethod() {
        return Mono.just("Hello!!!");
    }

}
