package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class ExamInfo {        //体检套餐信息
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String examName;             //套餐名称
    @NotNull
    private Double examPrice;             //套餐价格

    public ExamInfo(Long id, String examName, Double examPrice) {
        this.id = id;
        this.examName = examName;
        this.examPrice = examPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Double getExamPrice() {
        return examPrice;
    }

    public void setExamPrice(Double examPrice) {
        this.examPrice = examPrice;
    }
}
