package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pierrot on 21-09-2024.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderShipmentUpdateDTO {

    @NotBlank
    private String trackingNumber;
}
