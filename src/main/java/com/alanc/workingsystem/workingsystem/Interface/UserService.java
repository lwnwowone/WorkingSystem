package com.alanc.workingsystem.workingsystem.Interface;

import com.alanc.workingsystem.workingsystem.DataMeta.UserMeta;

public interface UserService {

    UserMeta getUserFromToken(String token);

    String newUser(String username, String password, String realName);

    String login(String username, String password);
}
