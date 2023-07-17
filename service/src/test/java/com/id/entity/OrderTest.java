package com.id.entity;

import com.id.dao.OrderDao;
import com.id.filters.ClientFilter;
import com.id.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static SessionFactory sessionFactory;
    private static Session session;
    private final OrderDao orderDao = new OrderDao();

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

        var order = Order.builder()
                .startDate(Instant.now())
                .finishDate(Instant.now().plusSeconds(86400))
                .build();
        order.setClient(client);
        order.setCar(car);

        session.save(client);
        session.save(car);
        session.save(order);

        session.getTransaction().commit();

        assertNotNull(order.getId());
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

        var order = Order.builder()
                .startDate(Instant.now().truncatedTo(ChronoUnit.HOURS))
                .finishDate(Instant.now().plusSeconds(86400).truncatedTo(ChronoUnit.HOURS))
                .build();
        order.setClient(client);
        order.setCar(car);

        session.save(client);
        session.save(car);
        session.save(order);
        session.clear();

        var actualResult = session.get(Order.class, 1L);

        session.getTransaction().commit();

        assertNotNull(order.getId());
        assertEquals(order, actualResult);
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
        session.save(order);

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
        session.save(order);

        session.delete(order);
        session.getTransaction().commit();

        Assertions.assertNull(session.get(Order.class, 1L));
    }

    @Test
    void getOrdersByFirstAndLastnames() {
        var orderGraph = session.createEntityGraph(Order.class);

        var client1 = Client.builder()
                .firstName("Test")
                .lastName("Testoff")
                .login("test@gmail.com")
                .driverLicenseId("123")
                .password("111")
                .role(Role.CLIENT)
                .build();
        var client2 = Client.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .login("test2@gmail.com")
                .driverLicenseId("1234")
                .password("1113")
                .role(Role.CLIENT)
                .build();

        var car1 = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();
        var car2 = Car.builder()
                .model("Toyota Camry 70")
                .colour("black")
                .price(3500)
                .status(CarStatus.AVAILABLE)
                .build();

        var order1 = Order.builder()
                .startDate(Instant.now().truncatedTo(ChronoUnit.HOURS))
                .finishDate(Instant.now().plusSeconds(86400).truncatedTo(ChronoUnit.HOURS))
                .build();
        var order2 = Order.builder()
                .startDate(Instant.now().truncatedTo(ChronoUnit.HOURS))
                .finishDate(Instant.now().plusSeconds(86400).truncatedTo(ChronoUnit.HOURS))
                .build();
        var order3 = Order.builder()
                .startDate(Instant.now().truncatedTo(ChronoUnit.HOURS))
                .finishDate(Instant.now().plusSeconds(86400).truncatedTo(ChronoUnit.HOURS))
                .build();
        order1.setCar(car1);
        order1.setClient(client1);
        order2.setCar(car2);
        order2.setClient(client1);
        order3.setCar(car2);
        order3.setClient(client2);

        session.save(client1);
        session.save(client2);
        session.save(car1);
        session.save(car2);
        session.save(order1);
        session.save(order2);
        session.save(order3);

        ClientFilter filter = ClientFilter.builder()
                .firstName("Test")
                .lastName("Testoff")
                .build();
        List<Order> orderList = orderDao.getOrdersByFirstAndLastnames(session, filter, orderGraph);

        assertThat(orderList).hasSize(2);
        assertThat(orderList.get(0).getClient().getLastName()).isEqualTo("Testoff");
    }
}