package az.spring.ecommerce.repository;

import az.spring.ecommerce.model.User;
import az.spring.ecommerce.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);

    List<UserWrapper> getAllUser();

    List<User> findByUsername(String username);
    List<User> findByName(String name);
    List<User> findByStatus(String status);

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Long id);

    Optional<User> findByEmailIgnoreCase(String email);

}