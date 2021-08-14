##Spring mvc的支持
swagger-doc目前是支持Spring mvc的，支持方式相当简单，仅仅需要一段配置而已

```xml
    <bean id="configSwaggerDoc" class="com.swagger.doc.core.ConfigSwaggerDoc"/>
```
如上代码所示，只需要在xml中声明即可，如果有特殊配置，可以自己定义`SwaggerDoc` 也是可以的
```xml
<bean class="com.swagger.doc.core.entity.SwaggerDoc">
        <property name="doc" value="测试"></property>
    </bean>

```

完整项目可以查看
http://git.oschina.net/wangkang_daydayup/swagger-doc/tree/master/sample/swagger-doc-springmvc-demo