package az.spring.ecommerce.repository;

import az.spring.ecommerce.model.HeadCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeadCollectionRepository
    extends JpaRepository<HeadCollection, Long> {

    Optional<HeadCollection> findByName(String name);
}
