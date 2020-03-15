package com.atyume.ibabym.basics;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class AdminUser {        //管理员信息
    @Id(autoincrement = true)
    private Long id;
    private String adminName;        /*管理员账号*/
    @NotNull
    private String adminPwd;         /*管理员密码*/

    public AdminUser(Long id, String adminName, String adminPwd) {
        this.id = id;
        this.adminName = adminName;
        this.adminPwd = adminPwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }
}
