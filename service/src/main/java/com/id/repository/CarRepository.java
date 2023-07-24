package com.id.repository;

import com.id.entity.Car;
import com.id.filters.CarFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.id.entity.QCar.car;

public class CarRepository extends AbstractCrudRepository<Long, Car> {

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }

    public List<Car> getAvailableCars(Session session, CarFilter filter, EntityGraph<Car> graph) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getStatus() != null) {
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
