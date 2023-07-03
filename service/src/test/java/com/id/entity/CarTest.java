package com.id.entity;

import com.id.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void saveAndReadCar() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var expectedResult = Car.builder()
                    .model("Toyota Crown S220")
                    .colour("white")
                    .price(3000)
                    .status(CarStatus.AVAILABLE)
                    .build();

            session.save(expectedResult);

            var actualResult = session.get(Car.class, 7L);

            session.getTransaction().commit();

            assertNotNull(expectedResult.getId());
            assertEquals(expectedResult, actualResult);
        }
    }

}