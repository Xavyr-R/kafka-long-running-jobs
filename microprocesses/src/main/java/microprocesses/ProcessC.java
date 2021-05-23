package microprocesses;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
public class ProcessC {
    @SneakyThrows
    public String run(String event) {
        log.info("Starting microprocess C for event {}", event);

        Thread.sleep(Duration.ofMinutes(3).toMillis());

        log.info("Done with microprocess C for event {}", event);

        return event + ", C processed";
    }
}
