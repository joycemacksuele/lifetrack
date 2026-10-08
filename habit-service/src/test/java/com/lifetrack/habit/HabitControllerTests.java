package com.lifetrack.habit;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Plain unit tests for HabitController (no Spring): duplicates, concurrency, sorting:
class HabitControllerTests {

	private final HabitController controller = new HabitController(new HabitService());

	HabitEntry entry(String type) {
		return new HabitEntry(UUID.randomUUID(), LocalDate.of(2026, 10, 4), type);
	}

	@Test
	void postNewEntryReturns201() {
		assertThat(controller.habitEntry(entry("gym")).getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	void postNewEntryReturnsCorrectBody() {
		HabitEntry entry = entry("cake");
		assertThat(controller.habitEntry(entry).getBody()).isEqualTo(entry);
	}
}
