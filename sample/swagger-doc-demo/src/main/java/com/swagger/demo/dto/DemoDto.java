package com.swagger.demo.dto;

import org.hibernate.validator.constraints.Length;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:21
 */
public class DemoDto {
    /**
     * id doc注释
     */
    private Long id;
    /**
     * name doc 注释
     */
    @Length(max = 2000)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
