package guru.springframework.spring6restmvc.events;

import guru.springframework.spring6restmvc.entities.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

/**
 * Created by Pierrot on 2024-10-16.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BeerCreatedEvent {

    private Beer beer;

    private Authentication authentication;
}
