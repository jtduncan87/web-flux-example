package com.duncan.jake.reactor_test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

public class MonoTest {

	@Test
	void monoWithConsumer() {
		System.out.println("monoWithConsumer");
		Mono.just("A")
			.log() // if no log framework is available, it logs to console
		.subscribe()
		;
		assertTrue(true);
	}
	
	@Test
	void monoWithDoOn() {
		System.out.println("monoWithDoOn");
		Mono.just("A")
			.doOnSubscribe(subs -> System.out.println("Subscribed: \n\t" + subs))
			.doOnRequest(request -> System.out.println("Request: \n\t" + request))
			.doOnSuccess(complete -> System.out.println("Success: \n\t" + complete))
		.subscribe(System.out::println);
	}
	
	@Test
	void emptyMono() {
		System.out.println("emptyMono");
		Mono.empty()
			.log()
			.subscribe(System.out::println);
	}
	
	@Test
	void monoError() {
		System.out.println("monoError");
		Mono.error(new Exception())
			.log()
			.subscribe();
	}
	
	@Test
	void monoOnError() {
		System.out.println("monoOnError");
		Mono.error(new Exception())
			.onErrorResume(e -> {
				System.out.println("Caught " + e);
				return Mono.just("B");
			})
			.log()
			.subscribe();
	}
}
