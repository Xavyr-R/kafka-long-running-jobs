package microprocesses;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
@KafkaListener(topics = "process_b_topic")
public class ConsumerB {
    private final ProcessB processB;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaHandler
    public void handleEvent(@Payload String event) {
        log.info("Handling the event with body {} the spring async way", event);

        String resultB = processB.run(event);

        kafkaTemplate.send("process_a_topic", resultB);
    }
}
