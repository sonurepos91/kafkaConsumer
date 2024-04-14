package com.streams.kafkaproject.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;

public interface MessageConsumeInterface {

    void ListenThroughSpringKafkaConncurrentMessageListener(String message, String key, Integer partition,
                                                            String topic, Long offset) throws Exception;
}
