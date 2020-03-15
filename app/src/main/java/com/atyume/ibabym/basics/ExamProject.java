package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class ExamProject {           //体检单项信息
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String projectName;            //项目名称
    @NotNull
    private String projectDetail;          //详细内容
    @NotNull
    private Double projectPrice;           //项目价格

    public ExamProject(Long id, String projectName, String projectDetail, Double projectPrice) {
        this.id = id;
        this.projectName = projectName;
        this.projectDetail = projectDetail;
        this.projectPrice = projectPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
    }

    public Double getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(Double projectPrice) {
        this.projectPrice = projectPrice;
    }
}
