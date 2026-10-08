package com.lifetrack.habit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HabitService {

    private static final Logger log = LoggerFactory.getLogger(HabitService.class);
    // Just for now to study data types and BigO. Later on I add a database.
    private final SaveEntry mapEntries = new SaveEntry();

    enum AddEntry {
        CREATED,
        DUPLICATED,
        CONFLICT// todo find better name
    }

    AddEntry habitEntry(HabitEntry entry) {
        // returns null if there was no mapping for the key (so we could add a new key),
        // otherwise the previous value associated with the specified key is returned (so it existed already)
        HabitEntry previousEntryValue = mapEntries.addIfAbsent(entry);

        if (previousEntryValue == null) {
            System.out.println("JOYCE CREATED" + mapEntries.mapToString());

            // options: we have a new id with a new value or a new id but duplicated value (which is ok)
            return AddEntry.CREATED;
        }

        // If values are the same, we have a duplicated id and data (entry was not added to map)
        if (previousEntryValue.equals(entry)) {
            System.out.println("JOYCE DUPLICATED" + mapEntries.mapToString());
            // todo why this log down below does not log while testing?
            return AddEntry.DUPLICATED;
        }

        // If values are not the same, we have a duplicated id with different data (entry was not added to map)
        System.out.println("JOYCE CONFLICT" + mapEntries.mapToString());
        return AddEntry.CONFLICT;
    }
}
