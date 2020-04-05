package hu.sule.administration.service;

import org.springframework.stereotype.Service;

@Service
public interface ExistDbCredentialsService{

    String initDatabaseDriver(String username, String password, String url);
}
