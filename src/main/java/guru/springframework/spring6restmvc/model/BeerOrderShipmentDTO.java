package guru.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Pierrot, on 2024-08-30.
 */
@Data
@Builder
// the reverse-association BeerOrder is not Mapped!!!
public class BeerOrderShipmentDTO {

    private UUID id;

    private Long version;

    private String trackingNumber;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
