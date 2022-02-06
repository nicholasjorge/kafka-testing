package dev.georgetech.kafka.json;

import dev.georgetech.kafka.json.model.Dance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaJsonProducer {

  private final KafkaTemplate<String, Dance> jsonKafkaTemplate;
  private final NewTopic topicJson;

  public void send(Dance dance) {
    ListenableFuture<SendResult<String, Dance>> future = jsonKafkaTemplate.send(topicJson.name(),
        dance);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, Dance> result) {
        log.info("Sent message={} with offset={}", dance, result.getRecordMetadata().offset());
      }

      @Override
      public void onFailure(Throwable ex) {
        log.error("Unable to send message={} due to : {}", dance, ex.getMessage());
      }
    });
  }
}
