package training.iqgateway.hospitalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The main class for starting the Hospital Service application.
 */
@SpringBootApplication
@EnableMongoRepositories
public class HospitalServiceApplication {

	/**
     * The main method to start the Hospital Service application.
     *
     * @param args Command line arguments passed to the application.
     */
	public static void main(String[] args) {
		SpringApplication.run(HospitalServiceApplication.class, args);
	}

}
