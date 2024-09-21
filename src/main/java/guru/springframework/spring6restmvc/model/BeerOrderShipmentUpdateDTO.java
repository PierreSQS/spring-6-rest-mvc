package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Pierrot on 21-09-2024.
 */
@Data
@Builder
public class BeerOrderShipmentUpdateDTO {

    @NotBlank
    private String trackingNumber;
}
