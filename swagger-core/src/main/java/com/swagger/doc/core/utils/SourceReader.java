package com.swagger.doc.core.utils;

import com.thoughtworks.qdox.library.SortedClassLibraryBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-03-24 下午4:33
 */
public class SourceReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(SourceReader.class);

    private SourceReader() {
    }

    public static List<JavaClass> readFile(File file) throws IOException {
        SortedClassLibraryBuilder classLibraryBuilder = new SortedClassLibraryBuilder();
        classLibraryBuilder.appendDefaultClassLoaders();
        JarFile jarFile = new JarFile(file);
        try {
            List<JarEntry> entryList = Collections.list(jarFile.entries()).stream()
                    .filter(entry -> !entry.isDirectory() && entry.getName().endsWith(".java"))
                    .collect(Collectors.toList());
            for (JarEntry jarEntry : entryList) {
                try (InputStream in = jarFile.getInputStream(jarEntry)) {
                    classLibraryBuilder.addSource(new InputStreamReader(in, "utf-8"));
                }
            }
        } catch (Exception e) {
            LOGGER.warn("", e);
        } finally {
            jarFile.close();
        }

        return new ArrayList<>(classLibraryBuilder.getClassLibrary().getJavaClasses());
    }

    public static List<JavaClass> readJavaFiles(List<File> classList) throws IOException {
        List<JavaClass> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(classList))
            return result;
        SortedClassLibraryBuilder classLibraryBuilder = new SortedClassLibraryBuilder();
        classLibraryBuilder.appendDefaultClassLoaders();
        for (File file : classList) {
            try {
                classLibraryBuilder.addSource(file);
            } catch (Exception e) {
                LOGGER.error("", e);
            }

        }
        return new ArrayList<>(classLibraryBuilder.getClassLibrary().getJavaClasses());

    }

    /**
     * 把class转换成map
     *
     * @param classList
     * @return
     */
    public static Map<String, JavaClass> transforJavaClass(List<JavaClass> classList) {
        Map<String, JavaClass> javaClassMap = new HashMap<>();
        for (JavaClass javaClass : classList) {
            javaClassMap.put(javaClass.getFullyQualifiedName(), javaClass);
        }
        return javaClassMap;
    }
}
