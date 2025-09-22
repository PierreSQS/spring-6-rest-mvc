package guru.springframework.spring6restmvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@JsonDeserialize(builder = BeerDTO.BeerDTOBuilder.class)
@Builder
@Data
public class BeerDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("beerName")
    private String beerName;

    @JsonProperty("beerStyle")
    private BeerStyle beerStyle;

    @JsonProperty("upc")
    private String upc;

    @JsonProperty("quantityOnHand")
    private Integer quantityOnHand;

    @JsonProperty("price")
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
