package com.my.drive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list")
public class ListController {

    @GetMapping
    public String list(String id){
        System.out.println(id);
        return "list";
    }
}
