package org.ms.post.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka consumer properties and beans.
 *
 * <p>
 *     This class configures the Kafka consumer to connect to the Kafka broker,
 *     deserialize incoming messages, and manage Kafka listener containers.
 * </p>
 *
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * The address of the Kafka bootstrap servers.
     * <p>
     *     This property is defined in the application.properties file and is used to
     *     configure the Kafka consumer.
     *     The bootstrap servers are the entry points for the Kafka cluster and are used
     *     to establish connections to the Kafka brokers.
     *     The value of this property is set to the 'spring.kafka.bootstrap-servers' key
     *     in the application.properties file.
     *     For example:
     *     <pre>
     *         spring.kafka.bootstrap-servers=localhost:9092
     *     </pre>
     *     In this case, the bootstrap servers are running on the local machine
     *     and listening on port 9092.
     *</p>
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     *
     * Creates a map of consumer configuration properties for connecting
     * to Kafka and consuming messages.
     *
     * @return a map of Kafka consumer configuration properties.
     */
    public Map<String, Object> consumerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "post_user_group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    /**
     * Creates a {@link ConsumerFactory} bean for Kafka consumers.
     * <p>
     * This bean uses the configuration properties defined in the {@link #consumerConfig()} method
     * to create and manage Kafka consumers. The {@link ConsumerFactory} is responsible for creating
     * consumer instances that can be used to receive messages from Kafka topics.
     * </p>
     *
     * @return a {@link ConsumerFactory} for Kafka consumers
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }


    /**
     * Creates a {@link KafkaListenerContainerFactory} that produces Kafka listener containers.
     * <p>
     *     This factory allows creating containers that listen for Kafka messages and process them
     *     concurrently using the provided consumer factory.
     * </p>
     *
     * @param consumerFactory the factory to use for creating Kafka consumers.
     * @return a new {@link ConcurrentKafkaListenerContainerFactory} instance for handling String keys and values.
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(
            ConsumerFactory<String, String> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
