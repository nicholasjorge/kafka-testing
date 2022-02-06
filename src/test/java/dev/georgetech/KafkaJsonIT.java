package dev.georgetech;

import static org.assertj.core.api.Assertions.assertThat;

import dev.georgetech.kafka.json.KafkaJsonConsumer;
import dev.georgetech.kafka.json.KafkaJsonProducer;
import dev.georgetech.kafka.json.model.Dance;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@SpringBootTest(properties = "embedded.kafka.enabled=true")
class KafkaJsonIT {

  @Autowired
  private KafkaJsonProducer kafkaJsonProducer;

  @Autowired
  private KafkaJsonConsumer kafkaJsonConsumer;

  @Test
  void injectedComponentsShouldNotBeNull() {
    assertThat(kafkaJsonProducer).isNotNull();
    assertThat(kafkaJsonConsumer).isNotNull();
  }

  @Test
  void shouldSendAndReceiveKafkaMessage() {

    kafkaJsonProducer.send(new Dance(1, "samba"));

    Awaitility.waitAtMost(5, TimeUnit.SECONDS)
        .pollDelay(1, TimeUnit.SECONDS)
        .untilAsserted(() -> assertThat(kafkaJsonConsumer.getLatch().getCount()).isZero());
  }

}
