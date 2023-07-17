package com.id.dao;

import com.id.entity.Client;
import com.id.entity.Order;
import com.id.filters.ClientFilter;
import com.id.predicates.QPredicate;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import java.util.ArrayList;
import java.util.List;

import static com.id.entity.QClient.client;
import static com.id.entity.QOrder.order;
import static com.id.entity.QUser.user;

public class OrderDao {


    public List<Order> getOrdersByFirstAndLastnames(Session session, ClientFilter filter, EntityGraph<Order> graph) {
//        var predicate = QPredicate.builder()
//                .add(filter.getFirstName(), user.firstName::eq)
//                .add(filter.getLastName(), user.lastName::eq)
//                .buildAnd();
//        ПОЧЕМУ-ТО ВЕРХНИЙ ВАРИАНТ НЕ РАБОТАЕТ
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstName() != null) {
            predicates.add(client.firstName.eq(filter.getFirstName()));
        }
        if (filter.getLastName() != null) {
            predicates.add(client.lastName.eq(filter.getLastName()));
        }

        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .where(predicates.toArray(Predicate[]::new))
                .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                .fetch();
    }
}
