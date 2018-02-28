package com.alanc.workingsystem.workingsystem.Implemention;

import com.alanc.workingsystem.workingsystem.DataMeta.DailyWorkMeta;
import com.alanc.workingsystem.workingsystem.DataMeta.WorkRecordMeta;
import com.alanc.workingsystem.workingsystem.Interface.RecordService;
import com.alanc.workingsystem.workingsystem.Support.ToolBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RecordServiceImp implements RecordService {

    @Autowired
    private JdbcTemplate template;

    public RecordServiceImp(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public boolean newDailyRecord(Map recordMap) {
        //必要信息
        DailyWorkMeta meta = new DailyWorkMeta(recordMap);
        //-9999代表Map中key有缺失，无法初始化
        if(-9999 == meta.getItemID()){
            return false;
        }

        return saveDailyWork(meta);
    }

    @Override
    public boolean deleteDailyRecord(long userID, String recordDate){
        try {
            template.update("DELETE FROM DailyRecord Where ReportDate = ? and UserID = ?",new Object[]{recordDate,userID});
            template.update("DELETE FROM WorkRecord Where ReportDate = ? and UserID = ?",new Object[]{recordDate,userID});
            return true;
        }
        catch(Exception ex){
            System.out.println("ex = " + ex.getMessage());
            return false;
        }
    }


    @Override
    public boolean newTaskRecord(Map recordMap){
        WorkRecordMeta meta = new WorkRecordMeta(recordMap);
        return saveWorkRecord(meta);
    }

    @Override
    public boolean deleteTaskRecord(long userID, long recordID) {
        try {
            template.update("DELETE FROM WorkRecord Where ItemID = ? and UserID = ?",new Object[]{recordID,userID});
            return true;
        }
        catch(Exception ex){
            System.out.println("ex = " + ex.getMessage());
            return false;
        }
    }


    private boolean saveDailyWork(DailyWorkMeta meta){
        try {
            List<Map<String,Object>> result = template.queryForList("SELECT * from DailyRecord Where UserID = ? and ReportDate = ?",new Object[]{meta.getUserID(),meta.getReportDate()});
            if(result.size() > 0) {
                return false;
            }

            template.update("INSERT INTO DailyRecord (UserID,ReportDate,CreateAt,InputAt,WorkingPlace,Feelings,PlanList) VALUES (?,?,?,?,?,?,?)",new Object[]{meta.getUserID(),meta.getReportDate(),meta.getCreateAt(),meta.getInputAt(),meta.getWorkingPlace(),meta.getFeelings(),meta.getPlanListJsonString()});

            for (WorkRecordMeta wMeta : meta.getWorkRecodList()){
                boolean saveFlag = saveWorkRecord(wMeta);
                if(!saveFlag){
                    return false;
                }
            }

            return true;
        }
        catch(Exception ex){
            System.out.println("ex = " + ex.getMessage());
            return false;
        }
    }

    private boolean saveWorkRecord(WorkRecordMeta meta){
        try {
            template.update("INSERT INTO WorkRecord (UserID,CreateAt,JIRAID,ReportDate,Description,Solution,Type,Level,Time,Status,NeedCollect) VALUES (?,?,?,?,?,?,?,?,?,?,?)",new Object[]{meta.getUserID(),meta.getCreateAt(),meta.getJiraID(),meta.getReportDate(),meta.getDescription(),meta.getSolution(),meta.getType(),meta.getLevel(),meta.getTime(),meta.getStatus(),meta.getNeedCollect()});
            return true;
        }
        catch(Exception ex){
            System.out.println("ex = " + ex.getMessage());
            return false;
        }
    }
}
