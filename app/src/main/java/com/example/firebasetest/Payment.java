//IT19151984
//M.P.N.D.Marasingha
package com.example.firebasetest;

public class Payment {
    private String number;
    private String name;

    private String date;
    private Integer security;

    public Payment() {
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSecurity() {
        return security;
    }

    public void setSecurity(Integer security) {
        this.security = security;
    }
}


