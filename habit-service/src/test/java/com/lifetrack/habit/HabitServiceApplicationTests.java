package com.lifetrack.habit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

// @SpringBootTest with a random port is for a smaller number of end-to-end checks:
//    - Everything wired together: real Tomcat, real beans, real serialization, real filters. It catches problems
//      the unit-test can't: a missing @Service, a wrong package scan, bad config in application.properties, beans
//      that fail to inject.
//    - A real HTTP round trip with TestRestTemplate/RestTestClient/WebTestClient. Closest to what a client app does.
//    - It starts the whole application context, so it's slower, and as you add Postgres and Kafka, it needs those too.
//    - A failure tells you "something in the stack is broken", not what.

// RANDOM_PORT is useful to avoid conflicts in test environments)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class HabitServiceApplicationTests {

	// TODO: POST, then GET, and check the data. One or two of these is enough at first. Later, add Testcontainers for Postgres and Kafka.

	// The injection of the port
	@LocalServerPort
	private int port;

	// Spring interprets the @Autowired annotation, and the controller is injected before the test methods are run
	@Autowired
	private HabitController controller;

	@Autowired
	private RestTestClient restTestClient;

	@Test
	void contextLoads() throws Exception {
		// This proves Spring started the whole application context and managed to create and inject your controller bean
		assertThat(controller).isNotNull();
	}

	@Test
	void controllerIsAlive() throws Exception {
		assertThat(controller.ping()).isEqualTo("Habit service is alive");
	}
}
