package com.duncan.jake.reactor_test;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class OperatorTest {
	@Test
	void mapTest() {
		Flux.range(1, 10)
			.map(i -> i ^ 2)
			.subscribe();
	}
}
