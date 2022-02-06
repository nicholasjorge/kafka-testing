package dev.georgetech.kafka.avro;

import dev.georgetech.avro.Dance;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaAvroConsumer {

  private final CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(
      topics = "test-avro",
      groupId = "test-avro",
      containerFactory = "kafkaAvroListenerContainerFactory"
  )
  public void consume(ConsumerRecord<String, Dance> payload) {
    log.info("Received payload: {}", payload);
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}
