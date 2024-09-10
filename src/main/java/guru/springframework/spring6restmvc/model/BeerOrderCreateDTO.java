package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Pierrot on 10-09-2024.
 */
@Data
@Builder
public class BeerOrderCreateDTO {

    private String customerRef;

    @NotNull
    private UUID customerID;

    private Set<BeerOrderLineCreateDTO> beerOrderLineCreateDTOS;
}
