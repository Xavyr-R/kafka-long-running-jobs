package microprocesses;

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
public class ProcessA {
    @SneakyThrows
    public String run(String event) {
        log.info("Starting microprocess A for event {}", event);

        Thread.sleep(Duration.ofMinutes(3).toMillis());

        log.info("Done with microprocess A for event {}", event);

        return event + ", A completed";
    }
}
