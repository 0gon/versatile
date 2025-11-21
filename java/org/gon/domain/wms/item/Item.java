package org.gon.domain.wms.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.gon.comm.SnowFlakeIdGenerator;

import java.math.BigDecimal;

@Entity
@Getter
public class Item {

    @Id
    @Column(name = "ITEM_ID")
    @SnowFlakeIdGenerator
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    protected Item() {
    }

    public Item(String name, String description, BigDecimal price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public void addStock(int quantity) {
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity to increase must be non-negative");
        }
        this.quantity += quantity;
    }

    public void removeStock(int quantity) {
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity to decrease must be non-negative");
        }
        if(this.quantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        this.quantity -= quantity;
    }
}
