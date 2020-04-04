package com.atyume.ibabym.Model;

import com.atyume.greendao.gen.AdminUserDao;
import com.atyume.ibabym.basics.AdminUser;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.utils.MD5Utils;

import java.util.ArrayList;
import java.util.List;

public class AdminModel {
    private AdminUserDao adminUserDao = MyApplication.getInstances().getDaoSession().getAdminUserDao();

    public Long getUserId(String adminTell){
        AdminUser adminUser = new AdminUser();
        adminUser = adminUserDao.queryBuilder().where(AdminUserDao.Properties.AdminName.eq(adminTell)).unique();
        return adminUser.getId();
    }

    public void insertUser(String adminName, String adminPwd){
        String md5Pwd = MD5Utils.md5(adminPwd);
        AdminUser adminUser = new AdminUser(adminName,md5Pwd);
        adminUserDao.insert(adminUser);
    }

    public boolean judgeHaveUser(String adminName){
        List<AdminUser> adminUserList = new ArrayList<AdminUser>();
        adminUserList = adminUserDao.queryBuilder().where(AdminUserDao.Properties.AdminName.eq(adminName)).list();
        return adminUserList.size()!=0;
    }

    public boolean judgeTellPwd(String adminName, String adminPwd){
        AdminUser adminUser = new AdminUser();
        adminUser = adminUserDao.queryBuilder().where(AdminUserDao.Properties.AdminName.eq(adminName)).unique();
        String md5Pwd = MD5Utils.md5(adminPwd);
        return adminUser.getAdminPwd().equals(md5Pwd);
    }

    public String getAdminTell(Long adminId){
        AdminUser adminUser = new AdminUser();
        adminUser = adminUserDao.load(adminId);
        return adminUser.getAdminName();
    }

    public void judgeUpdatePwd(Long adminId, String newPwd){
        AdminUser adminUser = new AdminUser();
        adminUser = adminUserDao.load(adminId);
        adminUser.setAdminPwd(newPwd);
        adminUserDao.update(adminUser);
    }
}
