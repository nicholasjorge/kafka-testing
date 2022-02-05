package dev.georgetech.kafka;

import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

  private final CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(
      topics = "test",
      groupId = "test"
  )
  public void consume(String data) {
    log.info("Received data: {}", data);
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }
}
