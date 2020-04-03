package com.atyume.ibabym.Model;

import com.atyume.greendao.gen.OrderExamInfoDao;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderExamInfo;
import com.atyume.ibabym.ui.dashboard.OrderExam;

import java.util.ArrayList;
import java.util.List;

public class OrderExamModel {
    private OrderExamInfoDao orderExamInfoDao = MyApplication.getInstances().getDaoSession().getOrderExamInfoDao();

    public void insertOrderExam(Long examId,Long babyId,String nowTime,String orderTime,Integer isFinish){
        OrderExamInfo orderExamInfo = new OrderExamInfo(examId,babyId,nowTime,orderTime,isFinish);
        orderExamInfoDao.insert(orderExamInfo);
    }
    public OrderExamInfo getOrderExamInfo(Long orderExamId){
        OrderExamInfo orderExamInfo = new OrderExamInfo();
        orderExamInfo = orderExamInfoDao.load(orderExamId);
        return orderExamInfo;
    }
    public int getOrderCountByDate(String orderTime){
        List<OrderExamInfo> orderExamInfoList = new ArrayList<OrderExamInfo>();
        orderExamInfoList = orderExamInfoDao.queryBuilder().where(OrderExamInfoDao.Properties.OrderTime.eq(orderTime),OrderExamInfoDao.Properties.IsSucced.eq(0)).list();
        return orderExamInfoList.size();
    }
    public void updateSucceed(OrderExamInfo orderExamInfo){
        orderExamInfo.setIsSucced(1);
        orderExamInfoDao.update(orderExamInfo);
    }
    public void updateCancel(OrderExamInfo orderExamInfo){
        orderExamInfo.setIsSucced(2);
        orderExamInfoDao.update(orderExamInfo);
    }

    public List<OrderExamInfo> getOrderList(Long inoculId){
        List<OrderExamInfo> orderExamInfoList = new ArrayList<OrderExamInfo>();
        orderExamInfoList = orderExamInfoDao.queryBuilder().where(OrderExamInfoDao.Properties.InoculId.eq(inoculId)).list();
        return orderExamInfoList;
    }

    public List<OrderExamInfo> getAlreadyFinishOrderList(Long inoculId){
        List<OrderExamInfo> orderExamInfoList = new ArrayList<OrderExamInfo>();
        orderExamInfoList = orderExamInfoDao.queryBuilder().where(OrderExamInfoDao.Properties.InoculId.eq(inoculId),OrderExamInfoDao.Properties.IsSucced.eq(1)).list();
        return orderExamInfoList;
    }
}
