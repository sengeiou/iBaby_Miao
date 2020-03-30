package com.atyume.ibabym.utils;

public class MyOrderList {
    private Long id;
    private String title;
    private String take_Ordertime;
    private String OrderTime;
    private String isfinish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTake_Ordertime() {
        return take_Ordertime;
    }

    public void setTake_Ordertime(String take_Ordertime) {
        this.take_Ordertime = take_Ordertime;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(String isfinish) {
        this.isfinish = isfinish;
    }
}
