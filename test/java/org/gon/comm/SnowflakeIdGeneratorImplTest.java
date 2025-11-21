package org.gon.comm;

import org.gon.TestMain;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.jupiter.api.Assertions.*;

class SnowflakeIdGeneratorImplTest {

    @Test
    void testGenerateUniqueIds() throws InterruptedException {
        SnowflakeIdGeneratorImpl generator = new SnowflakeIdGeneratorImpl(1, 1);
        SortedSet<Long> ids = new ConcurrentSkipListSet<>();

        int size = (int) Math.pow(2, 8);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < size; i++) {
                ids.add(generator.nextId());
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < size; i++) {
                ids.add(generator.nextId());
            }
        });


        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertEquals(size * 2, ids.size(), "All generated IDs should be unique");

        System.out.println("Generated IDs count: " + ids.size());
        System.out.println(TestMain.decodeId(ids.first()));
        System.out.println(TestMain.decodeId(ids.last()));
    }
}