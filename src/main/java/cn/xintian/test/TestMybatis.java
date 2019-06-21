package cn.xintian.test;

import cn.xintian.dao.AccountDao;
import cn.xintian.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;


public class TestMybatis {
    /**
     * 测试保存
     * @throws Exception
     */
    @Test
    public void run1() throws Exception {
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = factory.openSession();
        AccountDao dao = session.getMapper(AccountDao.class);
        List<Account> lists = dao.findAll();
        for(Account list:lists){
            System.out.println(list);
        }
        session.close();
        in.close();
    }
    /**
        测试保存
     */
    @Test
    public void run2() throws Exception {
        Account account =new Account();
        account.setName("小柳");
        account.setMoney(2000d);
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = factory.openSession();
        AccountDao dao = session.getMapper(AccountDao.class);
        dao.save(account);
        session.commit();
        session.close();
        in.close();
    }
}