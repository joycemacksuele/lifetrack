package com.lifetrack.habit;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// Plain unit tests for HabitService (no Spring)
class HabitServiceTests {

	private final HabitService service = new HabitService();

	HabitEntry entry(String type) {
		return new HabitEntry(UUID.randomUUID(), LocalDate.of(2026, 10, 4), type);
	}

	HabitEntry entry(UUID id, String type) {
		return new HabitEntry(id, LocalDate.of(2026, 10, 4), type);
	}

	@Test
	void addHabitEntry() {
		assertThat(service.habitEntry(entry("cookie"))).isEqualByComparingTo(HabitService.AddEntry.CREATED);
		assertThat(service.habitEntry(entry("cookie"))).isEqualByComparingTo(HabitService.AddEntry.CREATED);
	}

	@Test
	void addHabitEntryDuplicatedId() {
		UUID id = UUID.randomUUID();
		assertThat(service.habitEntry(entry(id, "cookie"))).isEqualByComparingTo(HabitService.AddEntry.CREATED);
		assertThat(service.habitEntry(entry(id, "cookie"))).isEqualByComparingTo(HabitService.AddEntry.DUPLICATED);
	}

	@Test
	void addHabitEntryConflictDuplicatedIdDifferentValue() {
		UUID id = UUID.randomUUID();
		assertThat(service.habitEntry(entry(id, "cookie"))).isEqualByComparingTo(HabitService.AddEntry.CREATED);
		assertThat(service.habitEntry(entry(id, "walk"))).isEqualByComparingTo(HabitService.AddEntry.CONFLICT);
	}
}
