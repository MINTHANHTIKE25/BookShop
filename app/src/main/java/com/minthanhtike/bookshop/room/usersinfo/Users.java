package com.minthanhtike.bookshop.room.usersinfo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_table")
public class Users {
    @PrimaryKey(autoGenerate = true)
    int tbId;
    String name;
    String password;
    String age;
    String email;
    String address;
    String phno;
    String nrc;

    public Users(String name, String password, String age, String email, String address, String phno, String nrc) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phno = phno;
        this.nrc = nrc;
    }

    public Users(int tbId, String name, String password, String age, String email, String address, String phno, String nrc) {
        this.tbId = tbId;
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phno = phno;
        this.nrc = nrc;
    }

    public Users() {
    }

    public int getTbId() {
        return tbId;
    }

    public void setTbId(int tbId) {
        this.tbId = tbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

}
