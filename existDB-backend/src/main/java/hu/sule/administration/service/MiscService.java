package hu.sule.administration.service;

import org.springframework.stereotype.Service;

@Service
public interface MiscService {
    String getDbVersion();
    String getServerIp();
}
