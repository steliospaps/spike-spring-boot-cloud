package com.example.service01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Value("${server.port}")
    private String port;

    @Value("${app.task.id}")
    private String taskId;

    
    @GetMapping("/")
    public Payload getRoot() {
        return new Payload("hello world from "+taskId+" "+port);
    }
}
