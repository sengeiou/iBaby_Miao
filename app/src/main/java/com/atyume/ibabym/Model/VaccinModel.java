package com.atyume.ibabym.Model;

import android.widget.DatePicker;
import android.widget.Toast;

import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.Vaccin;

import java.util.ArrayList;
import java.util.List;

public class VaccinModel {
    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();

    public List<Vaccin> geVaccinList() {
        List<Vaccin> vaccinList = new ArrayList<Vaccin>();
        vaccinList = vaccinDao.loadAll();
        return vaccinList;
    }

    //    获取疫苗信息
    public Vaccin getVaccin(Long miaoId) {
        Vaccin vaccin = new Vaccin();
        vaccin = vaccinDao.load(miaoId);
        return vaccin;
    }

    //    判断该疫苗是否存在
    public boolean judgeMiaoExist(Long MiaoId) {
        List<Vaccin> vaccinList = vaccinDao.queryBuilder().where(VaccinDao.Properties.Id.eq(MiaoId)).list();
        if (vaccinList == null || vaccinList.size() == 0) {
            return false;
        }
        return true;
    }

    //    新增疫苗
    public void insertVaccin(String MiaoName, String MiaoDetail, String MiaoAttention, String MiaoEffect, String MiaoProperAge, String MiaoCertiProcess, Double MiaoPrice,String MiaoFactory,String MiaoNo,Long MiaoAmount) {
        Vaccin vaccin = new Vaccin(MiaoName, MiaoDetail, MiaoAttention, MiaoEffect, MiaoProperAge, MiaoCertiProcess, MiaoPrice, MiaoFactory, MiaoNo, MiaoAmount);
        vaccinDao.insert(vaccin);
    }

//    获取对应疫苗名称
    public String getVaccinNameById(Long MiaoId){
        Vaccin vaccin = vaccinDao.load(MiaoId);
        return vaccin.getVaccinName();
    }
    public void updateVaccin(Vaccin vaccin,String MiaoName, String MiaoDetail, String MiaoEffect, String MiaoProperAge, String MiaoCertiProcess, Double MiaoPrice,String MiaoFactory,String MiaoNo,Long MiaoAmount){
            vaccin.setVaccinName(MiaoName);
            vaccin.setVaccinEffect(MiaoDetail);
            vaccin.setVaccinNo(MiaoNo);
            vaccin.setProduceCompany(MiaoFactory);
            vaccin.setVaccinAge(MiaoProperAge);
            vaccin.setVaccinProcess(MiaoCertiProcess);
            vaccin.setVaccinPrice(MiaoPrice);
            vaccin.setVaccinAmount(MiaoAmount);
            vaccin.setVaccinDisadv(MiaoEffect);
            vaccinDao.update(vaccin);
    }
    public void deleteVaccinById(Long miaoId){
        vaccinDao.deleteByKey(miaoId);
    }
    public List<Vaccin> getVaccinListByName(String selectText){
        List<Vaccin> vaccinList = new ArrayList<Vaccin>();
        vaccinList = vaccinDao.queryBuilder().where(VaccinDao.Properties.VaccinName.like("%"+selectText+"%")).list();
        return vaccinList;
    }
}
