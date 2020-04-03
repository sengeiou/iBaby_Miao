package com.atyume.ibabym.Model;

import com.atyume.greendao.gen.HosInfoDao;
import com.atyume.ibabym.basics.HosInfo;
import com.atyume.ibabym.basics.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class HosInfoModel {
    private HosInfoDao hosInfoDao = MyApplication.getInstances().getDaoSession().getHosInfoDao();
//    根据接种单位获得Id
    public Long getHosIdByArea(String certiArea){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoDao.queryBuilder().where(HosInfoDao.Properties.HosName.eq(certiArea)).unique();
        return hosInfo.getId();
    }
//    根据Id获取名称
    public String getHosName(Long hosId){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoDao.load(hosId);
        return hosInfo.getHosName();
    }
//    获取医院信息
    public HosInfo getHosInfo(long hosId){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoDao.load(hosId);
        return hosInfo;
    }
//    新增医院信息
    public void insertHosInfo(String hosName,String hosAdress,Long hosMiaoId,Long hosMiaoAmount) {
        HosInfo hosInfo = new HosInfo(hosName,hosAdress,hosMiaoId,hosMiaoAmount);
       hosInfoDao.insert(hosInfo);

    }
//    修改医院信息
    public void updateHosInfo(String hosName,String hosAdress,Long hosMiaoId,Long hosMiaoAmount){
        HosInfo hosInfo = hosInfoDao.queryBuilder().where(HosInfoDao.Properties.HosName.eq(hosName),
                HosInfoDao.Properties.HosAdress.eq(hosAdress),HosInfoDao.Properties.VaccinId.eq(hosMiaoId)).unique();
        Long newAmount = hosInfo.getVaccinAmount()+hosMiaoAmount;
        hosInfo.setVaccinAmount(newAmount);
        hosInfoDao.update(hosInfo);
    }
    public void updateHosById(Long hosId,String hosName,String hosAdress,Long hosMiaoId,Long hosMiaoAmount){
        HosInfo hosInfo = hosInfoDao.load(hosId);
        hosInfo.setHosName(hosName);
        hosInfo.setHosAdress(hosAdress);
        hosInfo.setVaccinId(hosMiaoId);
        hosInfo.setVaccinAmount(hosMiaoAmount);
        hosInfoDao.update(hosInfo);
    }
//    判断该医院是否存在
    public boolean judgeHosIsExist(String hosName,String hosAdress,Long hosMiaoId){
        List<HosInfo> hosInfoList = hosInfoDao.queryBuilder().where(HosInfoDao.Properties.HosName.eq(hosName),
                HosInfoDao.Properties.HosAdress.eq(hosAdress),HosInfoDao.Properties.VaccinId.eq(hosMiaoId)).list();
        if (hosInfoList==null || hosInfoList.size()==0){
            return false;    //不存在
        }
        return true;
    }
//    获取包含对应疫苗Id的医院信息
    public List<HosInfo> getHosInfoList(Long vaccinId){
        List<HosInfo> hosInfoList = new ArrayList<HosInfo>();
        hosInfoList = hosInfoDao.queryBuilder().where(HosInfoDao.Properties.VaccinId.eq(vaccinId)).list();
        return hosInfoList;
    }

    public List<HosInfo> getAllHos(){
        List<HosInfo> hosInfoList = new ArrayList<HosInfo>();
        hosInfoList = hosInfoDao.loadAll();
        return hosInfoList;
    }
//    删除医院信息
    public void deleteHosById(Long hosId){
        hosInfoDao.deleteByKey(hosId);
    }
//    判断是否还有疫苗库存
    public boolean judgeVaccinAmount(Long hosId){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoDao.load(hosId);
        Long vaccinAmount = hosInfo.getVaccinAmount();
        return vaccinAmount > 0;
    }
//    扣除对应门诊的疫苗数量
    public void deleteVaccinAmount(Long hosId){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoDao.load(hosId);
        Long vaccinAmount = hosInfo.getVaccinAmount();
//        修改数量-1
        hosInfo.setVaccinAmount(vaccinAmount-1);
        hosInfoDao.update(hosInfo);
    }
    //    扣除对应门诊的疫苗数量
    public void updateVaccinAmount(Long hosId){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoDao.load(hosId);
        Long vaccinAmount = hosInfo.getVaccinAmount();
//        修改数量-1
        hosInfo.setVaccinAmount(vaccinAmount+1);
        hosInfoDao.update(hosInfo);
    }

}
