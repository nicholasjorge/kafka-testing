package dev.georgetech;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "embedded.kafka.enabled=true")
class KafkaTestingApplicationIT {

  @Test
  void contextLoads() {

  }

}
