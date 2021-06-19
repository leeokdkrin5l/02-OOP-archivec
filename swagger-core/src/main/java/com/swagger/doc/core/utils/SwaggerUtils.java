package com.swagger.doc.core.utils;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.types.ResolvedArrayType;
import com.fasterxml.classmate.types.ResolvedObjectType;
import com.fasterxml.classmate.types.ResolvedPrimitiveType;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.swagger.doc.core.SpringNewDocReader;
import com.swagger.doc.core.entity.WrapSwagger;
import com.thoughtworks.qdox.model.JavaClass;
import io.swagger.models.Swagger;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import springfox.documentation.schema.DefaultGenericTypeNamingStrategy;
import springfox.documentation.schema.DefaultTypeNameProvider;
import springfox.documentation.spi.schema.GenericTypeNamingStrategy;
import springfox.documentation.spi.schema.TypeNameProviderPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Optional.fromNullable;
import static springfox.documentation.schema.Types.typeNameFor;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-31 下午2:10
 */
public class SwaggerUtils {
    private static final TypeResolver typeResolver = new TypeResolver();

    public static WrapSwagger parseJarSource(String source_dir, ApplicationContext configurableApplicationContext,
                                             List<String> ingnoreController) {
        File file = new File(source_dir);
        File files[] = file.listFiles();
        List<JavaClass> javaClassList = new ArrayList<>();
        if (files != null)
            for (File file1 : files) {
                if (file1.isDirectory())
                    continue;
                try {
                    List<JavaClass> list = SourceReader.readFile(file1);
                    javaClassList.addAll(list);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        Map<String, JavaClass> javaClassMap = SourceReader.transforJavaClass(javaClassList);
        Swagger swagger = new SpringNewDocReader(new Swagger()).read(javaClassMap, configurableApplicationContext,
            ingnoreController);
        WrapSwagger wrapSwagger = new WrapSwagger();
        BeanUtils.copyProperties(swagger, wrapSwagger);
        wrapSwagger.process();
        return wrapSwagger;
        //        String json = BeanJsonConversionUtil.beanConversionJson(swagger);
        //        File file1 = new File("swagger.json");
        //        //        File file2 = new File("markdown.md");
        //        try {
        //            //            TemplateLoader loader = new ClassPathTemplateLoader();
        //            //            loader.setPrefix("");
        //            //            loader.setSuffix(".hbs");
        //            //            Handlebars handlebars = new Handlebars(loader);
        //            //            initHandlebars(handlebars);
        //            //            Template template = handlebars.compile("markdown");
        //            //            String content = template.apply(wrapSwagger);
        //            //            FileWriter fileWriter1 = new FileWriter(file2);
        //            //            fileWriter1.write(content);
        //            //            fileWriter1.close();
        //
        //            FileWriter fileWriter = new FileWriter(file1);
        //            fileWriter.write(json);
        //            fileWriter.flush();
        //            fileWriter.close();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
    }

    private static void initHandlebars(Handlebars handlebars) {
        handlebars.registerHelper("ifeq", (Helper<String>) (value, options) -> {
            if (value == null || options.param(0) == null) {
                return options.inverse();
            }
            if (value.equals(options.param(0))) {
                return options.fn();
            }
            return options.inverse();
        });

        handlebars.registerHelper("basename", (Helper<String>) (value, options) -> {
            if (value == null) {
                return null;
            }
            int lastSlash = value.lastIndexOf("/");
            if (lastSlash == -1) {
                return value;
            } else {
                return value.substring(lastSlash + 1);
            }
        });
        handlebars.registerHelper("lowercase", (Helper<String>) (value, options) -> {

            if (value == null) {
                return null;
            }
            int lastSlash = value.lastIndexOf("/");
            if (lastSlash == -1) {
                return value;
            } else {
                return value.substring(lastSlash + 1).toLowerCase();
            }
        });
        handlebars.registerHelper("emptyif", (Helper<String>) (value, options) -> {

            if (value == null || value.equals("")) {
                return "无";
            }
            return value;
        });
        handlebars.registerHelper(StringHelpers.join.name(), StringHelpers.join);
        handlebars.registerHelper(StringHelpers.lower.name(), StringHelpers.lower);

    }

    /**
     * 拿到这个类的名称
     * @param type
     * @return
     */
    public static String typeName(Type type) {
        ResolvedType resolvedType = asResolved(type);
        if (isContainerType(resolvedType)) {
            return containerType(resolvedType);
        }
        return innerTypeName(resolvedType);
    }

    private static String innerTypeName(ResolvedType type) {
        if (type.getTypeParameters().size() > 0 && type.getErasedType().getTypeParameters().length > 0) {
            return genericTypeName(type);
        }
        return simpleTypeName(type);
    }

    private static String simpleTypeName(ResolvedType type) {
        Class<?> erasedType = type.getErasedType();
        if (type instanceof ResolvedPrimitiveType) {
            return typeNameFor(erasedType);
        } else if (erasedType.isEnum()) {
            return "string";
        } else if (type instanceof ResolvedArrayType) {
            GenericTypeNamingStrategy namingStrategy = new DefaultGenericTypeNamingStrategy();
            return String.format("Array%s%s%s", namingStrategy.getOpenGeneric(),
                simpleTypeName(type.getArrayElementType()), namingStrategy.getCloseGeneric());
        } else if (type instanceof ResolvedObjectType) {
            String typeName = typeNameFor(erasedType);
            if (typeName != null) {
                return typeName;
            }
        }
        return typeNameDefault(type.getErasedType());
    }

    private static ResolvedType asResolved(Type type) {
        return typeResolver.resolve(type);
    }

    private static String genericTypeName(ResolvedType resolvedType) {
        Class<?> erasedType = resolvedType.getErasedType();
        GenericTypeNamingStrategy namingStrategy = new DefaultGenericTypeNamingStrategy();

        String simpleName = fromNullable(typeNameFor(erasedType)).or(typeName(resolvedType.getErasedType()));
        StringBuilder sb = new StringBuilder(String.format("%s%s", simpleName, namingStrategy.getOpenGeneric()));
        boolean first = true;
        for (int index = 0; index < erasedType.getTypeParameters().length; index++) {
            ResolvedType typeParam = resolvedType.getTypeParameters().get(index);
            if (first) {
                sb.append(innerTypeName(typeParam));
                first = false;
            } else {
                sb.append(String.format("%s%s", namingStrategy.getTypeListDelimiter(), innerTypeName(typeParam)));
            }
        }
        sb.append(namingStrategy.getCloseGeneric());
        return sb.toString();
    }

    public static boolean isContainerType(ResolvedType type) {
        if (List.class.isAssignableFrom(type.getErasedType()) || Set.class.isAssignableFrom(type.getErasedType())
            || type.isArray()) {
            return true;
        }
        return false;
    }

    public static String containerType(ResolvedType type) {
        if (List.class.isAssignableFrom(type.getErasedType())) {
            return "List";
        } else if (Set.class.isAssignableFrom(type.getErasedType())) {
            return "Set";
        } else if (type.isArray()) {
            return "Array";
        } else {
            throw new UnsupportedOperationException(String.format("Type is not collection type %s", type));
        }
    }

    private static String typeNameDefault(Type type) {
        TypeNameProviderPlugin selected = new DefaultTypeNameProvider();
        return selected.nameFor((Class<?>) type);
    }
}
