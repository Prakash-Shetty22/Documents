package training.iqgateway.chatserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import training.iqgateway.chatserver.model.Message;

@Controller
public class ChatController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	//method which receives the data for chatroom and then it sends the message to the chatroom topic
	@MessageMapping("/message") // user needs to use "/app/message"
	@SendTo("chatroom/public") //"chatroom" is the topic prefix set in WebSocketConfig class
	private Message receivePublicMessage(@Payload Message message) {
		return message;
	}

	@MessageMapping("/private-message")
	private Message receivedPrivateMessage(@Payload Message message) {
		
		simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // user needs to use "/user/{username}/private"
		return message;
	}
}
