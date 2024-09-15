package com.nucleodb.cluster.communication;

import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.cluster.raft.common.Status;
import com.nucleodb.cluster.raft.leadership.LeaderCommand;
import com.nucleodb.cluster.raft.leadership.LeaderRequest;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    public static final String RaftTopic = "raft-comms";

    @Bean
    public BatchMessagingMessageConverter batchConverter() {
        return new BatchMessagingMessageConverter(converter());
    }

    @Bean
    public RecordMessageConverter converter() {
        JsonMessageConverter converter = new JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("com.nucleodb.cluster");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("status", Status.class);
        mappings.put("leaderRequest", LeaderRequest.class);
        mappings.put("leader", LeaderCommand.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public NewTopic leaderCommandTopic() {
        return TopicBuilder
                .name(RaftTopic)
                .partitions(1)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "5000")  // Set retention to 5 seconds
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, "delete")  // Ensure messages are deleted
                .config(TopicConfig.SEGMENT_MS_CONFIG, "5000")  // Ensure segments are deleted quickly
                .config(TopicConfig.DELETE_RETENTION_MS_CONFIG, "5000")
                .build();
    }


}
