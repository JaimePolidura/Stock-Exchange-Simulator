package es.jaime.exchange;

import org.apache.logging.log4j.message.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ExchangeApplication {
	public static String ticker;

	public static void main(String[] args) {
		ensureCorrectArgs(args);

		ticker = args[0];

		SpringApplication.run(ExchangeApplication.class, args);
	}

	private static void ensureCorrectArgs(String[] args){
		if(args == null || args.length == 0){
			throw new IllegalArgumentException("Not enough arguments");
		}
	}

}
