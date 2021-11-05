package com.swagger.doc.admin.entity;

import com.swagger.doc.admin.constants.ProjectTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kangwang
 * @Description:
 * @date 2020/9/1
 */
@Getter
@Setter
public class Project {
    private Long id;
    private String name;
    private String desc;
    private String gitAddr;
    /**
     * 项目的类型
     */
    private ProjectTypeEnum type;
}
