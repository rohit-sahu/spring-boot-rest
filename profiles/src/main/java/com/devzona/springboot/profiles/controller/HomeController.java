package com.devzona.springboot.profiles.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"dev", "uat", "preprod", "prod"})
@RestController
@RequestMapping("/home")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Value("${server.port}")
    private String profile;

    @GetMapping
    public String getHello() {
        return "Server is started on " + profile + " port number";
    }

    @GetMapping("/all")
    public String getAllProfileHello() {
        return "Server is started on " + profile + " port number";
    }

    @Profile("uat")
    @RestController
    @RequestMapping("/uathome")
    class HomeUATController {

        @GetMapping
        public String getHello() {
            return "Server is started on " + profile + " port number";
        }
    }
}
