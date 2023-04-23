package uz.pdp.rest_api_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.rest_api_jwt.entity.Outcome;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {
}
