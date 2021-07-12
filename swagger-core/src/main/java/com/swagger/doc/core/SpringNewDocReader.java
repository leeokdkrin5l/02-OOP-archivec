package com.swagger.doc.core;

import com.swagger.doc.core.entity.RequestMappingInfo;
import com.swagger.doc.core.entity.SwaggerDoc;
import com.swagger.doc.core.utils.JavaSourceUtils;
import com.swagger.doc.core.utils.SpringAnnotationUtils;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.refs.GenericRef;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-05-02 上午9:47
 */
public class SpringNewDocReader extends AbstractDocReader {
    public SpringNewDocReader(Swagger swagger) {
        super(swagger);
    }

    private static ModelConverters modelConverters = new ModelConverters();
    private Map<String, JavaClass> classJavaClassMap;
    private SwaggerDoc             swaggerDoc;

    @Override
    public Swagger read(Map<String, JavaClass> classJavaClassMap, ApplicationContext configurableApplicationContext) {
        this.classJavaClassMap = classJavaClassMap;
        this.swaggerDoc = getSwaggerDoc(configurableApplicationContext);
        this.javaClassMap = classJavaClassMap;
        List<String> ignoreController = swaggerDoc.getIgnoreControllers();
        readControllerList(configurableApplicationContext, ignoreController);
        fixSwagger();
        return swagger;
    }

    private void fixSwagger() {
        swagger.setBasePath(swaggerDoc.getBasePath());
        swagger.setInfo(swaggerDoc.getInfo());
        swagger.setHost(swaggerDoc.getHost());
    }

    private void readControllerList(ApplicationContext configurableApplicationContext, List<String> ingnoreController) {
        Map<String, Object> objectMap = configurableApplicationContext.getBeansWithAnnotation(Controller.class);
        if (!CollectionUtils.isEmpty(ingnoreController)) {
            for (String s : ingnoreController) {
                objectMap.remove(s);
            }
        }
        for (Map.Entry<String, Object> controllerEntry : objectMap.entrySet()) {
            Class clazz = controllerEntry.getValue().getClass();
            JavaClass javaClass = classJavaClassMap.get(clazz.getName());
            String doc = JavaSourceUtils.getJavaClassDoc(javaClass, "desc");
            processMethod(clazz, javaClass);
            Tag tag = new Tag();
            tag.setName(clazz.getSimpleName());
            tag.setDescription(doc);
            swagger.addTag(tag);
        }
    }

    private void processMethod(Class clazz, JavaClass javaClass) {
        Method methods[] = clazz.getMethods();
        Map<String, JavaMethod> javaMethodMap = JavaSourceUtils.getAllMethod(javaClass);

        for (Method method : methods) {
            RequestMappingInfo requestMapping = SpringAnnotationUtils.getRequestMappingInfo(method);
            if (requestMapping == null)
                continue;
            JavaMethod javaMethod = javaMethodMap.get(method.getName());
            String doc = "";
            if (javaMethod != null) {
                doc = javaMethod.getComment();
            }

            for (String s : requestMapping.getValue()) {
                Path path = new Path();
                Operation operation = new Operation();
                operation.setConsumes(Arrays.asList(requestMapping.getConsumes()));
                operation.setProduces(Arrays.asList(requestMapping.getProduces()));
                operation.setParameters(processParameter(method, javaMethod));
                String operationId = "";
                for (RequestMethod requestMethod : requestMapping.getMethod()) {
                    switch (requestMethod) {
                        case GET:
                            path.get(operation);
                            break;
                        case POST:
                            path.post(operation);
                            break;
                        case PUT:
                            path.put(operation);
                            break;
                        case DELETE:
                            path.delete(operation);
                            break;
                        case OPTIONS:
                            path.options(operation);
                            break;
                    }
                    operationId += method.getName() + "Using" + requestMethod.name();
                }
                operation.setTags(Arrays.asList(method.getDeclaringClass().getSimpleName()));
                operation.addResponse("200", getResponse(method.getGenericReturnType()));
                if (javaMethod != null)
                    operation.setSummary(javaMethod.getComment());
                operation.setOperationId(operationId);
                operation.setDescription(doc);
                logger.debug("tag is {} msg is {}", method.getDeclaringClass().getSimpleName(), doc);
                swagger.path(SpringAnnotationUtils.getControllerPath(clazz) + s, path);
            }

        }
    }

    private Response getResponse(Type genericReturnType) {
        Response response = new Response();
        if (genericReturnType.getTypeName().equals("void"))
            return response;
        String name = null;
        if (genericReturnType instanceof Class) {
            name = readModelMap(genericReturnType, classJavaClassMap.get(((Class) genericReturnType).getName()));
        } else {
            name = readType(genericReturnType);
        }
        if (StringUtils.isEmpty(name)) {
            response.setSchema(modelConverters.readAsProperty(genericReturnType));
        } else {
            RefProperty properties = new RefProperty();
            response.setSchema(properties);
            properties.set$ref("#/definitions/" + name);
        }

        return response;
    }

    /**
     * 解析parameter
     * @param method
     * @param javaMethod
     * @return
     */
    private List<Parameter> processParameter(Method method, JavaMethod javaMethod) {
        List<Parameter> parameterList = new ArrayList<>();
        Map<Integer, String> paraMaterNameMap = new HashMap<>();
        for (int i = 0; i < method.getParameters().length; i++) {
            paraMaterNameMap.put(i, method.getParameters()[i].getName());
        }
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter parameterSwagger = null;
            java.lang.reflect.Parameter parameter = method.getParameters()[i];
            //判断这个参数是否需要跳过
            if (JavaSourceUtils.isSkip(parameter, swaggerDoc))
                continue;
            String name = null;
            if (!(parameter.getParameterizedType() instanceof Class)) {
                name = readType(parameter.getParameterizedType());
            } else {
                name = readModelMap(parameter.getType(), classJavaClassMap.get(parameter.getType().getName()));
            }
            //判断该参数是否是body对象
            if (SpringAnnotationUtils.isRequestBody(parameter)) {
                if (StringUtils.isEmpty(name))
                    throw new IllegalArgumentException(String.format(" method %s paramater %s can not use @RequestBody",
                        method.getName(), parameter.getName()));
                parameterSwagger = new BodyParameter();
                //                if (StringUtils.isEmpty(name)) {
                //                    parameterSwagger = new QueryParameter();
                //                    QueryParameter queryParameter = (QueryParameter) parameterSwagger;
                //                    queryParameter.setProperty(modelConverters.readAsProperty(parameter.getParameterizedType()));
                //                    queryParameter.setName(paraMaterNameMap.get(i));
                //                } else {
                RefModel refModel = new RefModel();
                refModel.set$ref("#/definitions/" + name);
                parameterSwagger.setName(paraMaterNameMap.get(i));
                ((BodyParameter) parameterSwagger).setSchema(refModel);
                //                }

            } else {
                //如果是path param
                if (SpringAnnotationUtils.isPathParam(parameter)) {
                    parameterSwagger = new PathParameter();
                    ((PathParameter) parameterSwagger)
                        .setProperty(modelConverters.readAsProperty(parameter.getParameterizedType()));
                    if (javaMethod != null) {
                        String desc = JavaSourceUtils.readParamDesc(JavaSourceUtils.readJavaMethodParam(javaMethod),
                            paraMaterNameMap.get(i));
                        parameterSwagger.setDescription(desc);
                    }
                } else {
                    Map<String, Model> parameterMap = modelConverters.read(parameter.getParameterizedType());
                    if (parameterMap.isEmpty()) {
                        parameterSwagger = new QueryParameter();
                        QueryParameter queryParameter = (QueryParameter) parameterSwagger;
                        queryParameter.setProperty(modelConverters.readAsProperty(parameter.getParameterizedType()));
                        queryParameter.setName(paraMaterNameMap.get(i));
                        if (javaMethod != null) {
                            String desc = JavaSourceUtils.readParamDesc(JavaSourceUtils.readJavaMethodParam(javaMethod),
                                paraMaterNameMap.get(i));
                            parameterSwagger.setDescription(desc);
                        }

                    } else {
                        for (Map.Entry<String, Model> modelEntry : parameterMap.entrySet()) {
                            for (Map.Entry<String, Property> stringPropertyEntry : modelEntry.getValue().getProperties()
                                .entrySet()) {
                                QueryParameter queryParameter = new QueryParameter();
                                queryParameter.setProperty(stringPropertyEntry.getValue());
                                queryParameter.setName(stringPropertyEntry.getKey());
                                JavaClass javaClass = classJavaClassMap
                                    .get(((Class) (parameter.getParameterizedType())).getName());
                                if (javaClass != null) {
                                    JavaField field = javaClass.getFieldByName(stringPropertyEntry.getKey());
                                    if (field != null)
                                        queryParameter.setDescription(field.getComment());
                                }
                                parameterList.add(queryParameter);

                            }
                        }
                    }
                }

            }

            if (parameterSwagger != null)
                parameterList.add(parameterSwagger);

        }

        return parameterList;
    }

    private String readType(Type typeImpl) {
        JavaClass javaClass = null;
        String name = "";
        javaClass = classJavaClassMap.get(typeImpl.getTypeName());
        Map<String, Model> stringModelMap = modelConverters.read(typeImpl);
        for (Map.Entry<String, Model> stringModelEntry : stringModelMap.entrySet()) {
            treeProcess(stringModelEntry.getValue().getProperties(), stringModelEntry.getKey());
        }

        ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) typeImpl;
        Class rawType = parameterizedType.getRawType();
        Map<String, Type> stringClassMap = new HashMap<>();
        if (rawType.equals(List.class)) {
            for (Type type : parameterizedType.getActualTypeArguments()) {
                if (type instanceof ParameterizedTypeImpl) {
                    readType(type);
                } else {
                    readModelMap(type, classJavaClassMap.get(type.getTypeName()));
                }
            }
        } else if (rawType.equals(Map.class)) {
            logger.debug("map");
        } else {
            javaClass = classJavaClassMap.get(rawType.getName());
            TypeVariable<Class>[] typeVariables = rawType.getTypeParameters();
            Type[] types = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < typeVariables.length; i++) {
                stringClassMap.put(typeVariables[i].getName(), types[i]);
            }
        }
        for (Map.Entry<String, Model> stringModelEntry : stringModelMap.entrySet()) {
            swagger.addDefinition(stringModelEntry.getKey(), stringModelEntry.getValue());
            if (javaClass != null)
                JavaSourceUtils.readClassFieldDoc(stringModelEntry.getValue(), javaClass);
            name = stringModelEntry.getKey();
            for (Map.Entry<String, Property> stringPropertyEntry : stringModelEntry.getValue().getProperties()
                .entrySet()) {
                if (stringPropertyEntry.getValue() instanceof RefProperty) {
                    RefProperty property = (RefProperty) stringPropertyEntry.getValue();
                    //拿到该字段对应的类
                    try {
                        Field field = rawType.getDeclaredField(property.getName());
                        TypeVariableImpl p = (TypeVariableImpl) field.getGenericType();
                        String typeName = p.getTypeName();
                        Type type = stringClassMap.get(typeName);
                        if (type instanceof Class) {
                            readModelMap(type, classJavaClassMap.get(((Class) type).getName()));
                        } else {
                            readType(type);
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                } else if (stringPropertyEntry.getValue() instanceof ArrayProperty) {
                    ArrayProperty property = (ArrayProperty) stringPropertyEntry.getValue();
                    try {
                        Field field = rawType.getDeclaredField(property.getName());
                        String typeName = "";
                        if (field.getGenericType() instanceof TypeVariableImpl) {
                            TypeVariableImpl p = (TypeVariableImpl) field.getGenericType();
                            typeName = p.getTypeName();
                        } else {
                            typeName = ((ParameterizedTypeImpl) field.getGenericType()).getActualTypeArguments()[0]
                                .getTypeName();
                        }

                        Type type = stringClassMap.get(typeName);
                        if (type instanceof Class) {
                            readModelMap(type, classJavaClassMap.get(((Class) type).getName()));
                        } else {
                            readType(type);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    logger.debug("array");
                } else if (stringModelEntry.getValue() instanceof MapProperty) {
                    logger.debug("map");
                }
            }
        }
        return name;
    }

    private void treeProcess(Map<String, Property> propertyMap, String k) {
        if (propertyMap == null) {
            logger.warn("treeProcess propertyMap is null {}", k);
            return;
        }
        for (Map.Entry<String, Property> stringPropertyEntry : propertyMap.entrySet()) {
            if (stringPropertyEntry.getValue() instanceof ArrayProperty) {
                ArrayProperty arrayProperty = (ArrayProperty) stringPropertyEntry.getValue();
                if (arrayProperty.getItems() instanceof RefProperty) {
                    RefProperty refProperty = (RefProperty) arrayProperty.getItems();
                    try {
                        Field f = refProperty.getClass().getDeclaredField("genericRef");
                        f.setAccessible(true);
                        GenericRef genericRef = (GenericRef) f.get(refProperty);
                        if (StringUtils.equals(genericRef.getSimpleRef(), k)) {
                            arrayProperty.setItems(new ObjectProperty());
                        }
                    } catch (NoSuchFieldException e) {
                        continue;
                    } catch (IllegalAccessException e) {
                        continue;
                    }
                }
            }
        }
    }

    private String readModelMap(Type targetClass, JavaClass javaClass) {
        Map<String, Model> modelMap = modelConverters.read(targetClass);
        StringBuilder stringBuilder = new StringBuilder();
        if (!modelMap.isEmpty()) {
            modelMap.forEach((k, v) -> {
                stringBuilder.append(k);
                treeProcess(v.getProperties(), k);
                if (swagger.getDefinitions() != null && swagger.getDefinitions().get(k) != null) {
                    Map<String, Property> propertyMap = swagger.getDefinitions().get(k).getProperties();
                    return;
                }
                swagger.addDefinition(k, v);
                if (javaClass != null)
                    JavaSourceUtils.readClassFieldDoc(v, javaClass);
                Map<String, Property> propertyMap = v.getProperties();
                propertyMap.forEach((k1, v1) -> {
                    if (v1 instanceof ArrayProperty) {
                        //如果list里面的item是对象
                        if (((ArrayProperty) v1).getItems() instanceof RefProperty) {
                            logger.debug(v1.toString());
                            try {
                                Field field = ((Class) (targetClass)).getDeclaredField(v1.getName());
                                Type type = field.getGenericType();
                                if (type instanceof ParameterizedTypeImpl) {
                                    ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) type;
                                    logger.debug("#############{}",
                                        parameterizedType.getActualTypeArguments()[0].getTypeName());
                                    readModelMap(parameterizedType.getActualTypeArguments()[0], classJavaClassMap
                                        .get(parameterizedType.getActualTypeArguments()[0].getTypeName()));
                                }
                            } catch (NoSuchFieldException e) {

                            }
                        }
                        logger.debug(v1.toString());
                    } else if (v1 instanceof RefProperty) {
                        try {
                            Type realClass = ((Class) targetClass).getDeclaredField(v1.getName()).getGenericType();
                            readModelMap(realClass, classJavaClassMap.get(realClass.getTypeName()));
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        logger.debug(v1.toString());
                    } else if (v1 instanceof MapProperty) {
                        Class target = (Class) targetClass;
                        try {
                            Type type = target.getDeclaredField(k1).getGenericType();
                            if (type instanceof ParameterizedTypeImpl) {
                                ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) type;
                                for (Type type1 : parameterizedType.getActualTypeArguments()) {
                                    if (type1 instanceof Class) {
                                        readModelMap(type1, classJavaClassMap.get(((Class) type1).getName()));
                                    } else if (type1 instanceof ParameterizedTypeImpl) {
                                        readType(type1);
                                    }
                                }
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        logger.debug(v1.toString());
                    }
                });
            });
        }
        return stringBuilder.toString();
    }

}
