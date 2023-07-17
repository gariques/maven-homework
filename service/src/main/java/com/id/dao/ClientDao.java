package com.id.dao;

import com.id.entity.Client;
import com.id.filters.ClientFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import java.util.ArrayList;
import java.util.List;

import static com.id.entity.QClient.client;

public class ClientDao {

    public List<Client> getClientsByFirstAndLastnames(Session session, ClientFilter filter, EntityGraph<Client> graph) {
//        var predicate = QPredicate.builder()
//                .add(filter.getFirstName(), user.firstName::eq)
//                .add(filter.getLastName(), user.lastName::eq)
//                .buildAnd();
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstName() != null) {
            predicates.add(client.firstName.eq(filter.getFirstName()));
        }
        if (filter.getLastName() != null) {
            predicates.add(client.lastName.eq(filter.getLastName()));
        }

        return new JPAQuery<Client>(session)
                .select(client)
                .from(client)
                .where(predicates.toArray(Predicate[]::new))
                .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                .fetch();
    }

    public List<Client> findAllByFirstAndLastnames(Session session, String firstName, String lastName) {
        return new JPAQuery<Client>(session)
                .select(client)
                .from(client)
                .where(client.firstName.eq(firstName)
                        .and(client.lastName.eq(lastName)))
                .fetch();
    }
}
