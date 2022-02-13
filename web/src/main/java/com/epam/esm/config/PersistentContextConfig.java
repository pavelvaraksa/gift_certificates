package com.epam.esm.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

public class PersistentContextConfig {

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setPackagesToScan("com.epam.esm");
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(getAdditionalProperties());
        factoryBean.afterPropertiesSet();
        SessionFactory sessionFactory = factoryBean.getObject();
        System.out.println("getSessionFactory: " + sessionFactory);
        return sessionFactory;
    }

    private Properties getAdditionalProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        return properties;
    }
}
