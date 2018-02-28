package com.alanc.workingsystem.workingsystem;

import com.alanc.workingsystem.workingsystem.DataMeta.UserMeta;
import com.alanc.workingsystem.workingsystem.Interface.UserService;
import com.alanc.workingsystem.workingsystem.Support.ToolBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "userInfo")
    public Map UserInfo(@RequestHeader String token, HttpServletResponse httpServletResponse){
        if(token.isEmpty()){
            httpServletResponse.setStatus(400);
            return null;
        }
        else{
            UserMeta meta = userService.getUserFromToken(token);
            if(null != meta){
                Map resultMap = new HashMap<String,String>();
                resultMap.put("UserID",meta.getUserID());
                resultMap.put("UserName",meta.getUsername());
                resultMap.put("RealName",meta.getRealName());
                return resultMap;
            }
            else{
                httpServletResponse.setStatus(400);
                return null;
            }
        }
    }

    @PostMapping(value = "register", consumes = "application/json;charset=UTF-8")
    public String Rdegister(@RequestBody Map jsonMap, HttpServletResponse httpServletResponse){
        boolean checkResult = ToolBox.checkMapKeys(jsonMap,new String[]{"username","password","realName"});
        if(!checkResult){
            httpServletResponse.setStatus(400);
            return "用户名、密码、真实姓名都不可为空，请检查后重试";
        }

        String token = userService.newUser(jsonMap.get("username").toString(),jsonMap.get("password").toString(),jsonMap.get("realName").toString());
        if(null == token || token.isEmpty()){
            httpServletResponse.setStatus(400);
            return "注册失败，请重试";
        }
        else{
            return token;
        }
    }

    @PostMapping(value = "login", consumes = "application/json;charset=UTF-8")
    public String Login(@RequestBody Map jsonMap, HttpServletResponse httpServletResponse){
        boolean checkResult = ToolBox.checkMapKeys(jsonMap,new String[]{"username","password"});
        if(!checkResult){
            httpServletResponse.setStatus(400);
            return "用户名或密码为空，请检查后重试";
        }

        String token = userService.login(jsonMap.get("username").toString(),jsonMap.get("password").toString());
        if(null == token || token.isEmpty()){
            httpServletResponse.setStatus(400);
            return "登录失败，请重试";
        }
        else{
            return token;
        }
    }

}