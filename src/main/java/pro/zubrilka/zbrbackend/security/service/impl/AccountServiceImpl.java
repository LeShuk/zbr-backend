package pro.zubrilka.zbrbackend.security.service.impl;

import pro.zubrilka.zbrbackend.security.dto.AccountDTO;
import pro.zubrilka.zbrbackend.security.entity.Account;
import pro.zubrilka.zbrbackend.security.service.AccountService;

public class AccountServiceImpl implements AccountService {
    @Override
    public Account findByEmail(String email) {
        return null;
    }

    @Override
    public void saveNewAccount(AccountDTO accountDTO) {

    }
}
