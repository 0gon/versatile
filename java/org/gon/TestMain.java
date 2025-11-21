package org.gon;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.gon.domain.member.entity.RoleType;

import java.security.Key;
import java.time.Clock;

public class TestMain {
    public static void main(String[] args) {
        String s = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9Xk";
        System.out.println(s);
        byte[] b = Decoders.BASE64.decode(s);
        System.out.println(b.length);
        Key key = Keys.hmacShaKeyFor(b);
        System.out.println(key.getAlgorithm());


    }


    static int size = 64;
    static int datacenterBits = 6;
    static int workerBits = 6;
    static int sequenceBits = 12;

    public static String prettyBinary(long l) {
        String b = Long.toBinaryString(l);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if(size - b.length() > i) {
                sb.append("0");
            } else {
                sb.append(b.charAt(i - (size - b.length())));
            }

            if((i + 1) % 4 == 0) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    public static Clock prev = null;
    public static String decodeId(long id) {
        String b = Long.toBinaryString(id);

        String binary = TestMain.prettyBinary(id).replace(" ", "");

        long timestamp = Long.parseLong(binary.substring(0, size - datacenterBits - workerBits - sequenceBits), 2) + 1400000000000L;
        Clock clock = Clock.fixed(java.time.Instant.ofEpochMilli(timestamp), java.time.ZoneOffset.UTC);
        long datacenterId = Long.parseLong(binary.substring(size - datacenterBits - workerBits - sequenceBits, size - workerBits - sequenceBits), 2);
        long workerId = Long.parseLong(binary.substring(size - workerBits - sequenceBits, size - sequenceBits), 2);
        long sequence = Long.parseLong(binary.substring(size - sequenceBits, size), 2);

        if(prev == null) {
            prev = clock;
        } else {
            System.out.println(clock.instant().toEpochMilli() - prev.instant().toEpochMilli());
        }

        return String.format("timestamp: %s, datacenterId: %d, workerId: %d, sequence: %d",
                clock.instant(), datacenterId, workerId, sequence);
    }
}
