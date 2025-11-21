package org.gon.domain.shop.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.gon.comm.SnowFlakeIdGenerator;

@Entity
public class Order {

    @Id
    @Column(name = "ORDER_ID")
    @SnowFlakeIdGenerator
    private Long id;
}
