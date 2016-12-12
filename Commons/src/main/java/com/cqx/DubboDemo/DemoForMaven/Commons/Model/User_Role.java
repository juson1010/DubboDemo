package com.cqx.DubboDemo.DemoForMaven.Commons.Model;


import javax.persistence.*;

/**
 * Created by cqx on 16/7/22.
 */
@Entity
@Table(name = "User_Role")
public class User_Role implements java.io.Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id")
    protected int id;
//    private String id;

    @Column(name = "userId")
    private int userId;

    @Column(name = "roleId")
    private int roleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
