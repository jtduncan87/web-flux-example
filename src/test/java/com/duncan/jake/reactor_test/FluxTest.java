package com.duncan.jake.reactor_test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

public class FluxTest {
	private final List<String> letters = Arrays.asList("A", "B","C","D","E","F");
	@Test
	void firstFlux() {
		Flux.just("A", "B","C","D","E","F")
			.log()
			.subscribe();
	}
	
	@Test
	void fluxFromIterable() {
		Flux.fromIterable(letters)
			.log()
			.subscribe();
	}
	
	@Test
	void fluxFromRange() {
		Flux.range(0, 100)
			.log()
			.subscribe();
	}
	
	@Test
	void fluxFromInterval() throws InterruptedException {
		System.out.println("fluxFromInterval");
		Flux.interval(Duration.ofSeconds(1)) //generates infinite
			.log()
			.take(3) // will complete after 3
			.subscribe();
		
		Thread.sleep(5000);
	}
	
	@Test
	void fluxRequest() {
		System.out.println("fluxRequest");
		Flux.range(1,6)
			.log()
			.subscribe(null, null,null,
					s -> s.request(3))
			;
	}
	
	@Test
	void fluxCustomSubscriber() {
		System.out.println("fluxCustomSubscriber");
		Flux.range(1, 10)
			.log()
			.subscribe(new BaseSubscriber<Integer>() {
				int elementsToProcess = 3;
				int counter = 0;
				public void hookOnSubscribe(Subscription subscription) {
					System.out.println("......Subscribed!");
					request(elementsToProcess);
				}
				public void hookOnNext(Integer value) {
					counter++;
					if(counter == elementsToProcess) {
						counter = 0;
						Random r = new Random();
						elementsToProcess = r.ints(1, 4)
								.findFirst().getAsInt();
						request(elementsToProcess);
					}
				}
			});
	}
	
	@Test
	void fluxLimitRate() {
		System.out.println("fluxLimitRate");
		Flux.range(1, 5)
			.log()
			.limitRate(3)
			.subscribe();
	}
}
