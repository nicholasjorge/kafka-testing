package dev.georgetech.kafka.json;

import dev.georgetech.kafka.json.model.Dance;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaJsonConsumer {

  private final CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(
      topics = "test-json",
      groupId = "test-json",
      containerFactory = "kafkaJsonListenerContainerFactory"
  )
  public void consume(Dance dance) {
    log.info("Received dance: {}", dance);
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }
}
