package cn.xintian.controller;

import cn.xintian.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/accessToken")
public class AccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @RequestMapping("/find")
    public String find(){
        String s = accessTokenService.find();
        System.out.println(s);
        return "list";
    }


    @RequestMapping("/update")
    public void update(){
        String s = "111";
        accessTokenService.update(s);
        return;
    }

}
