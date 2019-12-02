package com.foxhis.sendip.sendip.util;

public enum AccessWebsite{

    SOHU(1,"http://pv.sohu.com/cityjson?ie=utf-8"),
    CIP(2,"http://www.cip.cc/"),
    CHINAZ(3,"http://ip.tool.chinaz.com/"),
    NET(4,"http://www.net.cn/static/customercare/yourip.asp");

    private int code;
    private String name;

    private AccessWebsite(int code,String url) {
        this.code = code;
        this.name = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
