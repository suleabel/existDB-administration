package hu.sule.administration.service.impl;

import hu.sule.administration.queries.ExistDbMiscQueries;
import hu.sule.administration.service.MiscService;
import hu.sule.administration.service.impl.ExistDbCredentialsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiscServiceImpl implements MiscService {
    @Autowired
    private ExistDbMiscQueries existDbMiscQueries;

    public String getDbVersion(){
        return existDbMiscQueries.getDbVersion(ExistDbCredentialsServiceImpl.getDetails());
    }

    public String getServerIp() {
        return ExistDbCredentialsServiceImpl.getDetails().getIp();
    }
}
