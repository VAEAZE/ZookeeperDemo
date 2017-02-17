package com.dongnao.zookeeper;

/**
 * @author parker
 * @date 2016/12/13
 */
public class Student {

    private String name;

    private String phone;

    public Student setName(String name){
        this.name=name;
        return this;
    }
    public Student setPhone(String phone){
        this.phone=phone;
        return this;
    }

    public static Student build(){
        return new Student();
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
