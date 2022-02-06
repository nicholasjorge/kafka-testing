package dev.georgetech.config.avro;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import dev.georgetech.avro.Dance;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

@Configuration
public class KafkaAvroConsumerConfig {

  @Autowired
  private KafkaProperties kafkaProperties;

  private Map<String, Object> avroConsumerConfigs() {
    HashMap<String, Object> configs = new HashMap<>();
    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

    // configure error handler for poison message
    configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    configs.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    configs.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, KafkaAvroDeserializer.class);

    // configure mapping to our custom object
    configs.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
    configs.put(SCHEMA_REGISTRY_URL_CONFIG,
        kafkaProperties.getProperties().get(SCHEMA_REGISTRY_URL_CONFIG));

    return configs;
  }

  @Bean
  public ConsumerFactory<String, ConsumerRecord<String, Dance>> avroConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(avroConsumerConfigs());
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ConsumerRecord<String, Dance>>>
  kafkaAvroListenerContainerFactory(
      ConsumerFactory<String, ConsumerRecord<String, Dance>> avroConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, ConsumerRecord<String, Dance>> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(avroConsumerFactory);
    return factory;
  }

}
