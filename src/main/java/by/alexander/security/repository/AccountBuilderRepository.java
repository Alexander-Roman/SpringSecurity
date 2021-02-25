package by.alexander.security.repository;

import by.alexander.security.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBuilderRepository extends JpaRepository<Account.Builder, Long> {

    Optional<Account.Builder> findByUsername(String username);
}
