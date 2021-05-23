package microprocesses;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 * <br>
 * <code>spring.async.Controller</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 08 March 2021
 */
@RestController
@RequiredArgsConstructor
public class Controller {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/spring-async")
    public ResponseEntity<Void> produce(@RequestBody EventMessage eventMessage) {
        kafkaTemplate.send("process_a_topic", eventMessage.getMessage());

        return ResponseEntity.noContent().build();
    }

    @Data
    public static class EventMessage {
        private String message;
    }
}
