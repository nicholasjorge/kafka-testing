package dev.georgetech;

import static org.assertj.core.api.Assertions.assertThat;

import dev.georgetech.kafka.string.KafkaStringConsumer;
import dev.georgetech.kafka.string.KafkaStringProducer;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@SpringBootTest(properties = "embedded.kafka.enabled=true")
class KafkaStringIT {

  @Autowired
  private KafkaStringProducer kafkaStringProducer;

  @Autowired
  private KafkaStringConsumer kafkaStringConsumer;

  @Test
  void injectedComponentsShouldNotBeNull() {
    assertThat(kafkaStringProducer).isNotNull();
    assertThat(kafkaStringConsumer).isNotNull();
  }

  @Test
  void shouldSendAndReceiveKafkaMessage() {

    kafkaStringProducer.send("test data");

    Awaitility.waitAtMost(5, TimeUnit.SECONDS)
        .pollDelay(1, TimeUnit.SECONDS)
        .untilAsserted(() -> assertThat(kafkaStringConsumer.getLatch().getCount()).isZero());
  }
}
