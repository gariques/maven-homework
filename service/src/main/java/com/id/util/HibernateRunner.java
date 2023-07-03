package com.id.util;

import com.id.entity.Admin;
import com.id.entity.CarStatus;
import com.id.entity.Car;
import com.id.entity.Client;
import com.id.entity.Order;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var client = Client.builder()
                    .firstName("Alena")
                    .lastName("Alenova")
                    .login("test4@gmail.com")
                    .driverLicenseId("01321654987213")
                    .build();
            session.save(client);
//
//            var car = Car.builder()
//                    .model("Toyota Crown S220")
//                    .colour("white")
//                    .price(3000)
//                    .status(CarStatus.AVAILABLE)
//                    .build();
//            session.save(car);
//
//            var admin = Admin.builder()
//                    .firstName("Ivan")
//                    .lastName("Ivanov")
//                    .login("test@gmail.com")
//                    .build();
//            session.save(admin);

//            var order = Order.builder()
//                    .client_id(1L)
//                    .car_id(1L)
//                    .dateStart(LocalDate.now())
//                    .dateFinish(LocalDate.of(2023, 11, 3))
//                    .build();
//            session.save(order);

//            var client = session.get(Client.class, 1L);
//            System.out.println(client.toString());

            session.getTransaction().commit();
        }
    }
}
