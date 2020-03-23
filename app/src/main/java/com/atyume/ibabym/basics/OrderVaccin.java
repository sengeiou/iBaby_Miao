package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OrderVaccin {         //疫苗订单信息
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private Date inocluTime;     /*接种时间*/
    @NotNull
    private Date orderVaccinTime;    /*预约时间*/
    @NotNull
    private Long inocluId;       /*接种宝宝*/
    @NotNull
    private Long vaccinId;       /*接种的疫苗*/
    @NotNull
    private Long hosId;      /*接种单位*/
    @NotNull
    private Integer isSucceed;    /*判断是否已接种，未接种为0*/

    @Generated(hash = 1051131363)
    public OrderVaccin(Long id, @NotNull Date inocluTime,
            @NotNull Date orderVaccinTime, @NotNull Long inocluId,
            @NotNull Long vaccinId, @NotNull Long hosId,
            @NotNull Integer isSucceed) {
        this.id = id;
        this.inocluTime = inocluTime;
        this.orderVaccinTime = orderVaccinTime;
        this.inocluId = inocluId;
        this.vaccinId = vaccinId;
        this.hosId = hosId;
        this.isSucceed = isSucceed;
    }

    @Generated(hash = 1311314865)
    public OrderVaccin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInocluTime() {
        return inocluTime;
    }

    public void setInocluTime(Date inocluTime) {
        this.inocluTime = inocluTime;
    }

    public Date getOrderVaccinTime() {
        return orderVaccinTime;
    }

    public void setOrderVaccinTime(Date orderVaccinTime) {
        this.orderVaccinTime = orderVaccinTime;
    }

    public Long getInocluId() {
        return inocluId;
    }

    public void setInocluId(Long inocluId) {
        this.inocluId = inocluId;
    }

    public Long getVaccinId() {
        return vaccinId;
    }

    public void setVaccinId(Long vaccinId) {
        this.vaccinId = vaccinId;
    }

    public Long getHosId() {
        return hosId;
    }

    public void setHosId(Long hosId) {
        this.hosId = hosId;
    }

    public Integer getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(Integer isSucceed) {
        this.isSucceed = isSucceed;
    }
}
