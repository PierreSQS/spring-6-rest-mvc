package guru.springframework.spring6restmvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@JsonDeserialize(builder = CustomerDTO.CustomerDTOBuilder.class)
@Data
@Builder
public class CustomerDTO {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private Integer version;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
