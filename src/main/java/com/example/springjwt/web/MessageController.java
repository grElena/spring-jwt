package com.example.springjwt.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MessageController {
    @GetMapping("/start")
    public ResponseEntity<String> start() {
        return ResponseEntity.ok("start");
    }

}
