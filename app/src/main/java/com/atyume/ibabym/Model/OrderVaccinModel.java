package com.atyume.ibabym.Model;

import com.atyume.greendao.gen.OrderVaccinDao;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderVaccin;

import java.util.ArrayList;
import java.util.List;

public class OrderVaccinModel {
    private OrderVaccinDao orderVaccinDao = MyApplication.getInstances().getDaoSession().getOrderVaccinDao();
    public void insertOrderVaccin(String certiTime,String nowTime,Long babyId,Long miaoId,Long hosId,Integer isSucceed){
        OrderVaccin orderVaccin = new OrderVaccin(certiTime,nowTime,babyId,miaoId,hosId,0);
        orderVaccinDao.insert(orderVaccin);
    }
    public OrderVaccin getOrderVaccin(Long orderVaccinId){
        OrderVaccin orderVaccin = new OrderVaccin();
        orderVaccin = orderVaccinDao.load(orderVaccinId);
        return orderVaccin;
    }
    public int getOrderCountByDate(String orderTime){
        List<OrderVaccin> orderVaccinList = new ArrayList<OrderVaccin>();
        orderVaccinList = orderVaccinDao.queryBuilder().where(OrderVaccinDao.Properties.InocluTime.eq(orderTime),OrderVaccinDao.Properties.IsSucceed.eq(0)).list();
        return orderVaccinList.size();
    }
    public void updateSucceed(OrderVaccin orderVaccin){
        orderVaccin.setIsSucceed(1);
        orderVaccinDao.update(orderVaccin);
    }
    public void updateCancel(OrderVaccin orderVaccin){
        orderVaccin.setIsSucceed(2);
        orderVaccinDao.update(orderVaccin);
    }
    public List<OrderVaccin> getOrderList(Long inoculId){
        List<OrderVaccin> orderVaccinList = new ArrayList<OrderVaccin>();
        orderVaccinList = orderVaccinDao.queryBuilder().where(OrderVaccinDao.Properties.InocluId.eq(inoculId)).list();
        return orderVaccinList;
    }
    public List<OrderVaccin> getAlreadyFinishOrderList(Long inoculId){
        List<OrderVaccin> orderVaccinList = new ArrayList<OrderVaccin>();
        orderVaccinList = orderVaccinDao.queryBuilder().where(OrderVaccinDao.Properties.InocluId.eq(inoculId),OrderVaccinDao.Properties.IsSucceed.eq(1)).list();
        return orderVaccinList;
    }
}
