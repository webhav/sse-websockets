package com.webhav.websockets;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionControllerForStomp {
	
	@MessageMapping("/questions")
	public String processMessage(String question, Principal principal) {
		return question.toUpperCase() + " by " + principal.getName();
	}

}
