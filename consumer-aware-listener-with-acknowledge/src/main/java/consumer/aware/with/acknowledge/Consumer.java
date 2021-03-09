package consumer.aware.with.acknowledge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Consumer
 * <br>
 * <code>consumer.aware.with.acknowledge.Consumer</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "consumer_aware_with_acknowledge_topic")
public class Consumer {
    private final LongRunningJob longRunningJob;
    private final AsyncListenableTaskExecutor executor;

    @KafkaHandler
    public void handleEvent(@Payload String event, Acknowledgment acknowledgment, org.apache.kafka.clients.consumer.Consumer<?, ?> consumer) {
        log.info("Handling the event with body {} the consumer aware with acknowledgment way", event);

        Set<TopicPartition> assignedPartitions = consumer.assignment().stream()
                .filter(topicPartition -> "consumer_aware_with_acknowledge_topic".equalsIgnoreCase(topicPartition.topic()))
                .collect(Collectors.toSet());

        consumer.pause(assignedPartitions);

        executor.submitListenable(() -> longRunningJob.run(event))
                .addCallback(result -> {
                            acknowledgment.acknowledge();
                            consumer.resume(assignedPartitions);
                            log.info("Success callback");
                        },
                        ex -> {
                            //perform retry mechanism like a dead letter queue here
                            //alternatively nack the event with acknowledgement.nack(timeout)
                            acknowledgment.acknowledge();
                            consumer.resume(assignedPartitions);
                            log.warn("Error callback");
                        }
                );
    }
}
