package hu.sule.administration.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {


    public String testCucc() throws Exception {
        if(true){
            throw new Exception("sdf");
        }
        return "asdasdas";
    }
}
