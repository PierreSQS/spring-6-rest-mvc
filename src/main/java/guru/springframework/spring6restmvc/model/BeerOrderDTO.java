package guru.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Builder
@Data
public class BeerOrderDTO {
    private UUID id;
    private Long version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private String customerRef;

    private CustomerDTO customer;

    private BigDecimal paymentAmount;

    private Set<BeerOrderLineDTO> beerOrderLines;

    private BeerOrderShipmentDTO beerOrderShipment;
}
