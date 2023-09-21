package training.iqgateway.chatserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//adding the stomp endpoints
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
		//here "/ws" serves as a starting path for all our website connection
		//setAllowedOrigins is optional and * accepts from all the origin
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//this is the prefix in which the user will be sending the data to the server
		registry.setApplicationDestinationPrefixes("/app");
		//topic prefixes
		registry.enableSimpleBroker("/chatroom", "/user");
		registry.setUserDestinationPrefix("/user");
	}
	
}
