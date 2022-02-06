package dev.georgetech.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic topicString() {
    return TopicBuilder.name("test-string")
        .build();
  }

  @Bean
  public NewTopic topicAvro() {
    return TopicBuilder.name("test-avro")
        .build();
  }

  @Bean
  public NewTopic topicJson() {
    return TopicBuilder.name("test-json")
        .build();
  }
}
