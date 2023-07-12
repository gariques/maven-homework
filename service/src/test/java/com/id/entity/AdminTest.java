package com.id.entity;

import com.id.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void saveAndReadAdmin() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var expectedResult = Admin.builder()
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .login("test1@gmail.com")
                    .build();

            session.save(expectedResult);

            var actualResult = session.get(Admin.class, 4L);

            session.getTransaction().commit();

            assertNotNull(expectedResult.getId());
            assertEquals(expectedResult, actualResult);
        }
    }

}