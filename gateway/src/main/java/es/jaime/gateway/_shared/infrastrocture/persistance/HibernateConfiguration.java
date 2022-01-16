package es.jaime.gateway._shared.infrastrocture.persistance;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration("hibernate-configuration")
@EnableTransactionManagement
public class HibernateConfiguration {
    private final HibernateConfigurationFactory factory;

    public HibernateConfiguration(HibernateConfigurationFactory factory) {
        this.factory = factory;
    }

    @Bean("gateway-transaction-manager")
    public PlatformTransactionManager hibernateTransactionManager() {
        return this.hibernateTransactionManager(sessionFactory());
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        return factory.sessionFactory(dataSource());
    }

    @Bean
    @SneakyThrows
    public DataSource dataSource() {
        return factory.dataSource(
                "gateway-mysql",
                3306,
                "sxs_gateway",
                "root",
                ""
        );
    }

    private PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());

        return transactionManager;
    }
}
