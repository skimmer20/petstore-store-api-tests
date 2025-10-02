package com.enums;

public enum OrderStatus {
    PLACED("placed"),
    APPROVED("approved"),
    DELIVERED("delivered");

    private final String value;

    OrderStatus(String v){
        this.value = v;
    }

    public String value(){
        return value;
    }
}
