package org.gon;

import org.gon.comm.SnowflakeIdGeneratorImpl;

import java.time.Clock;
import java.util.*;

public class TestMain {
    public static void main(String[] args) {
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

        System.out.println(ids.size());
    }

    public static String prettyBinary(long l) {
        String b = Long.toBinaryString(l);
        String r = "";
        for (int i = 0; i < 64; i++) {
            if(64 - b.length() > i) {
                r += "0";
            } else {
                r += b.charAt(i - (64 - b.length()));
            }

            if((i + 1) % 4 == 0) {
                r += " ";
            }
        }

        return r;
    }

    public static String decodeId(long id) {
        String b = Long.toBinaryString(id);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            if(64 - b.length() > i) {
                sb.append("0");
            } else {
                sb.append(b.charAt(i - (64 - b.length())));
            }
        }

        String binary = sb.toString();
        long timestamp = Long.parseLong(binary.substring(0, 46), 2) + 1400000000000L;
        Clock clock = Clock.fixed(java.time.Instant.ofEpochMilli(timestamp), java.time.ZoneOffset.UTC);
        long datacenterId = Long.parseLong(binary.substring(46, 52), 2);
        long workerId = Long.parseLong(binary.substring(52, 58), 2);
        long sequence = Long.parseLong(binary.substring(58, 64), 2);

        return String.format("timestamp: %s, datacenterId: %d, workerId: %d, sequence: %d",
                clock.instant(), datacenterId, workerId, sequence);
    }
}
