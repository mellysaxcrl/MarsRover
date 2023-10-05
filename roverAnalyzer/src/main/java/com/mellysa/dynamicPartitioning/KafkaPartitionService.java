package com.mellysa.dynamicPartitioning;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaPartitionService {

    @Autowired
    private KafkaAdmin kafkaAdmin;

    public int getNumberOfPartitions(String topicName) {

        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Collections.singletonList(topicName));
            return describeTopicsResult.values().get(topicName).get().partitions().size();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
