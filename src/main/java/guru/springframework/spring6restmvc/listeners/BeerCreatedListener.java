package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.events.BeerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by Pierrot on 2024-10-16.
 */
@Slf4j
@Component
public class BeerCreatedListener {

    @EventListener
    public void listen(BeerCreatedEvent beerCreatedEvent) {
        log.info("I hear a beer was created!");
        log.info(beerCreatedEvent.getBeer().getId().toString());

        //todo impl - add real implementation to persist audit record
    }
}
