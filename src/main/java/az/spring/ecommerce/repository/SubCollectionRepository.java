package az.spring.ecommerce.repository;

import az.spring.ecommerce.model.SubCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCollectionRepository
    extends JpaRepository<SubCollection, Long> {

    Optional<SubCollection> findByName(String name);

}
