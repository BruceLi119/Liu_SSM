package cn.xintian.controller;

import cn.xintian.domain.Account;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api
@Controller
@RequestMapping("jsonController")
public class JsonController {

    /**
     * 测试响应 json 数据
     */
    @RequestMapping("/testResponseJson")
    public @ResponseBody Account testResponseJson(@RequestBody Account account) {
        Integer id = account.getId();
        System.out.println(id);
        String name = account.getName();
        System.out.println(name);
        System.out.println("异步请求： "+account);
        return account;
    }
}
