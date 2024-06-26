package smartyflip.accounting.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartyflip.accounting.model.UserAccount;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {


    @Query("SELECT u FROM UserAccount u WHERE u.email ILIKE :username OR u.username = :username")
    Optional<UserAccount> findByUsernameEquals(@Param("username") String username);

    Optional<UserAccount> findByUsernameIgnoreCase(String username);

    Optional<UserAccount> findByEmail(String email);

    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByEmail(String email);
}