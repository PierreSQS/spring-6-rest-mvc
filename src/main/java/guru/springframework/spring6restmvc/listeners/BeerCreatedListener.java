package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.events.BeerCreatedEvent;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.repositories.BeerAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Modified by Pierrot on 2024-10-19.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerCreatedListener {

    private final BeerMapper beerMapper;
    private final BeerAuditRepository beerAuditRepo;

    @Async // Marks the method as candidate for asynchronous execution
    @EventListener
    public void listen(BeerCreatedEvent beerCreatedEvent) {
        log.info("Current Thread Name: {}", Thread.currentThread().getName());
        log.info("Current Thread ID: {}", Thread.currentThread().threadId());

        val beerAudit = beerMapper.beerToBeerAudit(beerCreatedEvent.getBeer());
        beerAudit.setAuditEventType("BEER CREATED");

        Authentication authentication = beerCreatedEvent.getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            beerAudit.setPrincipalName(authentication.getName());
        }

        val savedBeerAudit = beerAuditRepo.save(beerAudit);

        log.debug("Beer Audit Event Created!");
        log.debug("Beer created ID: {}",savedBeerAudit.getId().toString());

    }
}
