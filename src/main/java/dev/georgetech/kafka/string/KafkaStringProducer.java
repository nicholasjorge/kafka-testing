package dev.georgetech.kafka.string;

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
public class KafkaStringProducer {

  private final KafkaTemplate<String, String> stringKafkaTemplate;
  private final NewTopic topicString;

  public void send(String data) {
    ListenableFuture<SendResult<String, String>> future = stringKafkaTemplate.send(topicString.name(),
        data);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        log.info("Sent message={} with offset={}", data, result.getRecordMetadata().offset());
      }

      @Override
      public void onFailure(Throwable ex) {
        log.error("Unable to send message={} due to : {}", data, ex.getMessage());
      }
    });
  }

}
