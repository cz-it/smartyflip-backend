package smartyflip.accounting.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartyflip.accounting.model.EmailActivationKey;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailActivationRepository extends JpaRepository<EmailActivationKey, Integer> {
    Optional<EmailActivationKey> findById(UUID uuid);
}
