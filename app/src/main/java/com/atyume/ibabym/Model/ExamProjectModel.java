package com.atyume.ibabym.Model;

import android.widget.Toast;

import com.atyume.greendao.gen.ExamProjectDao;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ProjectToExam;

import java.util.ArrayList;
import java.util.List;

public class ExamProjectModel {
    private ExamProjectDao examProjectDao = MyApplication.getInstances().getDaoSession().getExamProjectDao();

    public ExamProject getExamProject(Long projectId){
        ExamProject examProject = new ExamProject();
        examProject = examProjectDao.load(projectId);
        return examProject;
    }
//    新增
    public void insertProject(String projectName,String projectDetail,Double projectPrice){
        ExamProject examProject = new ExamProject(projectName,projectDetail,projectPrice);
        examProjectDao.insert(examProject);
    }
//    获取项目名称
    public String getProjectName(Long projectId){
        return examProjectDao.load(projectId).getProjectName();
    }

    public List<ExamProject> getProjectList(){
        List<ExamProject> examProjectList = new ArrayList<ExamProject>();
        examProjectList = examProjectDao.loadAll();
        return examProjectList;
    }
//    修改
    public void updateProject(ExamProject examProject,String projectName,String projectDetail,Double projectPrice){
        examProject.setProjectName(projectName);
        examProject.setProjectDetail(projectDetail);
        examProject.setProjectPrice(projectPrice);
        examProjectDao.update(examProject);
    }
//    删除
    public void deleteProjectById(Long projectId){
        examProjectDao.deleteByKey(projectId);
    }
}
