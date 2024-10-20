package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.BeerAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Pierrot on 2024-10-20.
 */
public interface BeerAuditRepository extends JpaRepository<BeerAudit, UUID>{
}
