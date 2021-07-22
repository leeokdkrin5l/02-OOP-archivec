package com.swagger.doc.core.param;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 上午11:57
 */
public class BodyParamParse extends AbstractParamParse {

    public BodyParamParse(ModelConverters modelConverters) {
        super(modelConverters);
    }

    @Override
    public List<Parameter> parseParameter(java.lang.reflect.Parameter parameter, JavaMethod javaMethod, Method method,
                                          String modelName, String paramName, Map<String, JavaClass> classJavaClassMap) {
        if (StringUtils.isEmpty(modelName))
            throw new IllegalArgumentException(String.format("method %s paramater %s can not use @RequestBody",
                method.getName(), parameter.getName()));
        BodyParameter bodyParameter = new BodyParameter();
        RefModel refModel = new RefModel();
        refModel.set$ref("#/definitions/" + modelName);
        bodyParameter.setName(paramName);
        bodyParameter.setSchema(refModel);
        return Arrays.asList(bodyParameter);
    }
}
