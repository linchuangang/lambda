package com.lin.model;

/**
 * Created by Administrator on 2018/7/30.
 */
public class Student {
    private Integer id;
    private String name;
    private Double score;
    public Student(Integer id,String name, Double score) {
        this.name = name;
        this.score = score;
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
