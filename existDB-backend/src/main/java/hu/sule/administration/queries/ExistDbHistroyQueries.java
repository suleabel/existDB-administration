package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistDbHistroyQueries {
    @Autowired
    private Util util;

    public String getResHistroy(ExistDetails details, String path){
        String query = "";
        return "asd";
    }
}
