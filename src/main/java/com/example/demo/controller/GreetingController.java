package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    @Autowired
    Greeting greeting;

    AtomicLong counter = new AtomicLong();  // keep track of the counts

    @GetMapping("/greeting")

    public Greeting greeting(@RequestParam(value = "name") String name) {
        greeting.setId(counter.incrementAndGet());
        greeting.setContent("Hey there" + name);
        return greeting;
    }
}
