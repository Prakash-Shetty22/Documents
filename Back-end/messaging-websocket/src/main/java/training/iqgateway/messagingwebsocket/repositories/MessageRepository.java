package training.iqgateway.messagingwebsocket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import training.iqgateway.messagingwebsocket.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	Message findByActors(String actors);
}
