package com.nilesh.whatsappclone.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<String> homeController() {
        return new ResponseEntity<String>("Weclome to our whatsapp clone using spring boot api", HttpStatus.OK);
    }
}
