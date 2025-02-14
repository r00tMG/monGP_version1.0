package sn.root.backend_service_mongp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.repository.AnnonceRepository;
import sn.root.backend_service_mongp.repository.UserRepository;

@SpringBootApplication
public class BackendServiceMongpApplication {


    public static void main(String[] args) {
		SpringApplication.run(BackendServiceMongpApplication.class, args);
	}

	@Bean
	CommandLineRunner start(UserRepository userRepository, AnnonceRepository annonceRepository){
		return args -> {
			userRepository.save(User.builder().email("user2").password("1234").build());
			userRepository.save(User.builder().email("user3").password("1234").build());
			userRepository.save(User.builder().email("user4").password("1234").build());
		};
	}
}
