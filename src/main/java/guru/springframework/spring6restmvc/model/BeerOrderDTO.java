package guru.springframework.spring6restmvc.model;

import guru.springframework.spring6restmvc.entities.BeerOrderLine;
import guru.springframework.spring6restmvc.entities.BeerOrderShipment;
import guru.springframework.spring6restmvc.entities.Customer;
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
    private Customer customer;

    @NotNull
    private Set<BeerOrderLine> beerOrderLines;

    private BeerOrderShipment beerOrderShipment;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private LocalDateTime lastModifiedDate;

}
