package training.iqgateway.messagingwebsocket.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Messaging")
public class Message {
	
	@Id
	private String actors;
	
	private Map<String, Map<String, String>> msgSent;
	private Map<String, Map<String, String>> msgReceive;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}

	public Message(String actors, Map<String, Map<String, String>> msgSent,
			Map<String, Map<String, String>> msgReceive) {
		super();
		this.actors = actors;
		this.msgSent = msgSent;
		this.msgReceive = msgReceive;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public Map<String, Map<String, String>> getMsgSent() {
		return msgSent;
	}

	public void setMsgSent(Map<String, Map<String, String>> msgSent) {
		this.msgSent = msgSent;
	}

	public Map<String, Map<String, String>> getMsgReceive() {
		return msgReceive;
	}

	public void setMsgReceive(Map<String, Map<String, String>> msgReceive) {
		this.msgReceive = msgReceive;
	}

	@Override
	public String toString() {
		return "Message [actors=" + actors + ", msgSent=" + msgSent + ", msgReceive=" + msgReceive + "]";
	}

}
