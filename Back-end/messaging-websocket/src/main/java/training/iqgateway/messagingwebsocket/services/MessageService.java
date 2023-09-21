package training.iqgateway.messagingwebsocket.services;

import static org.hamcrest.CoreMatchers.startsWith;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import training.iqgateway.messagingwebsocket.model.Message;
import training.iqgateway.messagingwebsocket.repositories.MessageRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
	
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}
	
	public boolean sendMessage(String message, String queue) throws IOException, TimeoutException {
		
		// Get today's date in the desired string format
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String today = now.format(formatter);
		
		LocalTime currentTime = LocalTime.now();
		// Format the current time as [hh:mm]
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("[HH:mm:ssSSS]"));
        
        // Modify message content to include formatted time
        String updatedMessageContent = today + " " + formattedTime + " " + message + "\n"; // Append new line
        
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
        		Channel channel = connection.createChannel()) {
        	
        	channel.queueDeclare(queue, false, false, false, null);
        	channel.basicPublish("", queue, null, updatedMessageContent.getBytes());
        	
        	/* used in websocket */
        	 // Send the message to RabbitMQ queue
            rabbitTemplate.convertAndSend(queue, updatedMessageContent);


            // Broadcast the message to the WebSocket topic
            messagingTemplate.convertAndSend("/topic/chat/" + queue, updatedMessageContent);
            
            /* ends here */
        	
        	return true; // Return true since the message sent successfully
        } catch (Exception e) {
        	e.printStackTrace();
        	return false; // Return false if any exception occurred and the message wasn't sent
        }
	}
	
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
        			
        			channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
        			
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

//        // Join received messages into a single string and return
//        return String.join("\n", receivedMessages);
        
        return receivedMessages;
	}
	
	public Message receiveOldMessage(Map<String, String> input) {
		return messageRepository.findByActors(input.get("actors").toUpperCase());
	}

}
