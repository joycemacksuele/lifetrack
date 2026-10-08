package com.lifetrack.habit;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// This application uses the Jackson JSON library to automatically marshal instances of type Record type into JSON.
// Jackson is included by default by the web starter.

// A record generates a constructor from the fields in its header plus getters (id(), date(), type()), equals, hashCode, and toString
public record HabitEntry(
        // A missing id should give 400
        @NotNull
        UUID id,
        // A missing date can be allowed?
        LocalDate date,
        // A missing type can be allowed?
        String type
) {}

