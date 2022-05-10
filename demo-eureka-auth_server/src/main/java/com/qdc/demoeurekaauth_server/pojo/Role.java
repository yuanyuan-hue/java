package com.qdc.demoeurekaauth_server.pojo;

import java.io.Serializable;
//序列化会自动实现，不需要自己打
public class Role implements Serializable {
//    private static final long serialVersionUID=8551327484428498338L;
    private Integer id;
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Role(){}

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
