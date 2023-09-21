package training.iqgateway.messagingwebsocket.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.iqgateway.messagingwebsocket.model.Message;
import training.iqgateway.messagingwebsocket.repositories.MessageRepository;
import training.iqgateway.messagingwebsocket.services.MessageService;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageRepository messageRepository;
	
//	private final SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    public MessageController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

//    @MessageMapping("/sendMessage")
//    public void handleSendMessage(MessagePayload messagePayload) {
//        // Process the messagePayload, save it to the database if needed
//        // ...
//
//        // Broadcast the message to the WebSocket topic (change the topic name based on your setup)
//        messagingTemplate.convertAndSend("/topic/chat", messagePayload);
//    }

	@MessageMapping("/sendMessage")
	public ResponseEntity<String> sendMessage(@RequestBody Map<String, String> input) {
		System.out.println(input + " I'm calling");
		String actors = input.get("actors").toUpperCase();
		String msgSent = input.get("message");
		String queue = actors;

		if (msgSent == null || msgSent.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Message is empty.");
		}

		// Get today's date in the desired string format
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String today = now.format(formatter);

		LocalTime currentTime = LocalTime.now();
		// Format the current time as [hh:mm]
		String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("[HH:mm:ssSSS]"));

		// Modify message content to include formatted time
		String updatedMessageContent = formattedTime + " " + msgSent + "\n"; // Append
																				// new
																				// line

		boolean isSent = false;
		try {
			isSent = messageService.sendMessage(msgSent, queue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (isSent) {
			Message existingMessage = messageRepository.findByActors(actors);

			if (existingMessage != null) {
				Map<String, Map<String, String>> tempMsgSent = existingMessage.getMsgSent();

				if (tempMsgSent.containsKey(today)) {
					System.out.println("Date present");
					System.out.println("Time not present");
					tempMsgSent.get(today).put(formattedTime, msgSent);
					existingMessage.setMsgSent(tempMsgSent);
					messageRepository.save(existingMessage);
				} else {
					System.out.println("Date not present");
					Map<String, String> innerMap = new HashMap<>();
					innerMap.put(formattedTime, msgSent);
					tempMsgSent.put(today, innerMap);
					existingMessage.setMsgSent(tempMsgSent);
					messageRepository.save(existingMessage);
				}
			} else {
				Map<String, Map<String, String>> tempMsgSent = new HashMap<String, Map<String, String>>();
				Map<String, String> innerMap = new HashMap<>();
				innerMap.put(formattedTime, msgSent);
				tempMsgSent.put(today, innerMap);
				existingMessage = new Message(actors, tempMsgSent, null);
			}

			messageRepository.save(existingMessage);

			return ResponseEntity.ok("Message Sent and Saved.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
		}
	}

	@PostMapping("/receiveMessage")
	public ResponseEntity<?> receiveMessage(@RequestBody Map<String, String> input) {

		String actors = input.get("actors").toUpperCase();
		String queue = actors;

		// Receive message from attender
		List<String> receivedMessage = null;
		try {
			receivedMessage = messageService.receiveMessage(queue);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to receive message.");
		}

		// Map<String, Map<String, String>> messageMap = new HashMap<>();

		// for (String message : receivedMessage) {
		// String[] parts = message.split(" ", 3);
		// if (parts.length >= 3) {
		//// LocalDateTime dateTime = LocalDateTime.parse(parts[0] + " " +
		// parts[1], formatter);
		//// String dateKey = dateTime.toLocalDate().toString();
		//// String timeKey = dateTime.toLocalTime().toString();
		// String dateKey = parts[0];
		// String timeKey = parts[1];
		// String messageContent = parts[2];
		//
		// messageMap.putIfAbsent(dateKey, new HashMap<>());
		// messageMap.get(dateKey).put(timeKey, messageContent);
		// }
		// }

		// Update the MsgRecieve field in the repository
		Message existingMessage = messageRepository.findByActors(actors);

		if (existingMessage != null) {
			Map<String, Map<String, String>> oldMsg = existingMessage.getMsgReceive();
			if (oldMsg != null) {
				for (String message : receivedMessage) {
					String[] parts = message.split(" ", 3);
					if (parts.length >= 3) {

						String dateKey = parts[0];
						String timeKey = parts[1];
						String messageContent = parts[2];
						
						if (oldMsg.containsKey(dateKey)) {
							System.out.println("Date present");
							oldMsg.get(dateKey).put(timeKey, messageContent);
						} else {
							System.out.println("Date not present");
							Map<String, String> innerMap = new HashMap<>();
							innerMap.put(timeKey, messageContent);
							oldMsg.put(dateKey, innerMap);
						}
					}
				}
				existingMessage.setMsgReceive(oldMsg);
			} else {
				Map<String, Map<String, String>> messageMap = new HashMap<String, Map<String, String>>();
				for (String message : receivedMessage) {
					String[] parts = message.split(" ", 3);
					if (parts.length >= 3) {

						String dateKey = parts[0];
						String timeKey = parts[1];
						String messageContent = parts[2];

						if (messageMap.containsKey(dateKey)) {
							System.out.println("Date present");
							messageMap.get(dateKey).put(timeKey, messageContent);
						} else {
							System.out.println("Date not present");
							Map<String, String> innerMap = new HashMap<>();
							innerMap.put(timeKey, messageContent);
							messageMap.put(dateKey, innerMap);
						}
					}
				}
				existingMessage.setMsgReceive(messageMap);
			}
		} else {
			Map<String, Map<String, String>> messageMap = new HashMap<String, Map<String, String>>();
			for (String message : receivedMessage) {
				String[] parts = message.split(" ", 3);
				if (parts.length >= 3) {

					String dateKey = parts[0];
					String timeKey = parts[1];
					String messageContent = parts[2];

					if (messageMap.containsKey(dateKey)) {
						System.out.println("Date present");
						messageMap.get(dateKey).put(timeKey, messageContent);
					} else {
						System.out.println("Date not present");
						Map<String, String> innerMap = new HashMap<>();
						innerMap.put(timeKey, messageContent);
						messageMap.put(dateKey, innerMap);
					}
				}
			}
			existingMessage = new Message(actors, null, messageMap);
		}

		messageRepository.save(existingMessage);

		if (receivedMessage != null) {
			// Message updated = messageRepository.findByActors(actors);

			// Send the receivedMessage as the response
			Map<String, Map<String, String>> messageMap = new HashMap<String, Map<String, String>>();
			for (String message : receivedMessage) {
				String[] parts = message.split(" ", 3);
				if (parts.length >= 3) {

					String dateKey = parts[0];
					String timeKey = parts[1];
					String messageContent = parts[2];

					if (messageMap.containsKey(dateKey)) {
						System.out.println("Date present");
						messageMap.get(dateKey).put(timeKey, messageContent);
					} else {
						System.out.println("Date not present");
						Map<String, String> innerMap = new HashMap<>();
						innerMap.put(timeKey, messageContent);
						messageMap.put(dateKey, innerMap);
					}
				}
			}
			return ResponseEntity.ok(messageMap);
			// return ResponseEntity.ok(receivedMessage);
		} else {
			// Handle the case when receivedMessage is null
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No message received.");
		}
	}
	
//	@PostMapping("/receiveOldMessage")
//	public Message receiveOldMessage(@RequestBody Map<String, String> input) {
//		messageService.receiveOldMessage(input);
//	}

}
