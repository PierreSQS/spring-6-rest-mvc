package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Pierrot, on 2024-08-30.
 */
@Builder
@Data
public class BeerOrderLineDTO {
    private UUID id;

    private Long version;
    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private BeerDTO beer;

    @Min(value = 1, message = "Quantity On Hand must be at least 1")
    private Integer orderQuantity;
    private Integer quantityAllocated;
}
