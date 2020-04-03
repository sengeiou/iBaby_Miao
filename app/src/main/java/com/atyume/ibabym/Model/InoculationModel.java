package com.atyume.ibabym.Model;

import com.atyume.greendao.gen.InoculationDao;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class InoculationModel {
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();
//    根据家长Id获取孩子信息
    public Inoculation selectBabyByParent(Long userId){
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.load(userId);
        return inoculation;
    }
//    根据孩子Id获取孩子信息
    public Inoculation selectBabyByBabyId(Long babyId){
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.load(babyId);
        return inoculation;
    }
//    根据家长Id获取孩子名字
    public String getBabyName(Long userId){
        Inoculation inoculation = new Inoculation();
        inoculation = selectBabyByParent(userId);
        return inoculation.getInoculBaby();
    }
//    根据孩子Id获取孩子名字
    public String getBabyNameByBaby(Long babyId){
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.load(babyId);
        return inoculation.getInoculBaby();
    }
//    根据宝宝名字获取宝宝Id
    public Long getBabyIdByName(String babyName){
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.queryBuilder().where(InoculationDao.Properties.InoculBaby.eq(babyName)).unique();
        return inoculation.getId();
    }
//    新增宝宝
    public void insertBaby(String babyName,String babyDate,String babySex,String babyAdreess,String babyHome,Long userId){
        inoculationDao.insert(new Inoculation(babyName,babyDate,babySex,babyAdreess,babyHome,userId));
    }
//    修改宝宝信息(Id)
    public void updateBabyById(Long babyId,String babyName,String babyDate,String babySex,String babyAdress,String babyHome){
        Inoculation inoculation = inoculationDao.load(babyId);
        inoculation.setInoculBaby(babyName);
        inoculation.setBabyData(babyDate);
        inoculation.setBabySex(babySex);
        inoculation.setBabyHome(babyAdress);
        inoculation.setBabyAdress(babyHome);
        inoculationDao.update(inoculation);
    }
//    修改宝宝信息(Inoculation)
    public void updateBaby(Inoculation inoculation,String babyName,String babyDate,String babySex,String babyAdress,String babyHome){
        inoculation.setInoculBaby(babyName);
        inoculation.setBabyData(babyDate);
        inoculation.setBabySex(babySex);
        inoculation.setBabyHome(babyAdress);
        inoculation.setBabyAdress(babyHome);
        inoculationDao.update(inoculation);
    }
//    获取宝宝信息列表
    public List<Inoculation> getInoculationList(){
        List<Inoculation> inoculationList = new ArrayList<Inoculation>();
        inoculationList = inoculationDao.loadAll();
        return inoculationList;
    }
    public void deleteBabyById(Long babyId){
        inoculationDao.deleteByKey(babyId);
    }
//    判断是否有该宝宝
    public boolean judgeBabyIsExit(Long userId){
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.load(userId);
        if(inoculation == null || inoculation.equals("")){
            return false;
        }
        return true;
    }
}
