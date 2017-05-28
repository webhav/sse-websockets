package com.webhav.websockets;

import java.security.Principal;
import java.util.Map;
import java.util.Random;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-questions").setHandshakeHandler(new RandomUsernameHandshakeHandler()).withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app")
				//.enableSimpleBroker("/topic", "/queue");
				.enableStompBrokerRelay("/topic","/queue");
	}
	
	private class RandomUsernameHandshakeHandler extends DefaultHandshakeHandler {
		
		private String[] ADJECTIVES = {"aggressive", "annoyed", "black", "bootiful", "crazy", "elegant", "little", "old-fashioned",
				"secret", "sleepy", "toxic"};
		
		private String[] NOUN = {"agent", "american", "crab", "gorilla", "flamingo", "king", "kitten", "penguin", "runner", "warrior"};
		
		@Override
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
				Map<String, Object> attributes) {
			String userName = this.getRandom(ADJECTIVES) + "-" + this.getRandom(NOUN) + "-" + this.getRandomInt(1000);
			return new UsernamePasswordAuthenticationToken(userName, null);
			
		}
		
		private String getRandom(String[] array) {
			return array[getRandomInt(array.length)];
		}
		
		private int getRandomInt(int bound) {
			Random r = new Random();
			return r.nextInt(bound);
		}
		
		
	}

}
