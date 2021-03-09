package consumer.aware;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Consumer
 * <br>
 * <code>consumer.aware.Consumer</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "consumer_aware_topic")
public class Consumer {
    private final LongRunningJob longRunningJob;
    private final AsyncListenableTaskExecutor executor;

    @KafkaHandler
    public void handleEvent(@Payload String event, org.apache.kafka.clients.consumer.Consumer<?,?> consumer) {
        log.info("Handling the event with body {} the consumer aware listener way", event);

        Collection<TopicPartition> topicPartitions = consumer.partitionsFor("consumer_aware_topic").stream()
                .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition()))
                .collect(Collectors.toList());

        consumer.pause(topicPartitions);

        executor.submitListenable(() -> longRunningJob.run(event))
                .addCallback(result -> {
                            consumer.resume(topicPartitions);
                            log.info("Success callback");
                        },
                        ex -> {
                            //perform retry mechanism like a dead letter queue here
                            consumer.resume(topicPartitions);
                            log.warn("Error callback");
                        }
                );
    }
}
