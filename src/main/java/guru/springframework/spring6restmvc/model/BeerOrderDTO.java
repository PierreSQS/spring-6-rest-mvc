package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BeerOrderDTO {
    private UUID id;
    private Integer version;

    private String customerRef;

    @NotBlank
    @NotNull
    private CustomerDTO customer;

    @NotNull
    private Set<BeerOrderLineDTO> beerOrderLinesDTO;

    private BeerOrderShipmentDTO beerOrderShipmentDTO;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private LocalDateTime lastModifiedDate;

}
