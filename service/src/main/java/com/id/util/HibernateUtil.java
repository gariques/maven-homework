package com.id.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        var configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
