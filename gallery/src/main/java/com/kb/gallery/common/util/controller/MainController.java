package com.kb.gallery.common.util.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//메인 화면을 담당
@RestController
@RequestMapping("/v1")
public class MainController {

    @GetMapping("api/greeting") // v1/api/greeting
    public String greet(){
        return "Hello Gallery Service!";
    }
}
