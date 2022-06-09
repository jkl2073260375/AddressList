package com.example.addressbook;

public class InfoBean {
    private String name;
    private String tel;
    private String home;
    private String sex;
    public InfoBean(String name, String tel, String home, String sex) {
        this.name=name;
        this.tel=tel;
        this.home=home;
        this.sex=sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


}
