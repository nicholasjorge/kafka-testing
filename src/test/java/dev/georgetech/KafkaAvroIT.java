package dev.georgetech;

import static org.assertj.core.api.Assertions.assertThat;

import dev.georgetech.avro.Dance;
import dev.georgetech.kafka.avro.KafkaAvroConsumer;
import dev.georgetech.kafka.avro.KafkaAvroProducer;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@SpringBootTest(properties = {
    "embedded.kafka.enabled=true",
    "embedded.kafka.schema-registry.enabled=true"
})
class KafkaAvroIT {

  @Autowired
  private KafkaAvroProducer kafkaAvroProducer;

  @Autowired
  private KafkaAvroConsumer kafkaAvroConsumer;

  @Test
  void injectedComponentsShouldNotBeNull() {
    assertThat(kafkaAvroProducer).isNotNull();
    assertThat(kafkaAvroConsumer).isNotNull();
  }

  @Test
  void shouldSendAndReceiveKafkaMessage() {

    kafkaAvroProducer.send(null, new Dance(1, "Samba"));

    Awaitility.waitAtMost(5, TimeUnit.SECONDS)
        .pollDelay(1, TimeUnit.SECONDS)
        .untilAsserted(() -> assertThat(kafkaAvroConsumer.getLatch().getCount()).isZero());
  }
}
