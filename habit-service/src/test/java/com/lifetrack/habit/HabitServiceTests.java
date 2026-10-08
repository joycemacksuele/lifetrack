package com.lifetrack.habit;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Plain unit tests for HabitService (no Spring)
class HabitServiceTests {

	private final HabitService service = new HabitService();

	@Test
	void controllerIsAlive() {
		assertThat(service.ping()).isEqualTo("Habit service is alive");
	}
}
