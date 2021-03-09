package not.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Application
 * <br>
 * <code>not.async.Application</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
