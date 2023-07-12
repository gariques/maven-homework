package com.id.entity;

import com.id.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    static void setUp() {
        sessionFactory = HibernateUtil.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    @AfterAll
    static void shutDown() {
        session.close();
        sessionFactory.close();
    }

    @BeforeEach
    void openTransaction() {
        session.beginTransaction();
    }

    @Test
    void saveOrder() {
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        var expectedResult = Order.builder()
                .startDate(Instant.now())
                .finishDate(Instant.now().plusSeconds(86400))
                .build();
        expectedResult.setClient(client);
        expectedResult.setCar(car);

        session.save(client);
        session.save(car);

        session.getTransaction().commit();

        assertNotNull(expectedResult.getId());
    }

    @Test
    void readOrder() {
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        var expectedResult = Order.builder()
                .startDate(Instant.now().truncatedTo(ChronoUnit.HOURS))
                .finishDate(Instant.now().plusSeconds(86400).truncatedTo(ChronoUnit.HOURS))
                .build();
        expectedResult.setClient(client);
        expectedResult.setCar(car);

        session.save(client);
        session.save(car);
        session.clear();

        var actualResult = session.get(Order.class, 1L);

        session.getTransaction().commit();

        assertNotNull(expectedResult.getId());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateOrder() {
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        var order = Order.builder()
                .startDate(Instant.now().truncatedTo(ChronoUnit.HOURS))
                .finishDate(Instant.now().plusSeconds(86400).truncatedTo(ChronoUnit.HOURS))
                .build();
        order.setCar(car);
        order.setClient(client);

        session.save(client);
        session.save(car);

        order.setFinishDate(Instant.now().plusSeconds(172800).truncatedTo(ChronoUnit.HOURS));
        session.update(order);

        session.getTransaction().commit();

        var updatedOrder = session.get(Order.class, 1L);

        assertThat(updatedOrder).isEqualTo(order);
    }

    @Test
    void deleteOrder() {
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        var order = Order.builder()
                .startDate(Instant.now().truncatedTo(ChronoUnit.HOURS))
                .finishDate(Instant.now().plusSeconds(86400).truncatedTo(ChronoUnit.HOURS))
                .build();
        order.setCar(car);
        order.setClient(client);

        session.save(client);
        session.save(car);

        session.delete(car);
        session.delete(client);
        session.getTransaction().commit();

        Assertions.assertNull(session.get(Order.class, 1L));
    }
}