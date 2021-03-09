package pause.container;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * LongRunningJob
 * <br>
 * <code>pause.container.LongRunningJob</code>
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
        log.info("Starting long running job the pause container way for event {}", event);

        Thread.sleep(Duration.ofMinutes(10).toMillis());

        log.info("Done with long running job the pause container way for event {}", event);
    }
}
