package dev.georgetech.config.json;

import dev.georgetech.kafka.json.model.Dance;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaJsonConsumerConfig {

  @Autowired
  private KafkaProperties kafkaProperties;

  private Map<String, Object> jsonConsumerConfigs() {
    HashMap<String, Object> configs = new HashMap<>();
    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    return configs;
  }

  @Bean
  public ConsumerFactory<String, Dance> jsonConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        jsonConsumerConfigs(),
        new StringDeserializer(),
        new JsonDeserializer<>(Dance.class)
    );
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Dance>>
  kafkaJsonListenerContainerFactory(ConsumerFactory<String, Dance> jsonConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, Dance> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(jsonConsumerFactory);
    return factory;
  }
}
