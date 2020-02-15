package com.example.demo.service;

import com.example.demo.model.Tantargy;
import com.example.demo.repository.TantargyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TantargyService {

    @Autowired
    TantargyRepository tantargyRepository;

    public String test() {
        return "test is working";
    }

    public Iterable<Tantargy> getTantargy() {
        return tantargyRepository.findAll();
    }

    public void saveTantargy() {
        System.out.println("Save");
    }


}
