package training.iqgateway.appointmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The main class to start the Appointment Service application.
 */
@SpringBootApplication
@EnableMongoRepositories
public class AppointmentServiceApplication {

	/**
     * The main method to start the application.
     *
     * @param args The command line arguments.
     */
	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}

}
