package com.swagger.doc.admin.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author kangwang
 * @Description:
 * @date 2020/10/21
 */
@AllArgsConstructor
@Getter
public enum ProjectTypeEnum {
    SPRING_MVC("Spring mvc的项目");
    private String desc;
}
