package guru.springframework.spring7restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Modified by Pierrot on 2026-03-02.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerDTO {
    private UUID id;
    private String name;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
