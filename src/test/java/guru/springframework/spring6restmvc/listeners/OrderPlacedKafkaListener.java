package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.config.KafkaConfig;
import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class OrderPlacedKafkaListener {

    AtomicInteger msgCounter = new AtomicInteger(0);

    @KafkaListener(topics = KafkaConfig.ORDER_PLACED_TOPIC, groupId = "KafkaIntegrationTestGroup")
    public void onReceive(OrderPlacedEvent message) {
        // Process the received message
        log.info("Received message: {}", message);
        msgCounter.incrementAndGet();
    }
}
