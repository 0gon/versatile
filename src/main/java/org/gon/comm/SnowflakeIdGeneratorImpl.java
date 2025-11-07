package org.gon.comm;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;

@Component
public class SnowflakeIdGeneratorImpl implements IdentifierGenerator, InitializingBean {

    private static final int DATACENTER_BITS = 6;
    private static final int WORKER_BITS = 6;
    private static final int SEQUENCE_BITS = 6;

    private static final int maxDatacenterId = (int) (Math.pow(2, DATACENTER_BITS) - 1);
    private static final int maxWorkerId = (int) (Math.pow(2, WORKER_BITS) - 1);
    private static final int maxSequence = (int) (Math.pow(2, SEQUENCE_BITS) - 1);

    private static final long CUSTOM_EPOCH = 1400000000000L;    // 41bit
    private final int datacenterID;
    private final int workerID;
    private volatile long sequence = 0L;
    private volatile long lastTimestamp = -1L;
    private final Clock clock;

    public SnowflakeIdGeneratorImpl(@Value("${server.datacenter-id}") int datacenterId,
                                    @Value("${server.worker-id}") int workerID,
                                    Clock clock) {
        this.datacenterID = datacenterId;
        this.workerID = workerID;
        this.clock = clock;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(datacenterID < 0 || datacenterID > maxDatacenterId) {
            throw new IllegalArgumentException(String.format("Datacenter ID must be between 0 and %d", maxDatacenterId));
        }
        if(workerID < 0 || workerID > maxWorkerId) {
            throw new IllegalArgumentException(String.format("Worker ID must be between 0 and %d", maxWorkerId));
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return nextId();
    }

    private long timestamp() {
        return Instant.now(clock).toEpochMilli() - CUSTOM_EPOCH;
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

        id |= (currentTimestamp << DATACENTER_BITS + WORKER_BITS + SEQUENCE_BITS);
        id |= (datacenterID << WORKER_BITS + SEQUENCE_BITS);
        id |= (workerID << SEQUENCE_BITS);
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
