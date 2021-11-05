package com.swagger.doc.admin.controller;

import com.swagger.doc.admin.dto.ResponseEntity;
import com.swagger.doc.admin.service.ProjectService;
import com.swagger.doc.core.entity.WrapSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kangwang
 * @Description:
 * @date 2020/9/1
 */
@RequestMapping("/admin/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/swagger/{id:\\d+}")
    public ResponseEntity<WrapSwagger> swaggerJson(@PathVariable("id") Long id) {
        WrapSwagger wrapSwagger = projectService.findProjectSwagger(id);
        return ResponseEntity.success(wrapSwagger);
    }
}
