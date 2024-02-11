package com.example.polls;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.TimeZone;

@SpringBootApplication
//converting Java 8 Date/Time fields in the domain models to SQL types
// when we persist them in the database
@EntityScan(basePackageClasses = {
		PollsApplication.class,
		Jsr310JpaConverters.class
})
public class PollsApplication {
	//set the default timezone for our application to UTC.

	@PostConstruct
	void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	public static void main(String[] args) {

		SpringApplication.run(PollsApplication.class, args);
	}

}
