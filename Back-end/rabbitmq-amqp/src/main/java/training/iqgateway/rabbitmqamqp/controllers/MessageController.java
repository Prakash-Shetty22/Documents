package training.iqgateway.rabbitmqamqp.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.iqgateway.rabbitmqamqp.dto.ContactsDTO;
import training.iqgateway.rabbitmqamqp.model.Message;
import training.iqgateway.rabbitmqamqp.repositories.MessageRepository;
import training.iqgateway.rabbitmqamqp.services.MessageService;

/**
 * Controller class for handling message-related endpoints.
 */
@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageRepository messageRepository;

	/**
	 * Handles the sending of a message to a specified queue.
	 *
	 * @param input A JSON map containing the message content and actors' details.
	 * @return A ResponseEntity indicating the success or failure of the message sending.
	 */
	@PostMapping("/sendMessage")
	public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> input) {
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
		String updatedMessageContent = today + " " + formattedTime + " " + msgSent + "\n"; // Append
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

			return ResponseEntity.ok(updatedMessageContent);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
		}
	}
	
	/**
	 * Handles the receiving of messages from a specified queue.
	 *
	 * @param input A JSON map containing the actors' details.
	 * @return A map containing the received messages, categorized by date and time.
	 */
	@PostMapping("/receiveMessage")
	public Map<String, Map<String, String>> receiveMessage(@RequestBody Map<String, String> input) {

		String actors = input.get("actors").toUpperCase();
		String queue = actors;

		// Receive message from attender
		List<String> receivedMessage = null;
		try {
			receivedMessage = messageService.receiveMessage(queue);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		Map<String, Map<String, String>> messageMap = new HashMap<String, Map<String, String>>();
		
		if (receivedMessage != null) {
			// Message updated = messageRepository.findByActors(actors);

			// Send the receivedMessage as the response
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

		}
		
		return messageMap;
	}

	/**
	 * Handles the retrieval of previous conversations/messages.
	 *
	 * @param input A JSON map containing the actors' details.
	 * @return The previous messages exchanged between the actors.
	 */
	@PostMapping("/receiveOldMessage")
	public Message receiveOldMessage(@RequestBody Map<String, String> input) {
		return messageService.receiveOldMessage(input);
	}
	
	/**
	 * Handles the retrieval of a contacts list for a specified user.
	 *
	 * @param input A JSON map containing the user's mobile number.
	 * @return A list of contact details (ContactsDTO) associated with the user.
	 */
	@PostMapping("/contactsList")
	public List<ContactsDTO> receiveContactsList(@RequestBody Map<String, String> input) {
		System.out.println("Inside controller : " + input.get("mobileNumber"));
		return messageService.receiveContactsList(input.get("mobileNumber"));
	}
	
	/**
	 * Ends an ongoing conversation between two actors.
	 *
	 * @param input A JSON map containing the actors' details.
	 * @return A ResponseEntity indicating the success or failure of ending the conversation.
	 */
	@PostMapping("/endConversation")
	public ResponseEntity<Map<String, String>> endConversation(@RequestBody Map<String, String> input) {
		return messageService.endConversation(input);
	}

}
