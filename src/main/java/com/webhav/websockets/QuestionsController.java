package com.webhav.websockets;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class QuestionsController {

	private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	@RequestMapping("/questions")
	public SseEmitter questions() {
		SseEmitter emitter = new SseEmitter();
		emitters.add(emitter);
		
		emitter.onCompletion(() -> emitters.remove(emitter));
		
		return emitter;
	}
	
	@RequestMapping(value="/new-question", method=RequestMethod.POST)
	public void postQuestion(String question) {
		System.out.println("Question - "+ question);
		System.out.println("********** Emitting to "+ emitters.size());
		for(SseEmitter e : emitters) {
			try {
				e.send(SseEmitter.event().name("spring").data(question));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
