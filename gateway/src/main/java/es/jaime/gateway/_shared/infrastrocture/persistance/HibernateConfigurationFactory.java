package es.jaime.gateway._shared.infrastrocture.persistance;

import lombok.SneakyThrows;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class HibernateConfigurationFactory {
    private final ResourcePatternResolver resourceResolver;

    public HibernateConfigurationFactory(ResourcePatternResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(hibernateProperties());

        List<Resource> mappingFiles = mappingEntityDatabaseFiles();

        sessionFactory.setMappingLocations(mappingFiles.toArray(new Resource[0]));

        return sessionFactory;
    }

    public DataSource dataSource(String host, Integer port, String databaseName, String username, String password) throws IOException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(
                String.format(
                        "jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                        host,
                        port,
                        databaseName
                )
        );
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        Resource mysqlResource = resourceResolver.getResource(String.format(
                "classpath:database/%s.sql",
                databaseName
        ));
        String mysqlSentences = new Scanner(mysqlResource.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();

        dataSource.setConnectionInitSqls(new ArrayList<>(Arrays.asList(mysqlSentences.split(";"))));

        return dataSource;
    }

    @SneakyThrows
    private List<Resource> mappingEntityDatabaseFiles() {
        return List.of(
                resourceResolver.getResource("classpath:database/orders.hbm.xml"),
                resourceResolver.getResource("classpath:database/listedcompanies.hbm.xml"),
                resourceResolver.getResource("classpath:database/trades.hbm.xml"),
                resourceResolver.getResource("classpath:database/closedpositions.hbm.xml")
        );
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(AvailableSettings.HBM2DDL_AUTO, "none");
        hibernateProperties.put(AvailableSettings.SHOW_SQL, "false");
        hibernateProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

        return hibernateProperties;
    }
}
