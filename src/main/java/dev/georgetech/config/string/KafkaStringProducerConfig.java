package dev.georgetech.config.string;

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
public class KafkaStringProducerConfig {

  @Autowired
  private KafkaProperties kafkaProperties;

  private Map<String, Object> stringProducerConfigs() {
    HashMap<String, Object> configs = new HashMap<>();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return configs;
  }

  @Bean
  public ProducerFactory<String, String> stringProducerFactory() {
    return new DefaultKafkaProducerFactory<>(stringProducerConfigs());
  }

  @Bean
  public KafkaTemplate<String, String> stringKafkaTemplate(
      ProducerFactory<String, String> stringProducerFactory) {
    return new KafkaTemplate<>(stringProducerFactory);
  }

}
