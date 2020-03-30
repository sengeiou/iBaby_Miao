package com.atyume.ibabym.basics;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OrderExamInfo {     //体检订单
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private Long examId;          //体检信息
    @NotNull
    private Long inoculId;              //体检宝宝
    @NotNull
    private String takeTime;           //下单时间
    @NotNull
    private String orderTime;             //预约时间
    @NotNull
    private Integer isSucced;             //体检是否完成，0为未完成

    @Keep
    public OrderExamInfo(Long examId, Long inoculId, String takeTime, String orderTime, Integer isSucced) {
        this.examId = examId;
        this.inoculId = inoculId;
        this.takeTime = takeTime;
        this.orderTime = orderTime;
        this.isSucced = isSucced;
    }

    @Generated(hash = 2097369956)@Keep
    public OrderExamInfo(Long id, @NotNull Long examId, @NotNull Long inoculId,
            @NotNull String takeTime, @NotNull String orderTime,
            @NotNull Integer isSucced) {
        this.id = id;
        this.examId = examId;
        this.inoculId = inoculId;
        this.takeTime = takeTime;
        this.orderTime = orderTime;
        this.isSucced = isSucced;
    }

    @Generated(hash = 1818395719)@Keep
    public OrderExamInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getInoculId() {
        return inoculId;
    }

    public void setInoculId(Long inoculId) {
        this.inoculId = inoculId;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getIsSucced() {
        return isSucced;
    }

    public void setIsSucced(Integer isSucced) {
        this.isSucced = isSucced;
    }

    @Override
    public String toString() {
        return "ShowOrderExamInfo{" +
                "id=" + id +
                ", examId=" + examId +
                ", inoculId=" + inoculId +
                ", takeTime='" + takeTime + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", isSucced=" + isSucced +
                '}';
    }
}
