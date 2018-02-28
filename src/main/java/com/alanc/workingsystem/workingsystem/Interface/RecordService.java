package com.alanc.workingsystem.workingsystem.Interface;

import java.util.Map;

public interface RecordService {

    //ID，创建时间(long)，汇报日期,填写时间(long),工作地点,感想收获,工作日志列表(List<WorkRecordMeta>),明日计划列表(List<String> planList)
    boolean newDailyRecord(Map recordMap);
    boolean deleteDailyRecord(long userID, String recordDate);

    boolean newTaskRecord(Map recordMap);
    boolean deleteTaskRecord(long userID, long recordID);
}
