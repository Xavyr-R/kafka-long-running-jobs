package pause.container.with.acknowledge;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * KafkaContainerSupportMethods
 * <br>
 * <code>pause.container.with.acknowledge.KafkaContainerSupportMethods</code>
 * <br>
 *
 * @author Xavyr Rademaker
 * @since 19 January 2021
 */
@Service
@RequiredArgsConstructor
public class KafkaContainerSupportMethods {

    private final KafkaListenerEndpointRegistry registry;

    public void pauseConsume(String containerId) {
        getContainer(containerId).ifPresent(MessageListenerContainer::pause);
    }

    public void resumeConsumer(String containerId) {
        getContainer(containerId).ifPresent(MessageListenerContainer::resume);
    }


    private Optional<MessageListenerContainer> getContainer(String containerId) {
        return Optional.ofNullable(registry.getListenerContainer(containerId));
    }
}
