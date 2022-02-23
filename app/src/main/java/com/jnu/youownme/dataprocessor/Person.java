package com.jnu.youownme.dataprocessor;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String number;
    private String reason;
    private String date;

    public Person(String name, String number, String reason,String date) {
        this.name=name;
        this.number=number;
        this.reason=reason;
        this.date=date;
    }

    public String getName() {
        return name;
    }
    public String getNumber() { return number; }
    public String getReason() {
        return reason;
    }
    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name=name;
    }
    public void setReason(String reason) {this.reason=reason;}
    public void setNumber(String number) {this.number=number;}
    public void setDate(String date) {this.date=date;}
}
