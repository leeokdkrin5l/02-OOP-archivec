package com.swagger.doc.core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/24
 */
public class FileUtils {
    public static boolean isJarFile(File file) {
        String subffix = StringUtils.getFileSuffix(file.getName());
        return StringUtils.equals(".jar", subffix);
    }

    public static List<File> listAllFile(File file) {
        List<File> fileRes = new ArrayList<>();
        File[] roots = file.listFiles();
        if (CollectionUtils.isEmpty(roots)) {
            return fileRes;
        }
        Stack<File> fileStack = new Stack<>();
        for (File root : roots) {
            fileStack.add(root);
        }
        while (!fileStack.empty()) {
            File fileTree = fileStack.pop();
            if (fileTree.isDirectory()) {
                for (File listFile : fileTree.listFiles()) {
                    fileStack.push(listFile);
                }
                continue;
            }
            fileRes.add(fileTree);
        }
        return fileRes;
    }

}
