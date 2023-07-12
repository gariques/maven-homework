package com.id.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@EqualsAndHashCode(exclude = "order")
@ToString(exclude = "order")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String colour;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private Order order;
}
