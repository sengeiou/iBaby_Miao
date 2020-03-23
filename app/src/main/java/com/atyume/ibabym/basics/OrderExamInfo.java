package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OrderExamInfo {     //体检订单
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String examHosName;          //体检医院名称
    @NotNull
    private Long inoculId;              //体检宝宝
    @NotNull
    private Integer isSucced;             //体检是否完成，0为未完成


    @Generated(hash = 335874105)
    public OrderExamInfo(Long id, @NotNull String examHosName,
            @NotNull Long inoculId, @NotNull Integer isSucced) {
        this.id = id;
        this.examHosName = examHosName;
        this.inoculId = inoculId;
        this.isSucced = isSucced;
    }

    @Generated(hash = 1818395719)
    public OrderExamInfo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamHosName() {
        return examHosName;
    }

    public void setExamHosName(String examHosName) {
        this.examHosName = examHosName;
    }

    public Long getInoculId() {
        return inoculId;
    }

    public void setInoculId(Long inoculId) {
        this.inoculId = inoculId;
    }

    public Integer getIsSucced() {
        return isSucced;
    }

    public void setIsSucced(Integer isSucced) {
        this.isSucced = isSucced;
    }
}
