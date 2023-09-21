package training.iqgateway.rabbitmqamqp.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class representing messaging details for a set of actors.
 */
@Document("Messaging")
public class Message {
	
	/**
	 * Unique identifier for the actors involved in the messaging.
	 */
	@Id
	private String actors;
	
	/**
	 * Map containing sent messages with date and time as keys.
	 */
	private Map<String, Map<String, String>> msgSent;
	
	/**
	 * Map containing received messages with date and time as keys.
	 */
	private Map<String, Map<String, String>> msgReceive;
	
	/**
	 * Default constructor.
	 */
	public Message() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor to initialize messaging details for actors.
	 *
	 * @param actors     Unique identifier for the actors.
	 * @param msgSent    Map containing sent messages with date and time as keys.
	 * @param msgReceive Map containing received messages with date and time as keys.
	 */
	public Message(String actors, Map<String, Map<String, String>> msgSent,
			Map<String, Map<String, String>> msgReceive) {
		super();
		this.actors = actors;
		this.msgSent = msgSent;
		this.msgReceive = msgReceive;
	}

	/**
	 * Get the unique identifier for the actors.
	 *
	 * @return The actors' unique identifier.
	 */
	public String getActors() {
		return actors;
	}

	/**
	 * Set the unique identifier for the actors.
	 *
	 * @param actors The actors' unique identifier to set.
	 */
	public void setActors(String actors) {
		this.actors = actors;
	}

	/**
	 * Get the map containing sent messages.
	 *
	 * @return The map of sent messages.
	 */
	public Map<String, Map<String, String>> getMsgSent() {
		return msgSent;
	}

	/**
	 * Set the map containing sent messages.
	 *
	 * @param msgSent The map of sent messages to set.
	 */
	public void setMsgSent(Map<String, Map<String, String>> msgSent) {
		this.msgSent = msgSent;
	}

	/**
	 * Get the map containing received messages.
	 *
	 * @return The map of received messages.
	 */
	public Map<String, Map<String, String>> getMsgReceive() {
		return msgReceive;
	}

	/**
	 * Set the map containing received messages.
	 *
	 * @param msgReceive The map of received messages to set.
	 */
	public void setMsgReceive(Map<String, Map<String, String>> msgReceive) {
		this.msgReceive = msgReceive;
	}

	/**
	 * Returns a string representation of the Message object.
	 *
	 * @return A string representation of the object.
	 */
	@Override
	public String toString() {
		return "Message [actors=" + actors + ", msgSent=" + msgSent + ", msgReceive=" + msgReceive + "]";
	}

}
