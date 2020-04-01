package hu.sule.administration.service;

import hu.sule.administration.queries.ExistDbMiscQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiscService {
    @Autowired
    private ExistDbMiscQueries existDbMiscQueries;

    public String getDbVersion(){
        return existDbMiscQueries.getDbVersion(ExistDbCredentialsService.getDetails());
    }

    public String getServerIp() {
        return ExistDbCredentialsService.getDetails().getIp();
    }
}
