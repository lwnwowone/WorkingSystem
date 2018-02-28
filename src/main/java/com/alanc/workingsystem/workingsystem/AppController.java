package com.alanc.workingsystem.workingsystem;

import com.alanc.workingsystem.workingsystem.DataMeta.UserMeta;
import com.alanc.workingsystem.workingsystem.Interface.RecordService;
import com.alanc.workingsystem.workingsystem.Interface.UserService;
import com.alanc.workingsystem.workingsystem.Support.ToolBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    public AppController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
    }

    //ID，创建时间(long)，汇报日期,填写时间(long),工作地点,感想收获,工作日志列表(List<WorkRecordMeta>),明日计划列表(List<String> planList)
    @PostMapping("dailyWork")
    public String NewDailyWork(@RequestHeader String token, @RequestBody Map jsonMap, HttpServletResponse httpServletResponse) {
        UserMeta meta = userService.getUserFromToken(token);
        if(null == meta){
            httpServletResponse.setStatus(400);
            return "必须使用一个有效的Token";
        }

        boolean isValid = ToolBox.checkMapKeys(jsonMap,new String[]{"reportDate","workingPlace","feelings","workRecodList","planList"});
        if(!isValid){
            httpServletResponse.setStatus(400);
            return "缺少必要字段，请重试";
        }
        jsonMap.put("userID",meta.getUserID());

        if(recordService.newDailyRecord(jsonMap)){
            return "添加成功";
        }
        else{
            httpServletResponse.setStatus(400);
            return "添加失败，请重试";
        }
    }

    @DeleteMapping("dailyWork")
    public String DeleteDailyWork(@RequestHeader String token, @RequestBody Map requestMap, HttpServletResponse httpServletResponse) {
        UserMeta meta = userService.getUserFromToken(token);
        if(null == meta){
            httpServletResponse.setStatus(400);
            return "必须使用一个有效的Token";
        }

        if(recordService.deleteDailyRecord(meta.getUserID(),requestMap.get("recordDate").toString())){
            return "删除成功";
        }
        else{
            httpServletResponse.setStatus(400);
            return "删除，请重试";
        }
    }

    @PostMapping("task")
    public String NewTask(@RequestHeader String token, @RequestBody Map jsonMap, HttpServletResponse httpServletResponse) {
        UserMeta meta = userService.getUserFromToken(token);
        if(null == meta){
            httpServletResponse.setStatus(400);
            return "必须使用一个有效的Token";
        }

        boolean isValid = ToolBox.checkMapKeys(jsonMap,new String[]{"reportDate","jiraID","type","level","time","status","needCollect"});
        if(!isValid){
            httpServletResponse.setStatus(400);
            return "缺少必要字段，请重试";
        }
        jsonMap.put("userID",meta.getUserID());

        if(recordService.newTaskRecord(jsonMap)){
            return "添加成功";
        }
        else{
            httpServletResponse.setStatus(400);
            return "添加失败，请重试";
        }
    }

    @DeleteMapping("task")
    public String DeleteTask(@RequestHeader String token, @RequestBody Map requestMap, HttpServletResponse httpServletResponse) {
        UserMeta meta = userService.getUserFromToken(token);
        if(null == meta){
            httpServletResponse.setStatus(400);
            return "必须使用一个有效的Token";
        }

        if(recordService.deleteTaskRecord(meta.getUserID(),Long.parseLong(requestMap.get("recordID").toString()))){
            return "删除成功";
        }
        else{
            httpServletResponse.setStatus(400);
            return "删除，请重试";
        }
    }
}
