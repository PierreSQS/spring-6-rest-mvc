package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.events.BeerCreatedEvent;
import guru.springframework.spring6restmvc.events.BeerDeletedEvent;
import guru.springframework.spring6restmvc.events.BeerEvent;
import guru.springframework.spring6restmvc.events.BeerPatchedEvent;
import guru.springframework.spring6restmvc.events.BeerUpdatedEvent;
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
    public void listen(BeerEvent beerEvent) {
        log.info("Current Thread Name: {}", Thread.currentThread().getName());
        log.info("Current Thread ID: {}", Thread.currentThread().threadId());

        val beerAudit = beerMapper.beerToBeerAudit(beerEvent.getBeer());

        String eventType;

        switch (beerEvent) {
            case BeerCreatedEvent ignored -> eventType = "BEER CREATED!";
            case BeerUpdatedEvent ignored -> eventType = "BEER UPDATED!";
            case BeerPatchedEvent ignored -> eventType = "BEER PATCHED!";
            case BeerDeletedEvent ignored -> eventType = "BEER DELETED!";
            default -> eventType = "UNKNOWN EVENT";
        }

        beerAudit.setAuditEventType(eventType);

        Authentication authentication = beerEvent.getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            beerAudit.setPrincipalName(authentication.getName());
        }

        val savedBeerAudit = beerAuditRepo.save(beerAudit);

        log.debug("Beer Audit Event Created!");
        log.debug("Beer created ID: {}",savedBeerAudit.getId().toString());

    }
}
