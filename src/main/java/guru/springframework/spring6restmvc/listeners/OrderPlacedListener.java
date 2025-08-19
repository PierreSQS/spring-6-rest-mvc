package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.config.KafkaConfig;
import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Modified by Pierrot on 2025-08-19.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OrderPlacedListener {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Async
    @EventListener
    public void listen(OrderPlacedEvent event) {

        log.info("Received Order Placed Event: {}", event);

        kafkaTemplate.send(KafkaConfig.ORDER_PLACED_TOPIC, event);

        log.info("Order Placed Event Received");
    }
}
