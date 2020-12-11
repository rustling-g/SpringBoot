package com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

//使用jpa注解配置映射关系
@Entity //告诉JPA这是一个实体类
@Table(name = "user")   //和数据库中的表映射,name若省略，默认表名=类名
@JsonIgnoreProperties("hibernateLazyInitializer")
public class User {
    @Id     //指明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private Integer id;
    @Column(length = 50)
    private String lastName;
    @Column
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
