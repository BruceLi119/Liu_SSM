package cn.xintian.service.impl;

import cn.xintian.dao.AccessTokenDao;
import cn.xintian.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Override
    public String find() {
        return accessTokenDao.find();
    }

    @Override
    public void update(String accessToken) {
        accessTokenDao.update(accessToken);
    }
}
