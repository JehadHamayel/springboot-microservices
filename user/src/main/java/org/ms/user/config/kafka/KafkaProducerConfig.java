package org.ms.user.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka producer.
 * <p>
 * This class provides the configuration settings necessary to create Kafka producers.
 * It defines the producer factory and Kafka template beans, which are used to send
 * messages to Kafka topics.
 * </p>
 *
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * The address of the Kafka bootstrap servers.
     * <p>
     *     This property is defined in the application.properties file and is used to
     *     configure the Kafka producer.
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
     * Provides the configuration settings for the Kafka producer.
     * <p>
     *     This method sets up the necessary properties for Kafka producers, including
     *     the bootstrap servers and serializers for keys and values.
     * </p>
     *
     * @return a {@link Map} containing the producer configuration properties
     */
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     * Creates a {@link ProducerFactory} bean for Kafka producers.
     * <p>
     * This bean uses the configuration properties defined in the {@link #producerConfig()} method
     * to create and manage Kafka producers. The {@link ProducerFactory} is responsible for creating
     * producer instances that can be used to send messages to Kafka topics.
     * </p>
     *
     * @return a {@link ProducerFactory} for Kafka producers
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Creates a {@link KafkaTemplate} bean for sending messages to Kafka topics.
     * <p>
     *     This bean uses the {@link ProducerFactory} to create a Kafka template that
     *     can be used to send messages to Kafka.
     * </p>
     *
     * @param producerFactory the {@link ProducerFactory} used to create the Kafka template
     * @return a {@link KafkaTemplate} for sending messages to Kafka topics
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(
            ProducerFactory<String, String> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
