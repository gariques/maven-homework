package com.id.entity;

import com.id.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void saveAndReadOrder() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var expectedResult = Order.builder()
                    .client_id(1L)
                    .car_id(1L)
                    .dateStart(LocalDate.now())
                    .dateFinish(LocalDate.of(2023, 11, 3))
                    .build();

            session.save(expectedResult);

            var actualResult = session.get(Order.class, 5L);

            session.getTransaction().commit();

            assertNotNull(expectedResult.getId());
            assertEquals(expectedResult, actualResult);
        }
    }

}