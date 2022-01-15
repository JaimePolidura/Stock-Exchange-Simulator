package es.jaime.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Source code lines: 4299
 * 4263
 * 4209
 * 4195
 * 4162
 * 4130
 * 4002
 * 3920
 * 3500?
 */
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
