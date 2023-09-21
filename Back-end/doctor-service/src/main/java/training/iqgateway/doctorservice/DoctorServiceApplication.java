package training.iqgateway.doctorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Spring Boot application class for the Doctor Service.
 */
@SpringBootApplication
@EnableMongoRepositories
public class DoctorServiceApplication {

	/**
	 * Main method to start the Doctor Service application.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DoctorServiceApplication.class, args);
	}

}
