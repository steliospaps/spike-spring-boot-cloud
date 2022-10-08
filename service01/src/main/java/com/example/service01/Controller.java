package com.example.service01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public Payload getRoot() {
        return new Payload("hello world");
    }
}
