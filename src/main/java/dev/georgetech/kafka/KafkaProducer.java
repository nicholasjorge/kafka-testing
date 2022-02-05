package dev.georgetech.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final NewTopic topic;

  public void send(String data) {
    kafkaTemplate.send(topic.name(), data);
    log.info("Sent data: {}", data);
  }

}
