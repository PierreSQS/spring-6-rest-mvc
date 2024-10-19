package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.events.BeerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Modified by Pierrot on 2024-10-19.
 */
@Slf4j
@Component
public class BeerCreatedListener {

    @Async // Marks the method as candidate for asynchronous execution
    @EventListener
    public void listen(BeerCreatedEvent beerCreatedEvent) {
        log.info("I hear a beer was created!");
        log.info(beerCreatedEvent.getBeer().getId().toString());

        log.info("Current Thread Name: {}", Thread.currentThread().getName());
        log.info("Current Thread ID: {}", Thread.currentThread().threadId());

        //todo impl - add real implementation to persist audit record
    }
}
