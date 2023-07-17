package com.id.dao;

import com.id.entity.Car;
import com.id.entity.CarStatus;
import com.id.entity.Order;
import com.id.entity.QCar;
import com.id.filters.CarFilter;
import com.id.filters.ClientFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import java.util.ArrayList;
import java.util.List;

import static com.id.entity.QCar.car;

public class CarDao {

    public List<Car> getAvailableCars(Session session, CarFilter filter, EntityGraph<Car> graph) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter != null) {
            predicates.add(car.status.eq(filter.getStatus()));
        }
        return new JPAQuery<Car>(session)
                .select(car)
                .from(car)
                .where(predicates.toArray(Predicate[]::new))
                .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                .fetch();
    }
}
