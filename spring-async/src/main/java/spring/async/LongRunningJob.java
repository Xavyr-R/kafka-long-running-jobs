package spring.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * LongRunningJob
 * <br>
 * <code>spring.async.LongRunningJob</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@Service
@Slf4j
public class LongRunningJob {
    @SneakyThrows
    @Async
    public void run(String event) {
        log.info("Starting long running job with spring async approach for event {}", event);

        Thread.sleep(Duration.ofMinutes(10).toMillis());

        log.info("Done with long running job with spring async approach for event {}", event);
    }
}
