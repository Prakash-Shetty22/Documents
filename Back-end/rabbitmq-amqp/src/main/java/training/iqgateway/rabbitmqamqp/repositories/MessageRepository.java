package training.iqgateway.rabbitmqamqp.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import training.iqgateway.rabbitmqamqp.model.Message;

/**
 * Repository interface for managing messaging data in the MongoDB database.
 */
public interface MessageRepository extends MongoRepository<Message, String> {
	
	/**
	 * Find a message by the unique identifier of the actors involved in the messaging.
	 *
	 * @param actors The unique identifier of the actors.
	 * @return The message associated with the given actors.
	 */
	Message findByActors(String actors);
	
	/**
	 * Find messages with actors ending with a specific suffix.
	 *
	 * @param actorsSuffix The suffix to match with the actors' unique identifiers.
	 * @return A list of messages with actors' identifiers ending with the specified suffix.
	 */
	List<Message> findMessagesByActorsEndingWith(String actorsSuffix);
	
}
