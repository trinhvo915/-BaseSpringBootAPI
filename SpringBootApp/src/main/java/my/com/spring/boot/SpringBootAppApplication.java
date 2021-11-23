package my.com.spring.boot;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import my.com.spring.boot.entities.Role;
import my.com.spring.boot.repository.RoleRepository;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        SpringBootAppApplication.class,
        Jsr310JpaConverters.class
})
public class SpringBootAppApplication {
	
	@Autowired
	RoleRepository roleRepository;
	
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		
		List<Role> roles = roleRepository.findAll();
		if(roles.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
		    
		    Date currentDate = new Date();
			return () -> {
		    	/* Init data for Role table */
		    	roleRepository.save(new Role(currentDate, currentDate,uuid,uuid,uuid,"USER"));
		    	roleRepository.save(new Role(currentDate, currentDate, uuid,uuid,uuid,"ADMIN"));
		    	roleRepository.save(new Role(currentDate, currentDate, uuid,uuid,uuid,"EXPERT"));
		    };
		}
		
	    return null;
	}
}