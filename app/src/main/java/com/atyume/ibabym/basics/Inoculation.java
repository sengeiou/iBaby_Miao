package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

@Entity
public class Inoculation {       //接种档案信息
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String inoculBaby;      /*宝宝名字*/
    private Date babyData;     /*出生日期*/
    private String babySex;       /*宝宝性别*/
    private String babyAdress;    /*户口地址*/
    private String babyhome;         /*常住地址*/
    @NotNull
    private Long parentId;          /*宝宝父母*/

    public Inoculation(Long id, String inoculBaby, Date babyData, String babySex, String babyAdress, String babyhome, Long parentId) {
        this.id = id;
        this.inoculBaby = inoculBaby;
        this.babyData = babyData;
        this.babySex = babySex;
        this.babyAdress = babyAdress;
        this.babyhome = babyhome;
        this.parentId = parentId;
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

    public String getBabyhome() {
        return babyhome;
    }

    public void setBabyhome(String babyhome) {
        this.babyhome = babyhome;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
