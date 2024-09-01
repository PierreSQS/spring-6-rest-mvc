package guru.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Pierrot, on 2024-08-30.
 */
@Builder
@Data
// updatedDate muß not be mapped
public class BeerOrderDTO {
    private UUID id;
    private Integer version;

    private String customerRef;
    private CustomerDTO customer;

    private Set<BeerOrderLineDTO> beerOrderLinesDTO;

    private BeerOrderShipmentDTO beerOrderShipmentDTO;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
