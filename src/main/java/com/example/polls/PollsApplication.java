package com.example.polls;

import com.example.polls.domain.entities.RoleEntity;
import com.example.polls.domain.entities.UserEntity;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import static com.example.polls.domain.entities.RoleName.ROLE_ADMIN;
import static com.example.polls.domain.entities.RoleName.ROLE_USER;

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
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
						  PasswordEncoder encoder){
		return args ->{
			if(roleRepository.findRoleEntityByName(ROLE_ADMIN).isPresent()) return;
			RoleEntity adminRole = roleRepository.save(new RoleEntity(ROLE_ADMIN));
			roleRepository.save(new RoleEntity(ROLE_USER));
			Set<RoleEntity> roles = new HashSet<>();
			roles.add(adminRole);
			UserEntity admin = new UserEntity("admin",
					encoder.encode("password"),
					roles);
			userRepository.save(admin);

		};
	}

}
