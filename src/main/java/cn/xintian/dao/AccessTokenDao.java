package cn.xintian.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface AccessTokenDao {

    @Select("select accessToken from AccessToken where id = 1")
    public String find();

    @Update("update accesstoken set accessToken = #{accessToken} where id = 1")
    public void update(String accessToken);
}
