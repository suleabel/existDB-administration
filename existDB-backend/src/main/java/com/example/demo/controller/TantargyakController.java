package com.example.demo.controller;

import com.example.demo.model.Tantargy;
import com.example.demo.service.TantargyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path="/rest")
public class TantargyakController {

    @Autowired
    TantargyService tantargyService;

    @RequestMapping("/test")
    public String test(){
        return tantargyService.test();
    }

    @RequestMapping("/targy")
    public Iterable<Tantargy> getTantargy(){
        return tantargyService.getTantargy();
    }

    @RequestMapping("/save")
    public void saveTantargy(){
        tantargyService.saveTantargy();
    }

}