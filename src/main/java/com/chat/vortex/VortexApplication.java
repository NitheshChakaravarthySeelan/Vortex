package com.chat.vortex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VortexApplication {

    public static void main(String[] args) {
        tryStartEmbeddedKafka();
        SpringApplication.run(VortexApplication.class, args);
    }

    private static void tryStartEmbeddedKafka() {
        try {
            Class<?> brokerClass = Class.forName("org.springframework.kafka.test.EmbeddedKafkaKraftBroker");
            Object broker = brokerClass.getConstructor(int.class, int.class, String[].class)
                .newInstance(1, 2, new String[] { "messages", "channels" });
            brokerClass.getMethod("afterPropertiesSet").invoke(broker);
            String bs = (String) brokerClass.getMethod("getBrokersAsString").invoke(broker);
            System.setProperty("spring.kafka.bootstrap-servers", bs);
        } catch (ClassNotFoundException e) {
            // spring-kafka-test not on classpath — using default localhost:9092
        } catch (Exception e) {
            System.err.println("WARN: Could not start embedded Kafka: " + e.getMessage());
        }
    }

}
