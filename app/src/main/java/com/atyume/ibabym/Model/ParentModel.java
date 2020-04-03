package com.atyume.ibabym.Model;

import android.text.TextUtils;
import android.util.Log;

import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ParentInfo;
import com.atyume.ibabym.utils.MD5Utils;

import java.util.ArrayList;
import java.util.List;

public class ParentModel {

    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();

//    判断数据库中是否存在此用户
    public boolean judgeHaveUser(String userTell){
        /*if(parentDao.load())*/
        List<ParentInfo> parentList = new ArrayList<ParentInfo>();
        parentList = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).list();
        return parentList.size() != 0;                 //没有注册过
//注册过
    }
//    判断对应用户名的用户的密码是否一致
    public boolean judgeTellPwd(String userTell, String userPwd){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).unique();
        Log.d("parentInfo:",parentInfo.getId()+" "+parentInfo.getParentPwd());
        //加密
        String md5Pwd = MD5Utils.md5(userPwd);
        return parentInfo.getParentPwd().equals(md5Pwd);
    }
//    找到对应用户名用户的Id
    public Long getUserId(String userTell){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).unique();
        return parentInfo.getId();
    }
//    新增家长
    public void insertUser(String userTell,String userPwd){
        //密码加密
        String md5Pwd = MD5Utils.md5(userPwd);
        parentDao.insert(new ParentInfo(userTell,md5Pwd));
    }
//    修改对应用户的密码(电话，新密码)
    public ParentInfo judgeUpdatePwd(String userTell, String userNewPwd){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).unique();

        String md5Pwd = MD5Utils.md5(userNewPwd);
        parentInfo.setParentPwd(md5Pwd);

        parentDao.update(parentInfo);
        return parentInfo;
    }
    //判断旧密码是否正确
    public boolean judgePwd(Long userId, String userPwd){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.load(userId);
        String md5Pwd = MD5Utils.md5(userPwd);
        return md5Pwd.equals(parentInfo.getParentPwd());
    }
//    修改密码(Id,新密码)
    public void judgeUpdatePwd(Long userId, String userNewPwd){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.load(userId);
        String md5Pwd = MD5Utils.md5(userNewPwd);
        parentInfo.setParentPwd(md5Pwd);
        parentDao.update(parentInfo);
    }
//    修改用户信息
    public void modifyUser(Long userId,String userNick,String userName, String userWork){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.load(userId);
        if(userNick == null || userNick.equals("")){
            userNick = parentInfo.getParentTell();
        }
        parentInfo.setParentName(userName);
        parentInfo.setParentNick(userNick);
        parentInfo.setParentWorkAdress(userWork);
        parentDao.update(parentInfo);
    }
//    查找用户
    public ParentInfo selectById(Long userId){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.load(userId);
        return parentInfo;
    }
    public String getUserNick(Long userId){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentDao.load(userId);
        if (parentInfo.getParentNick()==null || parentInfo.getParentNick().equals("")){
            return parentInfo.getParentTell();
        }
        return parentInfo.getParentNick();
    }
    public void updateParent(ParentInfo parentInfo,String parentName,String parentTell,String parentWork){
        parentInfo.setParentName(parentName);
        parentInfo.setParentTell(parentTell);
        parentInfo.setParentWorkAdress(parentWork);
        parentDao.update(parentInfo);
    }
}
