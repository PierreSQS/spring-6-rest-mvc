package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Pierrot, 28-05-2024.
 */
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
