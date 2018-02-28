package com.alanc.workingsystem.workingsystem.DataMeta;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DailyWorkMeta {
    //ID
    private long itemID;

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    //用户ID
    private long userID;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    //创建时间
    private long createAt;

    public long getCreateAt() {
        return createAt;
    }

    //汇报日期
    private String reportDate;

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    //填写时间
    private long inputAt;

    public long getInputAt() {
        return inputAt;
    }

    public void setInputAt(long inputAt) {
        this.inputAt = inputAt;
    }

    //工作地点
    private String workingPlace;

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    //感想收获
    private String feelings;

    public String getFeelings() {
        return feelings;
    }

    public void setFeelings(String feelings) {
        this.feelings = feelings;
    }

    //工作日志列表
    private List<WorkRecordMeta> workRecodList;

    public List<WorkRecordMeta> getWorkRecodList() {
        return workRecodList;
    }

    public void setWorkRecodList(List<WorkRecordMeta> workRecodList) {
        this.workRecodList = workRecodList;
    }

    //明日计划列表
    private List<String> planList;

    public List<String> getPlanList() {
        return planList;
    }

    public void setPlanList(List<String> planList) {
        this.planList = planList;
    }

    public String getPlanListJsonString() {
        return new Gson().toJson(this.planList);
    }

    public DailyWorkMeta() {
        this.itemID = -1;
        this.createAt = System.currentTimeMillis();
        this.inputAt = System.currentTimeMillis();
    }

    public DailyWorkMeta(Map recordMap){
        //必要信息
        this.itemID = -1;
        this.createAt = System.currentTimeMillis();
        this.inputAt = System.currentTimeMillis();

        this.setUserID(Long.parseLong(recordMap.get("userID").toString()));
        this.setReportDate(recordMap.get("reportDate").toString());
        this.setWorkingPlace(recordMap.get("workingPlace").toString());
        this.setFeelings(recordMap.get("feelings").toString());
        this.setPlanList((List<String>)recordMap.get("planList"));
        this.setWorkRecodList(generateTaskRecordList((List<Map>)recordMap.get("workRecodList")));

        //可选信息
        if(recordMap.containsKey("inputAt")){
            this.setInputAt((long)recordMap.get("inputAt"));
        }
    }

    private List<WorkRecordMeta> generateTaskRecordList(List<Map> recordMapList) {
        List<WorkRecordMeta> recordList = new ArrayList<WorkRecordMeta>();
        for (Map objectMap : recordMapList) {
            objectMap.put("userID",userID);
            objectMap.put("reportDate",reportDate);
            WorkRecordMeta meta = new WorkRecordMeta(objectMap);
            recordList.add(meta);
        }
        return recordList;
    }
}
