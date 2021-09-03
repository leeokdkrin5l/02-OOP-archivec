## Spring mvc的支持
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

## Spring mvc war包支持
打war包的时候会导致一些代码的中文描述被擦掉，所以需要做以下配置
### 第一步 maven配置
```xml
 <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-source-plugin</artifactId>
    <version>3.0.1</version>
    <executions>
        <execution>
            <id>attach-sources</id>
            <goals>
                <goal>jar-no-fork</goal>
            </goals>
        </execution>
    </executions>
</plugin>

```
首先在maven增加打包源码的插件,然后用maven打包命令打包，例如`swagger-doc-springmvc-demo`这个项目产生的war包在`target/swagger-core-1.0-SNAPSHOT-sources.jar`
复制该源码到文件夹`src/main/resources/source`文件夹中即可，然后重新打包war进行部署
### 第二步xml配置

```xml

<bean id="configProperties" class="com.swagger.doc.core.properties.ConfigProperties">
        <property name="useWar" value="true"></property>
</bean>

```
配置项目采用的是war包


完整项目可以查看
http://git.oschina.net/wangkang_daydayup/swagger-doc/tree/master/sample/swagger-doc-springmvc-demo