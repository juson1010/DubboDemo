package com.cqx.DubboDemo.DemoForMaven.Commons.Model;


import javax.persistence.*;

/**
 * Created by cqx on 16/7/22.
 */
@Entity
@Table(name = "Role")
public class Role implements java.io.Serializable {

        @Id
        @GeneratedValue
        @Column(name = "id")
    private int id;
//    private String id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
