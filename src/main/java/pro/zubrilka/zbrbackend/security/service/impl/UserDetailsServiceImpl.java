package pro.zubrilka.zbrbackend.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pro.zubrilka.zbrbackend.security.repo.AccountRepo;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Set<String> ROLES = Set.of("USER");

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepo.findByEmail(username);
        if (account != null) {
            var userBuilder = User
                    .withUsername(account.getEmail())
                    .password(account.getPassword());
            ROLES.forEach(userBuilder::roles);

            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }
}
