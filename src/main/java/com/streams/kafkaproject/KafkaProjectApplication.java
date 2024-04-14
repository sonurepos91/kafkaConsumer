package com.streams.kafkaproject;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KafkaProjectApplication {

    public static void main (String[] args) {
        SpringApplication.run(KafkaProjectApplication.class, args);


        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093,127.0.0.1:9094,127.0.0.1:9095");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(configProps);
        consumer.subscribe(Arrays.asList("test-consumer-api"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(2000);
            records.forEach(record -> {
                System.out.println((".....Received Message... " + "" + "\n" + "  ....Received Keys....  " + record.key() +
                        " ....Received Partition.....  " + record.partition() + "  .......Received Topics....  " + record.topic() +
                        " .....Received Offsets........  " + record.offset()));
            });
        }
    }
}
