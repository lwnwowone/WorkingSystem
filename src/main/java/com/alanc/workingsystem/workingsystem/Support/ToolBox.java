package com.alanc.workingsystem.workingsystem.Support;

import java.util.Map;
import java.util.UUID;

public class ToolBox {

    public static String generateUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    public static boolean checkMapKeys(Map map, String[] keys){
        for (String key : keys){
            if(map.containsKey(key)){
                if(map.get(key).toString().isEmpty()){
                    return false;
                }
            }
            else
                return false;
        }
        return true;
    }
}
