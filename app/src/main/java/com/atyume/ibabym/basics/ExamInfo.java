package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ExamInfo {        //体检套餐信息
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String examName;             //套餐名称
    @NotNull
    private Double examPrice;             //套餐价格
    private String examHosName;

    @Keep
    public ExamInfo(String examName, Double examPrice, String examHosName) {
        this.examName = examName;
        this.examPrice = examPrice;
        this.examHosName = examHosName;
    }

    @Keep
    @Generated(hash = 1127825698)
    public ExamInfo(Long id, @NotNull String examName, @NotNull Double examPrice,
            String examHosName) {
        this.id = id;
        this.examName = examName;
        this.examPrice = examPrice;
        this.examHosName = examHosName;
    }

    @Generated(hash = 467552702)@Keep
    public ExamInfo() {
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

    public String getExamHosName() {
        return examHosName;
    }

    public void setExamHosName(String examHosName) {
        this.examHosName = examHosName;
    }

    @Override
    public String toString() {
        return "ExamInfo{" +
                "id=" + id +
                ", examName='" + examName + '\'' +
                ", examPrice=" + examPrice +
                ", examHosName='" + examHosName + '\'' +
                '}';
    }
}
