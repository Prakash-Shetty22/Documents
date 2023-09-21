package training.iqgateway.rabbitmqamqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The main class that initializes and runs the RabbitmqAmqpApplication Spring Boot application.
 */
@SpringBootApplication
@EnableMongoRepositories
public class RabbitmqAmqpApplication {

	/**
     * The entry point of the RabbitmqAmqpApplication Spring Boot application.
     * This method initializes and starts the application.
     *
     * @param args The command-line arguments passed to the application.
     */
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqAmqpApplication.class, args);
	}

}
