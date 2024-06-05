package com.example.dutyplanner.domain.entity;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

    private String login;
    private String password;
    private String surname;
    private String name;
    private String middleName;

    public User(String login, String password, String surname, String name, String middleName)
    {
        this.login=login;
        this.password=password;
        this.surname=surname;
        this.middleName=middleName;
        this.name=name;
    }

    public void setLogin(String login) {this.login=login;}
    public void setPassword(String password) {
        this.password=password;
    }
    public void setName(String name) {this.name=name;}
    public void setSurname(String surname) {this.surname=surname;}
    public void setMiddleName(String middleName) {this.middleName=middleName;}
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getInitials() {
        return surname+" "+name.charAt(0)+" "+middleName.charAt(0);
    }
    public String getFullName() {return surname+" "+name+" "+middleName;}

}
