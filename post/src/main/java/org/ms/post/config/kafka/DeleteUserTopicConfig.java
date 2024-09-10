package org.ms.post.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class responsible for setting up Kafka topics for the application.
 * <p>
 *     This class creates a Kafka topic for handling the 'delete_user' events.
 * </p>
 */
@Configuration
public class DeleteUserTopicConfig {

    /**
     * Creates a Kafka topic named 'delete_user' using Spring's TopicBuilder.
     *
     * @return a new {@link NewTopic} instance for the 'delete_user' topic.
     */
    @Bean
    public NewTopic wikimediaTopic() {
        return TopicBuilder
                .name("delete_user")
                .build();
    }
}
