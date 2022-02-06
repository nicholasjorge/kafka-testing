package dev.georgetech.config.json;

import dev.georgetech.kafka.json.model.Dance;
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
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaJsonProducerConfig {

  @Autowired
  private KafkaProperties kafkaProperties;

  private Map<String, Object> jsonProducerConfigs() {
    HashMap<String, Object> configs = new HashMap<>();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return configs;
  }

  @Bean
  public ProducerFactory<String, Dance> jsonProducerFactory() {
    return new DefaultKafkaProducerFactory<>(jsonProducerConfigs());
  }

  @Bean
  public KafkaTemplate<String, Dance> jsonKafkaTemplate(
      ProducerFactory<String, Dance> jsonProducerFactory) {
    return new KafkaTemplate<>(jsonProducerFactory);
  }
}
