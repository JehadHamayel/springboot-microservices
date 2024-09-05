package org.ms.user.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class DeleteUserTopicConfig {

    @Bean
    public NewTopic wikimediaTopic() {
        return TopicBuilder
                .name("delete_user")
                .build();
    }
}
