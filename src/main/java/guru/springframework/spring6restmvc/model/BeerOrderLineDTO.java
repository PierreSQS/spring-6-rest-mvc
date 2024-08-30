package guru.springframework.spring6restmvc.model;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerOrder;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
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

    private Timestamp createdDate;

    private Timestamp lastModifiedDate;
    private BeerOrder beerOrder;

    private Beer beer;

    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
}
