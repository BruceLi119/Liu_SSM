package cn.xintian.domain;

/**
 * 存储access_token
 */
public class AccessToken {
    //自增id
    private int id;
    //获取到的accessToken
    private String accessToken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
