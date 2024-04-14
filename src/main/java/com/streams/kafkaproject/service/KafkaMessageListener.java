package com.streams.kafkaproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streams.kafkaproject.model.StudentDTO;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaMessageListener implements MessageConsumeInterface {

    Logger logger = LogManager.getLogger(KafkaMessageListener.class);

    @Override
    @KafkaListener(topics = {"test-kafka", "my-topic", "kafka-topic"}, groupId = "my-group-id", containerFactory = "kafkaListenerContainerFactory")
    public void ListenThroughSpringKafkaConncurrentMessageListener
            (@Payload String message,
             @Header(KafkaHeaders.RECEIVED_KEY) String key,
             @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
             @Header(KafkaHeaders.OFFSET) Long offset) throws JsonProcessingException {
        logger.info(".....Received Message... " + getPayload(message) + "\n" + "  ....Received Keys....  " + key +
                " ....Received Partition.....  " + partition + "  .......Received Topics....  " + topic +
                " .....Received Offsets........  " + offset);
    }
    private String getPayload(String message) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        StudentDTO studentDTO = mapper.readValue(message, StudentDTO.class);
        return studentDTO.toString();
    }
}
