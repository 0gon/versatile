package org.gon.comm;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    @Getter
    public static int datacenterId;
    @Getter
    public static int workerID;

    public Properties(@Value("${server.datacenter-id}") int datacenterId,
                      @Value("${server.worker-id}") int workerID) {
        Properties.datacenterId = datacenterId;
        Properties.workerID = workerID;
    }
}
