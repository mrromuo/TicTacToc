package com.raeed.tictactoc.sideClasses;

public class FrendRqusts {
    private String RqustBy_Id;
    private String RqustTo_Id;
    private String OrderNumber;

    public FrendRqusts(String rqustBy_Id, String rqustTo_Id, String orderNumber) {
        RqustBy_Id = rqustBy_Id;
        RqustTo_Id = rqustTo_Id;
        OrderNumber = orderNumber;
    }

    public String getRqustBy_Id() {
        return RqustBy_Id;
    }

    public void setRqustBy_Id(String rqustBy_Id) {
        RqustBy_Id = rqustBy_Id;
    }

    public String getRqustTo_Id() {
        return RqustTo_Id;
    }

    public void setRqustTo_Id(String rqustTo_Id) {
        RqustTo_Id = rqustTo_Id;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }
}
