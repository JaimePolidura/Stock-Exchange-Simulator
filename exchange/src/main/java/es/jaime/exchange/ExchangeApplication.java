package es.jaime.exchange;

import es.jaime.exchange.domain.services.MatchingOrderEngine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableAsync
public class ExchangeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExchangeApplication.class, args);
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
		private final MatchingOrderEngine matchingEngine;

		public ThreadStarter(MatchingOrderEngine matchingEngine) {
			this.matchingEngine = matchingEngine;
		}

		@Override
		public void run(String... args) {
			matchingEngine.run();
		}
	}
}
