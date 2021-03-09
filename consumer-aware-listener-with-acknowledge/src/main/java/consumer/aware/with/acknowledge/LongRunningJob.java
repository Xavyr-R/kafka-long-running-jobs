package consumer.aware.with.acknowledge;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * LongRunningJob
 * <br>
 * <code>consumer.aware.with.acknowledge.LongRunningJob</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@Service
@Slf4j
public class LongRunningJob {
    @SneakyThrows
    public void run(String event) {
        log.info("Starting long running job the consumer aware with acknowledgment for event {}", event);

        Thread.sleep(Duration.ofMinutes(10).toMillis());

        log.info("Done with long running job the consumer aware with acknowledgment for event {}", event);
    }
}
