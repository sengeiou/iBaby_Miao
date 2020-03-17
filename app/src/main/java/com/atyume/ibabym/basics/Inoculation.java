package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Inoculation {       //接种档案信息
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String inoculBaby;      /*宝宝名字*/
    private Date babyData;     /*出生日期*/
    private String babySex;       /*宝宝性别*/
    private String babyAdress;    /*户口地址*/
    private String babyHome;         /*常住地址*/
    @NotNull
    private Long parentId;          /*宝宝父母*/


    public Inoculation(Long id,String inoculBaby, String babySex, String babyAdress, String babyHome, Long parentId) {
        this.id = id;
        this.inoculBaby = inoculBaby;
        this.babySex = babySex;
        this.babyAdress = babyAdress;
        this.babyHome = babyHome;
        this.parentId = parentId;
    }

    @Generated(hash = 771586055)
    public Inoculation(Long id, @NotNull String inoculBaby, Date babyData,
            String babySex, String babyAdress, String babyHome,
            @NotNull Long parentId) {
        this.id = id;
        this.inoculBaby = inoculBaby;
        this.babyData = babyData;
        this.babySex = babySex;
        this.babyAdress = babyAdress;
        this.babyHome = babyHome;
        this.parentId = parentId;
    }

    @Generated(hash = 1426994754)
    public Inoculation() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInoculBaby() {
        return inoculBaby;
    }

    public void setInoculBaby(String inoculBaby) {
        this.inoculBaby = inoculBaby;
    }

    public Date getBabyData() {
        return babyData;
    }

    public void setBabyData(Date babyData) {
        this.babyData = babyData;
    }

    public String getBabySex() {
        return babySex;
    }

    public void setBabySex(String babySex) {
        this.babySex = babySex;
    }

    public String getBabyAdress() {
        return babyAdress;
    }

    public void setBabyAdress(String babyAdress) {
        this.babyAdress = babyAdress;
    }

    public String getBabyHome() {
        return babyHome;
    }

    public void setBabyHome(String babyHome) {
        this.babyHome = babyHome;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
