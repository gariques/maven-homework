package com.id.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"client", "car"})
@ToString(exclude = {"client", "car"})
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Client client;

    @OneToOne
    private Car car;

    private Instant startDate;

    private Instant finishDate;

    public void setClient(Client client) {
        client.setOrder(this);
        this.client = client;
    }

    public void setCar(Car car) {
        car.setOrder(this);
        this.car = car;
    }
}
