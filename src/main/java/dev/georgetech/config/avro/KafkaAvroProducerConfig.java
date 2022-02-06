package dev.georgetech.config.avro;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import dev.georgetech.avro.Dance;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaAvroProducerConfig {

  @Autowired
  private KafkaProperties kafkaProperties;

  private Map<String, Object> avroProducerConfigs() {
    HashMap<String, Object> configs = new HashMap<>();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

    configs.put(SCHEMA_REGISTRY_URL_CONFIG,
        kafkaProperties.getProperties().get(SCHEMA_REGISTRY_URL_CONFIG));
    return configs;
  }

  @Bean
  public ProducerFactory<String, Dance> avroProducerFactory() {
    return new DefaultKafkaProducerFactory<>(avroProducerConfigs());
  }

  @Bean
  public KafkaTemplate<String, Dance> avroKafkaTemplate(
      ProducerFactory<String, Dance> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

}
