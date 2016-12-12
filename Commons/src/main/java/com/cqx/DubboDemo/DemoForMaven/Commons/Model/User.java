package com.cqx.DubboDemo.DemoForMaven.Commons.Model;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cqx on 19/7/2016.
 */
@Entity
@Table(name = "User")
public class User implements java.io.Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Transient
    private List<String> roles = new ArrayList<String>();

    @Column(name = "weight")
    private Double weight;

    @Column(name = "birthday")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @Column(name = "lastUpdateDate")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

    @Column(name = "registerDate")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registerDate;

    @Column(name = "createDate")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "locked")
    private Boolean locked;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }




    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
