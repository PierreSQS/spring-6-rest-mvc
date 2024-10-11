package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Modified by Pierrot on 2024-10-11.
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id

    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
    private String name;

    @Column()
    private String email;

    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private Set<BeerOrder> beerOrders = new HashSet<>();
}
