package cn.xintian.service.impl;

import cn.xintian.dao.AccountDao;
import cn.xintian.domain.Account;
import cn.xintian.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        System.out.println("业务层：查询所有账户...");
        return accountDao.findAll();
    }

    @Override
    public void save(Account account) {
        System.out.println("业务层：保存所有账户...");
        accountDao.save(account);
    }
}
