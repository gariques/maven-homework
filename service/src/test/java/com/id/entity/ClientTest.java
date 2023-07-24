package com.id.entity;

import com.id.repository.ClientRepository;
import com.id.filters.ClientFilter;
import com.id.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClientTest {

    private static SessionFactory sessionFactory;
    private Session session;


    @BeforeAll
    static void setUp() {
        sessionFactory = HibernateUtil.buildSessionFactory();
    }

    @AfterAll
    static void shutDown() {
        sessionFactory.close();
    }

    @BeforeEach
    void openTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void rollback() {
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    void saveClient() {
        var clientRepository = new ClientRepository(session);
        var expectedResult = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        clientRepository.save(expectedResult);

        assertNotNull(expectedResult.getId());

    }

    @Test
    void readClient() {
        var clientRepository = new ClientRepository(session);
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        clientRepository.save(client);
        session.clear();

        var actualClient = clientRepository.findById(client.getId());

        assertNotNull(client.getId());
        assertEquals(client, actualClient.get());
    }

    @Test
    void updateClient() {
        var clientRepository = new ClientRepository(session);
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();
        clientRepository.save(client);
        client.setDriverLicenseId("123");

        clientRepository.update(client);

        var updatedClient = clientRepository.findById(client.getId());

        assertThat(updatedClient.get()).isEqualTo(client);
    }

    @Test
    void deleteClient() {
        var clientRepository = new ClientRepository(session);
        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        clientRepository.save(client);
        clientRepository.delete(client);

        assertThat(clientRepository.findById(client.getId()).isEmpty()).isTrue();
    }

    @Test
    void getClientsWithFilter() {
        var clientRepository = new ClientRepository(session);
        var clientGraph = session.createEntityGraph(Client.class);

        var client = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();
        var client2 = Client.builder()
                .firstName("Vasya")
                .lastName("Pupkin")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();

        session.save(client);
        session.save(client2);

        ClientFilter filter = ClientFilter.builder()
                .firstName("Test")
                .lastName("Testoff")
                .build();

        var clients = clientRepository.getClientsByFirstAndLastnames(session, filter, clientGraph);
        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getLastName()).isEqualTo("Testoff");
    }

}
