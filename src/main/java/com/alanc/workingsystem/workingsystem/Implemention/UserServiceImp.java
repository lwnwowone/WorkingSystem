package com.alanc.workingsystem.workingsystem.Implemention;

import com.alanc.workingsystem.workingsystem.DataMeta.UserMeta;
import com.alanc.workingsystem.workingsystem.Interface.UserService;
import com.alanc.workingsystem.workingsystem.Support.ToolBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserServiceImp implements UserService{

    @Autowired
    private JdbcTemplate template;

    public UserServiceImp(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public UserMeta getUserFromToken(String token) {
        if(null == token || token.isEmpty()){
            return null;
        }
        try {
            List<Map<String,Object>> result = template.queryForList("SELECT * from User Where AvailableToken = ?",new Object[]{token});
            if(result.size() > 0) {
                Map userInfo = result.get(0);
                UserMeta meta = new UserMeta();
                meta.setUserID(Integer.parseInt(userInfo.get("UserID").toString()));
                meta.setUsername(userInfo.get("UserName").toString());
                meta.setRealName(userInfo.get("RealName").toString());
                meta.setAvailableToken(token);
                return meta;
            }
            return null;
        }
        catch(Exception ex){
            System.out.println("ex = " + ex.getMessage());
            return null;
        }
    }

    @Override
    public String newUser(String username, String password, String realName) {
        try {
            List<Map<String,Object>> result = template.queryForList("SELECT * from User Where Username = ?",new Object[]{username});
            if(result.size() > 0) {
                return "";
            }

            String nToken = ToolBox.generateUUID();
            template.update("INSERT INTO User (Username,Password,RealName,AvailableToken) VALUES (?,?,?,?)",new Object[]{username,password,realName,nToken});
            return nToken;
        }
        catch(Exception ex){
            System.out.println("ex = " + ex.getMessage());
            return "";
        }
    }

    @Override
    public String login(String username, String password) {
        try {
            List<Map<String,Object>> result = template.queryForList("SELECT * from User Where Username = ?",new Object[]{username});
            if(result.size() > 0) {
                Map userInfo = result.get(0);
                if(userInfo.get("UserName").toString().equals(username) && userInfo.get("Password").toString().equals(password)){
                    String nToken = ToolBox.generateUUID();
                    template.update("UPDATE User SET AvailableToken = ? WHERE Username = ?",new Object[]{nToken,username});
                    return nToken;
                }
                return "";
            }

            return "";
        }
        catch(Exception ex){
            System.out.println("ex = " + ex.getMessage());
            return "";
        }
    }

}
