
###4. 打包源码

这里是最重要的一点，因为java编译后会把doc擦除，这就是为什么class文件里面很少能看见注释，所以需要利用源码来进行解析,所以需要使用maven插件
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
打包时把源码拷贝到source中

```shell

#!/usr/bin/env bash
mvn clean -U process-resources package -Dmaven.test.skip=true
cp target/swagger-doc-demo-1.0-SNAPSHOT.jar  source

```