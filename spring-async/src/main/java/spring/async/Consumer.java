package spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Consumer
 * <br>
 * <code>spring.async.Consumer</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "spring_async_topic")
public class Consumer {
    private final LongRunningJob longRunningJob;

    @KafkaHandler
    public void handleEvent(@Payload String event) {
        log.info("Handling the event with body {} the spring async way", event);

        longRunningJob.run(event);
    }
}
