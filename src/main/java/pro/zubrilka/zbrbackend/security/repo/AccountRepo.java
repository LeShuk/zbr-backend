package pro.zubrilka.zbrbackend.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.zubrilka.zbrbackend.security.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
