package com.attendance.swipe.SwipeService.Kafka;


import com.attendance.swipe.SwipeService.Employee.AttendanceEvent;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.common.config.SaslConfigs.SASL_JAAS_CONFIG;
import static org.apache.kafka.common.config.SaslConfigs.SASL_MECHANISM;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class KafkaConfiguration {
    @Bean
    KafkaTemplate<Long, AttendanceEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String saslJaasConfig;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;

    @Value("${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.properties.basic.auth.credentials.source}")
    private String credentialSource ;

    @Value("${spring.kafka.properties.basic.auth.user.info}")
    private String userInfo;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    ProducerFactory<Long, AttendanceEvent> producerFactory() {
       Map<String,Object> producerProperties=new HashMap<>();
        producerProperties.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.put(  RETRIES_CONFIG, 0);
        producerProperties.put(  BUFFER_MEMORY_CONFIG, 33554432);
        producerProperties.put(  KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        producerProperties.put(  VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        producerProperties.put(  SASL_JAAS_CONFIG, saslJaasConfig);
        producerProperties.put(  SASL_MECHANISM, saslMechanism);
        producerProperties.put(  CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);

        producerProperties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,schemaRegistryUrl);
        producerProperties.put(AbstractKafkaAvroSerDeConfig.USER_INFO_CONFIG,userInfo);
        producerProperties.put(AbstractKafkaAvroSerDeConfig.BASIC_AUTH_CREDENTIALS_SOURCE,credentialSource);
        return new DefaultKafkaProducerFactory<>(producerProperties);

    }

    @Bean
    ConsumerFactory<Long, AttendanceEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getKafkaProperties());
    }

    @Bean
    public Map<String, Object> getKafkaProperties() {
        return Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                GROUP_ID_CONFIG, "kafkaapp-consumer",
                SESSION_TIMEOUT_MS_CONFIG, 15000,
                RETRIES_CONFIG, 0,
                BUFFER_MEMORY_CONFIG, 33554432,
                KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                SASL_JAAS_CONFIG, saslJaasConfig,
                SASL_MECHANISM, saslMechanism,
                CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol
        );
    }
}
