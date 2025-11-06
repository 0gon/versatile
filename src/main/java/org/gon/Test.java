package org.gon;

import org.gon.comm.HibernateIdGenerator;

import java.io.Serializable;
import java.time.Instant;

public class Test {
    public static void main(String[] args) {
        HibernateIdGenerator generator = new HibernateIdGenerator();
        Serializable generate = generator.generate(null, null);
        System.out.println(generate);
    }
}
