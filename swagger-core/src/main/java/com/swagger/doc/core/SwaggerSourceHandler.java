package com.swagger.doc.core;

import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * @author kangwang
 * @Description:
 * @date 2020/10/21
 */
public class SwaggerSourceHandler {
    public WrapSwagger parseDirectorySwagger(String filePath) {
        List<File> fileList  = FileUtils.listAllFile(new File(filePath));
        return null;
    }
}
