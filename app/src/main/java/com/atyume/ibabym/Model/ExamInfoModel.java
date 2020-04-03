package com.atyume.ibabym.Model;

import android.widget.Toast;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class ExamInfoModel {
    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();

    public ExamInfo getExamInfo(Long examId){
        ExamInfo examInfo = new ExamInfo();
        examInfo = examInfoDao.load(examId);
        return examInfo;
    }

    public List<ExamInfo> getExamList(){
        List<ExamInfo> examInfoList = new ArrayList<ExamInfo>();
        examInfoList = examInfoDao.loadAll();
        return examInfoList;
    }

    public void insertExamInfo(String ExamName, Double ExamPrice, String ExamHos){
        examInfoDao.insert(new ExamInfo(ExamName,ExamPrice,ExamHos));
    }

    public void updateExamInfo(ExamInfo examInfo, String ExamName, Double ExamPrice, String ExamHos){
        examInfo.setExamName(ExamName);
        examInfo.setExamPrice(ExamPrice);
        examInfo.setExamHosName(ExamHos);
        examInfoDao.update(examInfo);
    }

    public Long getExamInfoId(String ExamName,String ExamHos){
        ExamInfo examInfo = new ExamInfo();
        examInfo = examInfoDao.queryBuilder().where(ExamInfoDao.Properties.ExamName.eq(ExamName),ExamInfoDao.Properties.ExamHosName.eq(ExamHos)).unique();
        return examInfo.getId();
    }

    public void deleteExamInfoById(Long examId){
        examInfoDao.deleteByKey(examId);
    }

    public List<ExamInfo> getExamListByName(String selectText){
        List<ExamInfo> examInfoList = new ArrayList<ExamInfo>();
        examInfoList = examInfoDao.queryBuilder().where(ExamInfoDao.Properties.ExamName.like("%"+selectText+"%")).list();
        return examInfoList;
    }

    public String getExamName(Long examId){
        ExamInfo examInfo = examInfoDao.load(examId);
        return examInfo.getExamName();
    }
}
