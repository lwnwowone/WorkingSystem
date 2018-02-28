package com.alanc.workingsystem.workingsystem.DataMeta;

import com.alanc.workingsystem.workingsystem.Support.ToolBox;

import java.util.List;
import java.util.Map;

public class WorkRecordMeta {

    //ID
    private long itemID;

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    //创建时间
    private long createAt;

    public long getCreateAt() {
        return createAt;
    }

    //用户ID
    private long userID;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    //汇报日期
    private String reportDate;

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    //JIRA ID
    private String jiraID;

    public String getJiraID() {
        return jiraID;
    }

    public void setJiraID(String jiraID) {
        this.jiraID = jiraID;
    }

    //问题类型
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //难度等级
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    //耗时
    private float time;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    //任务状态
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //任务状态
    private String solution;

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    //任务状态
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //需要汇总
    private int needCollect;

    public int getNeedCollect() {
        return needCollect;
    }

    public void setNeedCollect(int needCollect) {
        this.needCollect = needCollect;
    }

    public WorkRecordMeta() {
        this.itemID = -1;
        this.createAt = System.currentTimeMillis();
        this.jiraID = "无";
    }

    public WorkRecordMeta(Map recordMap){
        boolean checkFlag = ToolBox.checkMapKeys(recordMap,new String[]{"userID","reportDate","type","level","time","status","needCollect"});
        if(!checkFlag){
            this.itemID = -9999;
        }

        this.itemID = -1;
        this.createAt = System.currentTimeMillis();
        this.jiraID = "无";

        //必要信息
        this.setUserID(Long.parseLong(recordMap.get("userID").toString()));
        this.setReportDate(recordMap.get("reportDate").toString());
        this.setType(Integer.parseInt(recordMap.get("type").toString()));
        this.setLevel(Integer.parseInt(recordMap.get("level").toString()));
        this.setTime(Float.parseFloat(recordMap.get("time").toString()));
        this.setStatus(Integer.parseInt(recordMap.get("status").toString()));
        this.setNeedCollect(Integer.parseInt(recordMap.get("needCollect").toString()));

        //可选信息
        if(recordMap.containsKey("jiraID")){
            this.setJiraID(recordMap.get("jiraID").toString());
        }

        if(recordMap.containsKey("description")) {
            this.setDescription(recordMap.get("description").toString());
        }
        else{
            this.setDescription("");
        }

        if(recordMap.containsKey("solution")) {
            this.setSolution(recordMap.get("solution").toString());
        }
        else{
            this.setSolution("");
        }
    }

}
