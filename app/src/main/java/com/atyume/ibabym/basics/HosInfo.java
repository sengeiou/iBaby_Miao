package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HosInfo {           //卫生院信息
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String hosName;              //卫生院名称
    @NotNull
    private String hosAdress;            //卫生院地址
    @NotNull
    private Long vaccinId;               //卫生院疫苗
    @NotNull
    private Long vaccinAmount;           //卫生院疫苗数量


    @Generated(hash = 1782750859)
    public HosInfo(Long id, @NotNull String hosName, @NotNull String hosAdress,
            @NotNull Long vaccinId, @NotNull Long vaccinAmount) {
        this.id = id;
        this.hosName = hosName;
        this.hosAdress = hosAdress;
        this.vaccinId = vaccinId;
        this.vaccinAmount = vaccinAmount;
    }

    @Generated(hash = 1450780860)
    public HosInfo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getHosAdress() {
        return hosAdress;
    }

    public void setHosAdress(String hosAdress) {
        this.hosAdress = hosAdress;
    }

    public Long getVaccinId() {
        return vaccinId;
    }

    public void setVaccinId(Long vaccinId) {
        this.vaccinId = vaccinId;
    }

    public Long getVaccinAmount() {
        return vaccinAmount;
    }

    public void setVaccinAmount(Long vaccinAmount) {
        this.vaccinAmount = vaccinAmount;
    }
}
