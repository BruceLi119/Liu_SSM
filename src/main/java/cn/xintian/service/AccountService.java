package cn.xintian.service;

import cn.xintian.domain.Account;

import java.util.List;

public interface AccountService {

    public List<Account> findAll();

    public void save(Account account);
}
