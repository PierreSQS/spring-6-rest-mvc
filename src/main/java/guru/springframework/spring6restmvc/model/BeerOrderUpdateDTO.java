package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Pierrot on 21-09-2024.
 */
@Data
@Builder
public class BeerOrderUpdateDTO {
    private String customerRef;

    @NotNull
    private UUID customerID;

    private Set<BeerOrderLineUpdateDTO> beerOrderLineUpdateDTOs;

    private BeerOrderShipmentUpdateDTO beerOrderShipmentUpdateDTO;
}
