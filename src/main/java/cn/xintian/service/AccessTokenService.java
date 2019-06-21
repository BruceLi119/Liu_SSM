package cn.xintian.service;

public interface AccessTokenService {

    /**
     * 查询access_token
     * @return
     */
    public String find();

    /**
     * 修改access_token
     * @param accessToken
     */
    public void update(String accessToken);
}
