package realrstatesgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realrstatesgency.domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
}
