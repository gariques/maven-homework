package com.id.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Data
@EqualsAndHashCode(exclude = "order", callSuper = false)
@ToString(exclude = "order")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User{

    private String driverLicenseId;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Order order;

    @Builder
    public Client(Long id, String firstName, String lastName, String login, String password, Role role, String driverLicenseId) {
        super(id, firstName, lastName, login, password, role);
        this.driverLicenseId = driverLicenseId;
    }
}
