package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import guru.springframework.spring6restmvcapi.model.BeerOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EmbeddedKafka(kraft = true, partitions = 1, controlledShutdown = true)
class OrderPlacedListenerTest {

    // Registry for managing Kafka listeners
    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    // Event listener to be tested
    @Autowired
    OrderPlacedListener orderPlacedListener;

    // Kafka listener to be tested
    @Autowired
    OrderPlacedKafkaListener orderPlacedKafkaListener;

    @BeforeEach
    void setUp() {
        // Ensure the partition is assigned to the listener before each test
        kafkaListenerEndpointRegistry.getAllListenerContainers().forEach(
            container -> ContainerTestUtils.waitForAssignment(container, 1)
        );

    }

    @Test
    void listen() {

        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                .beerOrderDTO(BeerOrderDTO.builder()
                        .id(UUID.randomUUID())
                        .build())
                .build();

        // This test will start the listener and wait for messages
        orderPlacedListener.listen(orderPlacedEvent);

        // Verify that the listener has processed the event

        // Wait at most 5s and poll every 500ms until the assertion is met
        // This is to ensure that the message has been processed and the counter has been incremented
        await().atMost(5, TimeUnit.SECONDS).pollInterval(500, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    // Check if the message counter has been incremented
                    assertThat(orderPlacedKafkaListener.msgCounter.get()).isEqualTo(1);
                });

    }
}