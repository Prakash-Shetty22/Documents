package training.iqgateway.rabbitmqamqp.services;

import static org.hamcrest.CoreMatchers.startsWith;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import training.iqgateway.rabbitmqamqp.dto.ContactsDTO;
import training.iqgateway.rabbitmqamqp.model.Message;
import training.iqgateway.rabbitmqamqp.model.User;
import training.iqgateway.rabbitmqamqp.repositories.MessageRepository;
import training.iqgateway.rabbitmqamqp.repositories.UserRepository;

/**
 * Service class that handles message-related operations.
 */
@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
     * Retrieve all messages from the database.
     *
     * @return A list of all messages.
     */
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	/**
     * Send a message to the specified queue.
     *
     * @param message The message to send.
     * @param queue   The name of the queue to send the message to.
     * @return True if the message was sent successfully, false otherwise.
     * @throws IOException     If an I/O error occurs.
     * @throws TimeoutException If a timeout occurs.
     */
	public boolean sendMessage(String message, String queue) throws IOException, TimeoutException {

		// Get today's date in the desired string format
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String today = now.format(formatter);

		LocalTime currentTime = LocalTime.now();
		// Format the current time as [hh:mm]
		String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("[HH:mm:ssSSS]"));

		// Modify message content to include formatted time
		String updatedMessageContent = today + " " + formattedTime + " " + message + "\n"; // Append
																							// new
																							// line

		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("localhost");
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

			channel.queueDeclare(queue, false, false, false, null);
			channel.basicPublish("", queue, null, updatedMessageContent.getBytes());

			return true; // Return true since the message sent successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if any exception occurred and the
							// message wasn't sent
		}
	}

	/**
     * Receive messages from a queue.
     *
     * @param queue The name of the queue to receive messages from.
     * @return A list of received messages.
     * @throws IOException     If an I/O error occurs.
     * @throws TimeoutException If a timeout occurs.
     */
	public List<String> receiveMessage(String queue) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("localhost");
		Connection connection = factory.newConnection();

		int NUM_CONSUMERS = 6;

		// Use CountDownLatch for synchronization
		CountDownLatch latch = new CountDownLatch(NUM_CONSUMERS);

		List<String> receivedMessages = Collections.synchronizedList(new ArrayList<>());

		for (int i = 0; i < NUM_CONSUMERS; i++) {
			new Thread(() -> {
				try {
					Channel channel = connection.createChannel();
					channel.queueDeclare(queue, false, false, false, null);

					DeliverCallback deliverCallback = (consumerTag, delivery) -> {
						String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
						receivedMessages.add(message);
					};

					channel.basicConsume(queue, true, deliverCallback, consumerTag -> {
					});

					latch.countDown(); // Signal that a consumer is active
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // Wait until all consumers are active

		// Wait for consumers to process messages
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // Adjust this time to control the execution duration

		connection.close();

		return receivedMessages;
	}

	/**
     * Receive old messages for a conversation.
     *
     * @param input The input containing actors' information.
     * @return The updated message containing received old messages.
     */
	public Message receiveOldMessage(Map<String, String> input) {
		Message sentMessage = null;
		Message receiveMessage = null;
		if (messageRepository.findById(input.get("actor1") + "TO" + input.get("actor2")).isPresent()) {
			sentMessage = messageRepository
					.findByActors(input.get("actor1") + "TO" + input.get("actor2").toUpperCase());
		}
		if (messageRepository.findById(input.get("actor2") + "TO" + input.get("actor1")).isPresent()) {
			receiveMessage = messageRepository.findByActors(input.get("actor2") + "TO" + input.get("actor1"));
		}

		// Receive message from attender
		List<String> receivedMessage = null;
		try {
			User user = userRepository.findByMobileNumber(Long.parseLong(input.get("actor1")));
			if(user.getRole().equalsIgnoreCase("Patient")){				
				if (sentMessage != null)
					receivedMessage = receiveMessage(input.get("actor2") + "TO" + input.get("actor1"));
			}
			else {
				receivedMessage = receiveMessage(input.get("actor2") + "TO" + input.get("actor1"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// return null;
		}

//		if (receiveMessage != null && receivedMessage.size() > 0) {
		if (receiveMessage != null && receivedMessage != null && receivedMessage.size() > 0) {
			Map<String, Map<String, String>> oldMsg = receiveMessage.getMsgReceive();
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
				receiveMessage.setMsgReceive(oldMsg);
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
				receiveMessage.setMsgReceive(messageMap);
			}
			// }
		} else if (receivedMessage != null && receivedMessage.size() > 0) {
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
			receiveMessage = new Message(input.get("actor2") + "TO" + input.get("actor1"), null, messageMap);
		}

		if (receiveMessage != null && receivedMessage != null && receivedMessage.size() > 0) {
			Map<String, Map<String, String>> oldMsg = receiveMessage.getMsgSent();
			if (oldMsg != null) {
				for (String message : receivedMessage) {
					String[] parts = message.split(" ", 3);
					if (parts.length >= 3) {

						String dateKey = parts[0];
						String timeKey = parts[1];
						String messageContent = parts[2];

						if (oldMsg.containsKey(dateKey)) {
							System.out.println("Date present");
							if (!oldMsg.get(dateKey).containsKey(timeKey)) {
								// Retrieve the substring timeKey, ex:"30449"
								String numberString = timeKey.substring(timeKey.lastIndexOf(":") + 1);

								// Parse the substring as an integer
								int number = Integer.parseInt(numberString);

								// Add and subttract 1 to the number
								int increment = number + 1;
								int decrement = number - 1;

								// Format the incremented and decremented numbers with leading zeroes
					            String added = timeKey.substring(0, timeKey.lastIndexOf(":") + 1) + String.format("%05d", increment);
					            String subtracted = timeKey.substring(0, timeKey.lastIndexOf(":") + 1) + String.format("%05d", decrement);
								
								System.out.println("Added Key : " + added + " Subtracted Key : " + subtracted);

								// System.out.println(updatedString);
								if (!oldMsg.get(dateKey).containsKey(added)
										&& !oldMsg.get(dateKey).containsKey(subtracted)) {
									oldMsg.get(dateKey).put(timeKey, messageContent);
								}
							}
						} else {
							System.out.println("Date not present");
							Map<String, String> innerMap = new HashMap<>();
							innerMap.put(timeKey, messageContent);
							oldMsg.put(dateKey, innerMap);
						}
					}
				}
				receiveMessage.setMsgSent(oldMsg);
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
				receiveMessage.setMsgSent(messageMap);
			}
		}

		Message result = sentMessage;

		if (result != null) {
			if (receiveMessage != null) {
				result.setMsgReceive(receiveMessage.getMsgReceive());
			} else {
				result.setMsgReceive(null);
			}
		} else {
			if (receiveMessage != null && receivedMessage != null && receivedMessage.size() > 0) {
				result = new Message(input.get("actor1") + "TO" + input.get("actor2"), null,
						receiveMessage.getMsgReceive());
			}
		}

		if (receiveMessage != null && receivedMessage != null && receivedMessage.size() > 0) {
			messageRepository.save(receiveMessage);
		}

		return result;
	}

	/**
     * Retrieve contact information and unread message count for a given mobile number.
     *
     * @param mobileNumber The mobile number for which to retrieve contacts.
     * @return A list of ContactsDTO containing contact information and unread message count.
     */
	public List<ContactsDTO> receiveContactsList(String mobileNumber) {

		System.out.println("Inside service");

		List<Message> messages = messageRepository.findMessagesByActorsEndingWith(mobileNumber);
		List<String> ids = new ArrayList<>();

		List<ContactsDTO> result = new ArrayList<>();
		
		for (Message message : messages) {
			ContactsDTO contactsDTO = new ContactsDTO();

			String[] parts = message.getActors().split("TO");
			Long patientMobileNumber = Long.parseLong(parts[0]);

			User patient = userRepository.findByMobileNumber(patientMobileNumber);
			contactsDTO.setMobileNumber(patient.getMobileNumber());
			contactsDTO.setName(patient.getFullName());

			Map<String, Map<String, String>> msgSent = message.getMsgSent();

			int totalOfMsgSent = 0;

			for (Map.Entry<String, Map<String, String>> outerEntry : msgSent.entrySet()) {
				Map<String, String> innerMap = outerEntry.getValue();
				totalOfMsgSent += innerMap.size();
			}

			System.out.println("Total key-value pairs of MsgSent: " + totalOfMsgSent);

			Map<String, Map<String, String>> msgReceive = message.getMsgReceive();

			int totalOfMsgReceive = 0;

			if (msgReceive != null) {
				for (Map.Entry<String, Map<String, String>> outerEntry : msgReceive.entrySet()) {
					Map<String, String> innerMap = outerEntry.getValue();
					totalOfMsgReceive += innerMap.size();
				}
			}
			System.out.println("Total key-value pairs of MsgReceive: " + totalOfMsgReceive);

			contactsDTO.setUnreadMessages(totalOfMsgSent - totalOfMsgReceive);

			String lastOuterKey = null;
			String lastInnerKey = null;
			String lastInnerValue = null;

			for (Map.Entry<String, Map<String, String>> outerEntry : msgSent.entrySet()) {
				lastOuterKey = outerEntry.getKey();
				Map<String, String> innerMap = outerEntry.getValue();

				for (Map.Entry<String, String> innerEntry : innerMap.entrySet()) {
					lastInnerKey = innerEntry.getKey();
					lastInnerValue = innerEntry.getValue();
				}
			}

			System.out.println("Last inner value: " + lastInnerValue);

			String truncatedMessage = truncateMessage(lastInnerValue, 20);
			System.out.println(truncatedMessage);

			contactsDTO.setLastMessage(truncatedMessage);

			result.add(contactsDTO);
		}

		return result;
	}

	/**
     * Truncate a message to a specified maximum length.
     *
     * @param message    The message to truncate.
     * @param maxLength  The maximum length for the truncated message.
     * @return The truncated message.
     */
	public static String truncateMessage(String message, int maxLength) {
		if (message.length() <= maxLength) {
			return message;
		} else {
			int endIndex = Math.min(message.length(), maxLength);
			int newLineIndex = message.substring(0, endIndex).indexOf('\n');

			if (newLineIndex != -1) {
				return message.substring(0, newLineIndex);
			} else {
				return message.substring(0, endIndex) + "...";
			}
		}
	}

	/**
     * End a conversation between two actors by deleting related messages.
     *
     * @param input The input containing actors' information.
     * @return A response entity indicating the success or failure of conversation end.
     */
	public ResponseEntity<Map<String, String>> endConversation(Map<String, String> input) {
		String actors1 = input.get("actors1").toUpperCase();
		String actors2 = input.get("actors2").toUpperCase();

		Map<String, String> response = new HashMap<>();

		try {
			messageRepository.deleteById(actors1);
			messageRepository.deleteById(actors2);
			response.put("message", "Conversation Ended Successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			response.put("message", "Failed To Delete Conversation");
			return ResponseEntity.ok(response);
		}
	}

}
