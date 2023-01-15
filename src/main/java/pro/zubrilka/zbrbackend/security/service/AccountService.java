package pro.zubrilka.zbrbackend.security.service;

import pro.zubrilka.zbrbackend.security.dto.AccountDTO;
import pro.zubrilka.zbrbackend.security.entity.Account;

public interface AccountService {
    Account findByEmail(String email);
    void saveNewAccount(AccountDTO accountDTO);

    default Account fromDTO(AccountDTO accountDTO) {
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());

        return account;
    }
}
