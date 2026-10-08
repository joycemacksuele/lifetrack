package com.lifetrack.habit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// just for now to save the data and study data types. Later on I will use a database
public class SaveEntry {
    // Needs to be thread-safe (Since many requests run concurrently, on different threads)
    // - Do we need to search on this data? or just add to the end of it and get it all to display on UI?
    //    Right now, no: add, and get all. Later with a DB I'll look up by ID and filter by type.
    // - Does it need to be sorted?
    //    The UI probably wants entries by date so it can sort at read time, or the DB does it with ORDER BY.
    // Options:
    // - ArrayList
    //     Why not? Isn't thread-safe
    // java.util.Collections:
    //     - synchronizedList method (thread-safe)
    //        It returns a synchronized view of the specified list
    //        It is imperative that the user manually synchronize on the returned list when iterating over it
    //        Why not? Returning it from GET means JSON serialization iterates it, so that's a trap
    // java.util.concurrent: is thread-safe and designed so threads block each other as little as possible
    // - ConcurrentHashMap (thread-safe)
    //    Atomic `putIfAbsent`. Best for lookup by ID and de-duplication.
    //    Why not? Iteration order is arbitrary, so GET has to sort by date at read time.
    // - CopyOnWriteArrayList (thread-safe)
    //    All mutative operations (add, set, and so on) are implemented by making a fresh copy of the underlying array
    //    Why not? Every write copies the whole array, so it suits read-heavy, write-rare data. Fine for a tiny list
    // - ConcurrentSkipListMap (thread-safe)
    //    Skip list makes searching or adding faster by some sort of probability algo in a sorted map.
    //    Useful if you need ordering or range queries, like entries by date.
    //    Why not? It orders by ID key but the order I need is by date.

    // Decision: ConcurrentHashMap so I start learning idempotency by adding an id to HabitEntry
    // - Note:
    // Thread-safe collections protect single operations, not sequences: "Check if it exists, then add" is two steps, and
    // another thread can slip in between. ConcurrentHashMap has atomic operations for this (putIfAbsent, computeIfAbsent).
    // - What about a duplicate id?
    //    putIfAbsent returns the previous value if there was one, and nothing if your entry was inserted.
    //    - What can be returned:
    //        201 for new inserts
    //        200 if the same id arrives with the same content
    //        409 Conflict if the same id arrives with different content

    ConcurrentHashMap<UUID, HabitEntry> entries;
}
