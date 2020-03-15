package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class ParentInfo {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private Long parentTell;     //电话（账号）
    private String parentName;     //姓名
    private String  parentWorkAdress;    //工作单位
    private String parentNick;       //昵称
    @NotNull
    private String parentPwd;     //账号密码

    public ParentInfo(Long id, Long parentTell, String parentName, String parentWorkAdress, String parentNick, String parentPwd) {
        this.id = id;
        this.parentTell = parentTell;
        this.parentName = parentName;
        this.parentWorkAdress = parentWorkAdress;
        this.parentNick = parentNick;
        this.parentPwd = parentPwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentTell() {
        return parentTell;
    }

    public void setParentTell(Long parentTell) {
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
}
