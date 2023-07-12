package com.id.entity;

import com.id.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientTest {

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
    void saveClient() {
        var expectedResult = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        session.save(expectedResult);

        session.getTransaction().commit();

        assertNotNull(expectedResult.getId());

    }

    @Test
    void readClient() {
        var expectedResult = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        session.save(expectedResult);
        session.clear();

        var actualResult = session.get(Client.class, 1L);

        session.getTransaction().commit();

        assertNotNull(expectedResult.getId());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateClient() {
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();
        session.save(client);
        client.setDriverLicenseId("123");

        session.update(client);

        session.getTransaction().commit();

        var updatedCar = session.get(Client.class, 1L);

        assertThat(updatedCar).isEqualTo(client);
    }

    @Test
    void deleteClient() {
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        session.save(client);
        session.delete(client);
        session.getTransaction().commit();

        Assertions.assertNull(session.get(Client.class, 1L));
    }
}
