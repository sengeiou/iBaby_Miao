package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ParentInfo {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String parentTell;     //电话（账号）
    private String parentName;     //姓名
    private String  parentWorkAdress;    //工作单位
    private String parentNick;       //昵称
    @NotNull
    private String parentPwd;     //账号密码

    @Keep
    public ParentInfo(String parentTell, String parentPwd) {
        this.parentTell = parentTell;
        this.parentPwd = parentPwd;
    }


    @Generated(hash = 781774772)
    public ParentInfo(Long id, @NotNull String parentTell, String parentName,
            String parentWorkAdress, String parentNick, @NotNull String parentPwd) {
        this.id = id;
        this.parentTell = parentTell;
        this.parentName = parentName;
        this.parentWorkAdress = parentWorkAdress;
        this.parentNick = parentNick;
        this.parentPwd = parentPwd;
    }


    @Generated(hash = 462531552)
    public ParentInfo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentTell() {
        return parentTell;
    }

    public void setParentTell(String parentTell) {
        this.parentTell = parentTell;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentWorkAdress() {
        return parentWorkAdress;
    }

    public void setParentWorkAdress(String parentWorkAdress) {
        this.parentWorkAdress = parentWorkAdress;
    }

    public String getParentNick() {
        return parentNick;
    }

    public void setParentNick(String parentNick) {
        this.parentNick = parentNick;
    }

    public String getParentPwd() {
        return parentPwd;
    }

    public void setParentPwd(String parentPwd) {
        this.parentPwd = parentPwd;
    }

    @Override
    public String toString() {
        return "ParentInfo{" +
                "id=" + id +
                ", parentTell='" + parentTell + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentWorkAdress='" + parentWorkAdress + '\'' +
                ", parentNick='" + parentNick + '\'' +
                ", parentPwd='" + parentPwd + '\'' +
                '}';
    }
}
