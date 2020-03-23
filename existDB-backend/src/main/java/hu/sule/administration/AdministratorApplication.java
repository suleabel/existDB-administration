package hu.sule.administration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AdministratorApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AdministratorApplication.class, args);
		Logger logger = LoggerFactory.getLogger(AdministratorApplication.class);
		logger.info("eXsit-DB administration application is started");
	}

}
