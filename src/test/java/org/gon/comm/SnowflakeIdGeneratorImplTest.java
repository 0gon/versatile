package org.gon.comm;

import org.gon.TestMain;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class SnowflakeIdGeneratorImplTest {

    @Test
    void testGenerateUniqueIds() {
        SnowflakeIdGeneratorImpl generator = new SnowflakeIdGeneratorImpl(1, 1, Clock.systemUTC());
        Set<Long> ids = Collections.synchronizedSet(new HashSet<>());

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                ids.add(generator.nextId());
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                ids.add(generator.nextId());
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(2000, ids.size(), "All generated IDs should be unique");



    }
}