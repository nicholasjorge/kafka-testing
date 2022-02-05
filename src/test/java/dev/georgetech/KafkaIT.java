package dev.georgetech;

import static org.assertj.core.api.Assertions.assertThat;

import dev.georgetech.kafka.KafkaConsumer;
import dev.georgetech.kafka.KafkaProducer;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@SpringBootTest
class KafkaIT {

  @Autowired
  private KafkaProducer kafkaProducer;

  @Autowired
  private KafkaConsumer kafkaConsumer;

  @Test
  void injectedComponentsShouldNotBeNull() {
    assertThat(kafkaProducer).isNotNull();
    assertThat(kafkaConsumer).isNotNull();
  }

  @Test
  void shouldSendAndReceiveKafkaMessage() {

    kafkaProducer.send("test data");

    Awaitility.waitAtMost(5, TimeUnit.SECONDS)
        .pollDelay(1, TimeUnit.SECONDS)
        .untilAsserted(() -> assertThat(kafkaConsumer.getLatch().getCount()).isZero());
  }
}
