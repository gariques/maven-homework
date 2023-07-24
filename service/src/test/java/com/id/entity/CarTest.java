package com.id.entity;

import com.id.repository.CarRepository;
import com.id.filters.CarFilter;
import com.id.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarTest {

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
    void rollBack() {
        session.getTransaction().rollback();
        session.close();

    }

    @Test
    void saveCar() {
        var carRepository = new CarRepository(session);
        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        carRepository.save(car);

        assertNotNull(car.getId());
    }

    @Test
    void readCar() {
        var carRepository = new CarRepository(session);
        var expectedResult = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        carRepository.save(expectedResult);
        session.clear();

        var actualResult = carRepository.findById(expectedResult.getId());

        assertNotNull(expectedResult.getId());
        assertEquals(expectedResult, actualResult.get());
    }

    @Test
    void updateCar() {
        var carRepository = new CarRepository(session);
        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();
        carRepository.save(car);
        car.setPrice(5000);
        car.setColour("new-Colour");

        carRepository.update(car);

        var updatedCar = carRepository.findById(car.getId());

        assertThat(updatedCar.get()).isEqualTo(car);
    }

    @Test
    void deleteCar() {
        var carRepository = new CarRepository(session);
        var car = Car.builder()
                .model("Toyota Crown S220")
                .colour("white")
                .price(3000)
                .status(CarStatus.AVAILABLE)
                .build();

        carRepository.save(car);
        carRepository.delete(car);

        assertThat(carRepository.findById(car.getId()).isEmpty()).isTrue();
    }

    @Test
    void getAvailableCars() {
        var carRepository = new CarRepository(session);
        var carGraph = session.createEntityGraph(Car.class);
        var car = Car.builder()
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
        var car3 = Car.builder()
                .model("Toyota Land Cruiser 300")
                .colour("white")
                .price(4000)
                .status(CarStatus.NOT_AVAILABLE)
                .build();

        var filter = CarFilter.builder()
                .status(CarStatus.AVAILABLE)
                .build();


        session.save(car);
        session.save(car2);
        session.save(car3);

        List<Car> carList = carRepository.getAvailableCars(session, filter, carGraph);

        assertThat(carList).hasSize(2);
        assertThat(carList.get(0).getModel()).isEqualTo("Toyota Crown S220");
        assertThat(carList.get(1).getModel()).isEqualTo("Toyota Camry 70");
    }
}