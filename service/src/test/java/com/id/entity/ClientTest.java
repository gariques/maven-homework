package com.id.entity;

import com.id.entity.Client;
import com.id.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientTest {

    @Test
    void saveAndReadClient() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var expectedResult = getClient();

            session.save(expectedResult);

            var actualResult = session.get(Client.class, 24L);

            session.getTransaction().commit();

            assertNotNull(expectedResult.getId());
            assertEquals(expectedResult, actualResult);
        }
    }

//    @Test
//    void readClient() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var client = getClient();
//
//            session.get();
//
//            Assertions.assertNotNull(client.getId());
//        }
//    }

    private Client getClient() {
        return Client.builder()
                .firstName("Test")
                .lastName("Testov")
                .login("test24@gmail.com")
                .driverLicenseId("24")
                .build();
    }
}
