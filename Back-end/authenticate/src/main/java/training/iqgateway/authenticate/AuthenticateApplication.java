package training.iqgateway.authenticate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The main class that starts the authentication application.
 */
@SpringBootApplication
@EnableMongoRepositories
public class AuthenticateApplication {

	/**
     * The main method to start the Spring Boot application.
     *
     * @param args The command line arguments passed to the application.
     */
	public static void main(String[] args) {
		SpringApplication.run(AuthenticateApplication.class, args);
	}

}
