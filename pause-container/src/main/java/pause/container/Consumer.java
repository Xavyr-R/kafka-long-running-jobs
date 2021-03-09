package pause.container;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Consumer
 * <br>
 * <code>pause.container.Consumer</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "pause_container_topic", id = "${kafka.container.id}", idIsGroup = false)
public class Consumer {
    private final LongRunningJob longRunningJob;
    private final AsyncListenableTaskExecutor executor;
    private final KafkaContainerSupportMethods containerSupportMethods;

    @Value("${kafka.container.id}")
    private String containerId;

    @KafkaHandler
    public void handleEvent(@Payload String event) {
        log.info("Handling the event with body {} the pause container way", event);

        containerSupportMethods.pauseConsume(containerId);

        executor.submitListenable(() -> longRunningJob.run(event))
                .addCallback(result -> {
                            containerSupportMethods.resumeConsumer(containerId);
                            log.info("Success callback");
                        },
                        ex -> {
                            //perform retry mechanism like a dead letter queue here
                            containerSupportMethods.resumeConsumer(containerId);
                            log.warn("Error callback");
                        }
                );
    }
}
