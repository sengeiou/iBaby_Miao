package com.atyume.ibabym.Model;

import com.atyume.greendao.gen.ExamProjectDao;
import com.atyume.greendao.gen.ProjectToExamDao;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ProjectToExam;

import java.util.ArrayList;
import java.util.List;

public class ProjectToExamModel {

    private ProjectToExamDao projectToExamDao = MyApplication.getInstances().getDaoSession().getProjectToExamDao();
    private ExamProjectDao examProjectDao = MyApplication.getInstances().getDaoSession().getExamProjectDao();
//    获取每个体检套餐的体检项目
    public List<ExamProject> getExamProjects(Long examId){
        List<ProjectToExam> projectToExamList = projectToExamDao.queryBuilder().where(ProjectToExamDao.Properties.ExamId.eq(examId)).list();
        List<ExamProject> examProjectList = new ArrayList<ExamProject>();
        for(ProjectToExam projectToExam : projectToExamList){
            ExamProject examProject = examProjectDao.load(projectToExam.getProjectId());
            examProjectList.add(examProject);
        }
        return examProjectList;
    }
//    获取套餐内每个体检项目的名称
    public List<String> getProjectNameLists(Long examId){
        List<ProjectToExam> projectToExamList = new ArrayList<ProjectToExam>();
        projectToExamList = projectToExamDao.queryBuilder().where(ProjectToExamDao.Properties.ExamId.eq(examId)).list();
        List<String> examProjectList = new ArrayList<String>();
        if(projectToExamList!=null || !projectToExamList.isEmpty()){
            for(ProjectToExam projectToExam : projectToExamList){
                ExamProject examProject = examProjectDao.load(projectToExam.getProjectId());
                examProjectList.add(examProject.getProjectName());
            }
        }
        return examProjectList;
    }
    public void insertExamProject(Long examInfoId, List<String> ProjectNameList){
        if(ProjectNameList!=null || !ProjectNameList.isEmpty()){
            for(int i=0; i<ProjectNameList.size();i++){
                ExamProject project = examProjectDao.queryBuilder().where(ExamProjectDao.Properties.ProjectName.eq(ProjectNameList.get(i))).unique();
                projectToExamDao.insert(new ProjectToExam(examInfoId, project.getId()));
            }
        }
    }
//    获取套餐内每个体检项目名称
    public List<String> getSelectProjectNameList(Long ExamId){
        List<ProjectToExam> projectToExamList = projectToExamDao.queryBuilder().where(ProjectToExamDao.Properties.ExamId.eq(ExamId)).list();
        List<String> projectNameList = new ArrayList<String>();
        for (ProjectToExam projectToExam : projectToExamList){
            projectNameList.add(getProjectName(projectToExam.getProjectId()));
        }
        return projectNameList;
    }
//    获取体检项目名称
    private String getProjectName(Long projectId){
        return examProjectDao.load(projectId).getProjectName();
    }
//    删除选中的体检项目
    public void deleteOldSelectProjectById(Long examId,List<String> projectNameList){
        if(projectNameList!=null){
            for(int i=0;i<projectNameList.size();i++){
                //找到对应套餐的体检单项
                ExamProject examProject = examProjectDao.queryBuilder().where(ExamProjectDao.Properties.ProjectName.eq(projectNameList.get(i))).unique();
                //找到套餐-单项对应项
                ProjectToExam projectToExam = projectToExamDao.queryBuilder().where(ProjectToExamDao.Properties.ExamId.eq(examId),ProjectToExamDao.Properties.ProjectId.eq(examProject.getId())).unique();
                //删除
                projectToExamDao.delete(projectToExam);
            }
        }

    }
}
