package com.lifetrack.habit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest is for fast, focused tests of the HTTP contract:
//    - It loads only the web layer (controllers, JSON conversion, validation, error handling), not your whole app.
//      Tests run in well under a second each once the context is cached.
//    - When a test fails, the problem is in the controller or its JSON handling, not somewhere in a service (since
//      it is mocker), a database, or a Kafka connection.
//    - Tests status codes, 400s for bad input, JSON shape
//		- Can test cases that are hard to produce for real: "service says duplicate id with different content, so
//		  the controller must return 409"
@WebMvcTest(HabitController.class) // This is how we can inject MockMvc
class HabitControllerWebTest {

	// Spring interprets the @Autowired annotation, and the MockMvc is injected before the test methods are run
	@Autowired
	private MockMvc mockMvc;

	// @MockitoBean is used to create and inject a mock for a service (otherwise the application context cannot start)
	@MockitoBean
	private HabitService service;


	@Test
	void postNewEntryReturns201() throws Exception {
		when(service.ping()).thenReturn("Hello");
		assertThat(mockMvc.perform(post("/habits")).andExpect(status().isCreated()));
	}
}
