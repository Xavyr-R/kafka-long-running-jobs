package pause.container.with.acknowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Application
 * <br>
 * <code>pause.container.Application</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
