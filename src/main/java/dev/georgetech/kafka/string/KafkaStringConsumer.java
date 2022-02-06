package dev.georgetech.kafka.string;

import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaStringConsumer {

  private final CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(
      topics = "test-string",
      groupId = "test-string",
      containerFactory = "kafkaStringListenerContainerFactory"
  )
  public void consume(String data) {
    log.info("Received data: {}", data);
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }
}
