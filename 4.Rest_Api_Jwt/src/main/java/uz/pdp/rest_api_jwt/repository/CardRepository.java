package uz.pdp.rest_api_jwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.rest_api_jwt.entity.Card;


//@RepositoryRestResource(path = "card",excerptProjection = CardProjection.class)
public interface CardRepository extends JpaRepository<Card,Integer> {

     boolean existsByUsername(String username);

     boolean existsByUsernameAndIdNot(String username, Integer id);
}
