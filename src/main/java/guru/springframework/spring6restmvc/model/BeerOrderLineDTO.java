package guru.springframework.spring6restmvc.model;

import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Pierrot, on 2024-08-30.
 */
@Builder
@Data
public class BeerOrderLineDTO {
    private UUID id;

    @Version
    private Long version;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    // the reverse-association BeerOrderLine is not needed
    private BeerDTO beer;

    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
}
