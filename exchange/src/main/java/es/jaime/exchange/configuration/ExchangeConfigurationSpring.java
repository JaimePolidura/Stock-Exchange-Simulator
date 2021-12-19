package es.jaime.exchange.configuration;

import es.jaime.exchange.domain.ExchangeConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

@Component("exchange-configuration")
public class ExchangeConfigurationSpring implements ApplicationRunner, ExchangeConfiguration {
    private final Map<String, String> configuration;

    public ExchangeConfigurationSpring() {
        this.configuration = new HashMap<>();
    }

    @Override
    public void run(ApplicationArguments args) {
        String ticker = args.getSourceArgs()[0];

        System.out.println("ticker -> " + ticker);

        this.configuration.put("ticker", ticker);
        this.configuration.put("rabbitmq-executedOrders-exchange.name", "sxs.executed-orders");
        this.configuration.put("rabbitmq-executedOrders-queue.name", "sxs.executed-orders");

        this.configuration.put("rabbitmq-errorOrders-exchange.name", "sxs.error-orders");
        this.configuration.put("rabbitmq-errorOrders-queue.name", "sxs.error-orders");
        this.configuration.put("matchcingengine-sleep", "100");

//        try {
//            // create and set properties into properties object
//            Properties props = new Properties();
//            props.setProperty("queue", "sxs.new-orders." + ticker);
//            // get or create the file
//            File file = new File("classpath:application.properties");
//            OutputStream out = new FileOutputStream(file);
//
//            DefaultPropertiesPersister p = new DefaultPropertiesPersister();
//            p.store(props, out, "Header COmment");
//            out.close();
//        } catch (Exception e ) {
//            e.printStackTrace();
//        }
//
//        try{
//            File myObj = new File("classpath:application.properties");
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                System.out.println(data);
//            }
//            myReader.close();
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String getTicker(){
        return String.valueOf(this.configuration.get("ticker"));
    }

    @Override
    public String errorOrdersExchangeName(){
        return String.valueOf(this.configuration.get("rabbitmq-errorOrders-exchange.name"));
    }

    @Override
    public String errorOrdersQueueName(){
        return String.valueOf(this.configuration.get("rabbitmq-errorOrders-queue.name"));
    }

    @Override
    public String executedOrdersExchangeName(){
        return String.valueOf(this.configuration.get("rabbitmq-executedOrders-exchange.name"));
    }

    @Override
    public String executedOrdersQueueName(){
        return String.valueOf(this.configuration.get("rabbitmq-executedOrders-queue.name"));
    }

    @Override
    public int matchingEngineSleep(){
        return Integer.parseInt(this.configuration.get("matchcingengine-sleep"));
    }

    @Bean("ticker")
    public String ticker(){
        return this.configuration.get("ticker");
    }
}
