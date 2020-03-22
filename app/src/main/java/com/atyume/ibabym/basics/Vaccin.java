package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Vaccin {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String vaccinName;           //疫苗名称
    private String vaccinEffect;          //疫苗作用
    private String vaccinAttention;        //接种注意事项
    private String vaccinDisadv;           //接种不良反应
    @NotNull
    private String vaccinAge;               //适合年龄
    @NotNull
    private String vaccinProcess;           //接种程序（共几剂）
    @NotNull
    private Double vaccinPrice;             //疫苗价格
    private String produceCompany;          //生产厂家
    @NotNull
    private String vaccinNo;                //生产批号
    @NotNull
    private Long vaccinAmount;              //疫苗数量

    public Vaccin(String vaccinName, String vaccinEffect, String vaccinAttention, String vaccinDisadv, String vaccinAge, String vaccinProcess, Double vaccinPrice, String produceCompany, String vaccinNo, Long vaccinAmount) {
        this.vaccinName = vaccinName;
        this.vaccinEffect = vaccinEffect;
        this.vaccinAttention = vaccinAttention;
        this.vaccinDisadv = vaccinDisadv;
        this.vaccinAge = vaccinAge;
        this.vaccinProcess = vaccinProcess;
        this.vaccinPrice = vaccinPrice;
        this.produceCompany = produceCompany;
        this.vaccinNo = vaccinNo;
        this.vaccinAmount = vaccinAmount;
    }

    @Generated(hash = 864148609)
    public Vaccin(Long id, @NotNull String vaccinName, String vaccinEffect,
            String vaccinAttention, String vaccinDisadv, @NotNull String vaccinAge,
            @NotNull String vaccinProcess, @NotNull Double vaccinPrice,
            String produceCompany, @NotNull String vaccinNo,
            @NotNull Long vaccinAmount) {
        this.id = id;
        this.vaccinName = vaccinName;
        this.vaccinEffect = vaccinEffect;
        this.vaccinAttention = vaccinAttention;
        this.vaccinDisadv = vaccinDisadv;
        this.vaccinAge = vaccinAge;
        this.vaccinProcess = vaccinProcess;
        this.vaccinPrice = vaccinPrice;
        this.produceCompany = produceCompany;
        this.vaccinNo = vaccinNo;
        this.vaccinAmount = vaccinAmount;
    }

    @Generated(hash = 1438921313)
    public Vaccin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVaccinName() {
        return vaccinName;
    }

    public void setVaccinName(String vaccinName) {
        this.vaccinName = vaccinName;
    }

    public String getVaccinEffect() {
        return vaccinEffect;
    }

    public void setVaccinEffect(String vaccinEffect) {
        this.vaccinEffect = vaccinEffect;
    }

    public String getVaccinAttention() {
        return vaccinAttention;
    }

    public void setVaccinAttention(String vaccinAttention) {
        this.vaccinAttention = vaccinAttention;
    }

    public String getVaccinDisadv() {
        return vaccinDisadv;
    }

    public void setVaccinDisadv(String vaccinDisadv) {
        this.vaccinDisadv = vaccinDisadv;
    }

    public String getVaccinAge() {
        return vaccinAge;
    }

    public void setVaccinAge(String vaccinAge) {
        this.vaccinAge = vaccinAge;
    }

    public String getVaccinProcess() {
        return vaccinProcess;
    }

    public void setVaccinProcess(String vaccinProcess) {
        this.vaccinProcess = vaccinProcess;
    }

    public Double getVaccinPrice() {
        return vaccinPrice;
    }

    public void setVaccinPrice(Double vaccinPrice) {
        this.vaccinPrice = vaccinPrice;
    }

    public String getProduceCompany() {
        return produceCompany;
    }

    public void setProduceCompany(String produceCompany) {
        this.produceCompany = produceCompany;
    }

    public String getVaccinNo() {
        return vaccinNo;
    }

    public void setVaccinNo(String vaccinNo) {
        this.vaccinNo = vaccinNo;
    }

    public Long getVaccinAmount() {
        return vaccinAmount;
    }

    public void setVaccinAmount(Long vaccinAmount) {
        this.vaccinAmount = vaccinAmount;
    }

    @Override
    public String toString() {
        return "Vaccin{" +
                "id=" + id +
                ", vaccinName='" + vaccinName + '\'' +
                ", vaccinEffect='" + vaccinEffect + '\'' +
                ", vaccinAttention='" + vaccinAttention + '\'' +
                ", vaccinDisadv='" + vaccinDisadv + '\'' +
                ", vaccinAge='" + vaccinAge + '\'' +
                ", vaccinProcess='" + vaccinProcess + '\'' +
                ", vaccinPrice=" + vaccinPrice +
                ", produceCompany='" + produceCompany + '\'' +
                ", vaccinNo='" + vaccinNo + '\'' +
                ", vaccinAmount=" + vaccinAmount +
                '}';
    }
}
