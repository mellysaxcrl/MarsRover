package com.mellysa.dynamicPartitioning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaListenerConfig {

    private final static String TOPIC = "mars-rover";

    @Autowired
    private final KafkaPartitionService kafkaPartitionService;

    public KafkaListenerConfig(KafkaPartitionService kafkaPartitionService) {
        this.kafkaPartitionService = kafkaPartitionService;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        int partitionCount = kafkaPartitionService.getNumberOfPartitions(TOPIC);

        factory.setConcurrency(partitionCount);

        return factory;
    }

}

