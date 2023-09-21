package training.iqgateway.messagingwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MessagingWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingWebsocketApplication.class, args);
	}

}
