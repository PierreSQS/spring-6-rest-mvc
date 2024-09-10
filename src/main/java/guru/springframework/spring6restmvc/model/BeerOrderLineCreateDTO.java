package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Created by Pierrot on 10-09-2024.
 */
@Data
@Builder
public class BeerOrderLineCreateDTO {

    @NotNull
    private UUID beerID;

    @Min(value = 1, message = "Quantity On Hand must be at least 1")
    private Integer orderQuantity;
}
