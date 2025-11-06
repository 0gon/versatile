package org.gon.comm;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.AnnotationBasedGenerator;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;

public class HibernateIdGenerator implements IdentifierGenerator {

//    @Override
//    public Long generate(SharedSessionContractImplementor session, Object object) {
//        LocalDateTime now = LocalDateTime.now();
//        String yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
//        long id = Long.parseLong(yyyyMMdd);
//        String serverNo = Properties.getServerNo();
//
//        return Long.valueOf(id + serverNo);
//    }

    private static final int CASE_ONE_BITS = 10;
    private static final int CASE_TWO_BITS = 9;
    private static final int SEQUENCE_BITS = 4;

    private static final int maxSequence = (int) (Math.pow(2, CASE_ONE_BITS) - 1);          // 2^5-1

    private static final long CUSTOM_EPOCH = 1420070400000L;    // 41bit
    private volatile long sequence = 0L;
    private int case_one = 10;
    private int case_two = 0;
    private volatile long lastTimestamp = -1L;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return nextId();
    }

    private static long timestamp() {
        return Instant.now().toEpochMilli() - CUSTOM_EPOCH;
    }

    public synchronized long nextId() {
        long currentTimestamp = timestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Invalid System Clock!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;
        return makeId(currentTimestamp);
    }

    private Long makeId(long currentTimestamp) {
        long id = 0;

        id |= (currentTimestamp << CASE_ONE_BITS + CASE_TWO_BITS + SEQUENCE_BITS);
        id |= (case_one << CASE_TWO_BITS + SEQUENCE_BITS);
        id |= (case_two << SEQUENCE_BITS);
        id |= sequence;

        return id;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = timestamp();
        }
        return currentTimestamp;
    }
}
