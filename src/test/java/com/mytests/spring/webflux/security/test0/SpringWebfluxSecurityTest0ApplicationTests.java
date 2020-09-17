package com.mytests.spring.webflux.security.test0;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class SpringWebfluxSecurityTest0ApplicationTests {


    private WebTestClient rest;
    @Autowired
    private ApplicationContext context;

    
    @Test
    public void testHome() {
        this.rest = WebTestClient
                .bindToApplicationContext(this.context)
                .configureClient()
                .build();
        this.rest.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                 .expectBody(String.class).isEqualTo("home");
    }
    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void testAdmin() {
        this.rest = WebTestClient
                .bindToApplicationContext(this.context)
                .configureClient()
                .build();
        this.rest.get()
                .uri("/admin")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("admin admin");
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void testCommon2() {
        this.rest = WebTestClient
                .bindToApplicationContext(this.context)
                .configureClient()
                .build();
        this.rest.get()
                .uri("/common2/admin")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("this page is available if the login name matches the path variable: admin");
    }

    @Test
    @WithMockUser(username = "irina", password = "jolt", roles = {"ADMIN","USER"})
    public void testCommon3() {
        this.rest = WebTestClient
                .bindToApplicationContext(this.context)
                .configureClient()
                .build();
        this.rest.get()
                .uri("/common3")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("this is the third common area for everyone");
    }
}
