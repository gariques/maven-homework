package com.id.entity;

import com.id.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

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

//    @AfterEach
//    void rollBack() {
//        session.getTransaction().rollback();
//    }

    @Test
    void saveCar() {
            var expectedResult = Car.builder()
                    .model("Toyota Crown S220")
                    .colour("white")
                    .price(3000)
                    .status(CarStatus.AVAILABLE)
                    .build();

            session.save(expectedResult);

            session.getTransaction().commit();

            assertNotNull(expectedResult.getId());
    }

    @Test
    void readCar() {
        var expectedResult = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        session.save(expectedResult);
        session.clear();

        var actualResult = session.get(Car.class, 1L);

        session.getTransaction().commit();

        assertNotNull(expectedResult.getId());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateCar() {
        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();
        session.save(car);
        car.setPrice(5000);
        car.setColour("new-Colour");

        session.update(car);

        session.getTransaction().commit();

        var updatedCar = session.get(Car.class, 1L);

        assertThat(updatedCar).isEqualTo(car);
    }

    @Test
    void deleteCar() {
        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        session.save(car);
        session.delete(car);
        session.getTransaction().commit();

        Assertions.assertNull(session.get(Car.class, 1L));
    }
}