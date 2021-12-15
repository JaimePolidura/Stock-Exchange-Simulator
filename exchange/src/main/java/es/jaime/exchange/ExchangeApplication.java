package es.jaime.exchange;

import es.jaime.exchange.domain.MatchingEngine;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class ExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeApplication.class, args);
	}

	@Bean
	public CachingConnectionFactory connection() {
		CachingConnectionFactory factory = new CachingConnectionFactory();

		//TODO
//		factory.setHost("rabbitmq");
//		factory.setPort(5672);

		return factory;
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("MatchingEngine-");
		executor.initialize();

		return executor;
	}

	@Component
	public static class ThreadStarter implements CommandLineRunner {
		private final MatchingEngine matchingEngine;

		public ThreadStarter(MatchingEngine matchingEngine) {
			this.matchingEngine = matchingEngine;
		}

		@Override
		public void run(String... args) {
			matchingEngine.run();
		}
	}
}
