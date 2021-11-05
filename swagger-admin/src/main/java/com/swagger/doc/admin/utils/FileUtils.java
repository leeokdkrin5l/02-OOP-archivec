package com.swagger.doc.admin.utils;


import com.swagger.doc.core.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author kangwang
 * @Description:
 * @date 2020/9/1
 */
public class FileUtils {
    public static final List<File> listAllFile(File fileDirectory, String suffix) {
        File file = fileDirectory;
        List<File> allListFile = new ArrayList<>();
        File[] allFile = file.listFiles();
        Stack<File> stack = new Stack<>();
        if (allFile != null) {
            for (File tmp : allFile) {
                stack.push(tmp);
            }
        }
        while (!stack.isEmpty()) {
            File tmpFile = stack.pop();
            if (tmpFile.isDirectory()) {
                File[] files = tmpFile.listFiles();
                if (files == null) {
                    continue;
                }
                for (File tmp : files) {
                    stack.push(tmp);
                }
                continue;
            }
            if (StringUtils.isBlank(suffix)) {
                allListFile.add(tmpFile);
            } else if (StringUtils.endsWith(tmpFile.getName(), suffix)) {
                allListFile.add(tmpFile);
            }

        }
        return allListFile;
    }
}
