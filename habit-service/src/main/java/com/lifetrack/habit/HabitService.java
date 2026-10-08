package com.lifetrack.habit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class HabitService {

    public String ping() {
        return "Habit service is alive";
    }

    // 200 OK (or 204 No Content): if the request successfully updates an existing resource.
    // 201 Created: if the request successfully creates a brand-new resource.
    @PostMapping()
    ResponseEntity<HabitEntry> habitEntry(@RequestBody HabitEntry entry) {
        // ResponseEntity will also matter for idempotency later.
        // If the same event arrives twice, you might return 201 the first time and 200 for the duplicate,
        // and that decision at runtime is exactly what @ResponseStatus can't do.

        // - ResponseEntity.ok(...) gives 200
        // - ResponseEntity.created(uri) gives 201 (and requires a URI for the Location header)
        // - ResponseEntity.status(HttpStatus.X) you pick the code yourself
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        // A proper 201 should also include a Location header pointing at the new resource (like /habits/42),
        // but you have no ID yet, so skip that for now. It becomes relevant when you add the database.
    }

}
