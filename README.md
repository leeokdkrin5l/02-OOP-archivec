#swagger-doc 
##重点先说说这个项目解决了什么问题
这个项目跟swagger有着很大的联系，总得来说是给swagger解决了大部分人不想用swagger的问题，污染代码。大家可以来看看这是我之前用swagger的时候的代码

```java

@GetMapping("/v1/index/banner")
@ApiOperation(value = "获取首页Banner", response = BannerJsonDto.class)
@ApiResponse(code = 200, message = "success")
public ResultUtils.ObjectResult getBanners() {
      .....
    return ResultUtils.ToObjectResult(bannerJsonDtos);
}

```
如上所示，如果用swagger就会产生这种无用的注解，但是还没法不加 OK 继续来看看swagger-doc同样的代码应该怎么写

```java

/**
 * 模糊查询功能
 *
 * @param 
 * @return
 * @since 1.0
 */
@GetMapping("/select/like")
public ResponseEntity<PageResponse> selectLike(@Valid QueryParam querParam) {
     ......
    return ResponseEntity.success(pageResponse);
}

```

对比两种使用方式，我想大多数人都会喜欢下面一种方式，因为doc是大部分代码种不可少的东西，我理解的是如果大家在写javadoc的同时顺便把文档也写完了，是不是提高了很好的效率
所以就有此项目诞生

##快速开始

由于本项目还在构建中，所以没有扔到mvncenter，所以先通过mvn install 到本地仓库即可
###1.安装到本地仓库
```shell
cd swagger-doc
mvn install 
```
###2.项目中使用
```java
<dependency>
    <groupId>swagger-doc</groupId>
    <artifactId>swagger-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
###3.自定义url
```java

@RestController
public class SwaggerController {
    private volatile WrapSwagger wrapSwagger;

    @RequestMapping(value = "/swagger.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String wrapSwagger() {
        if (wrapSwagger == null) {
            wrapSwagger = SwaggerUtils.parseJarSource("source", UpmOpsApiApplication.getInstalce(),
                Arrays.asList("basicErrorController", "swaggerController"));
        }

        return BeanJsonConversionUtil.beanConversionJson(wrapSwagger);
    }

}


```
需要自己去定义返回的controller，(目前版本不是正式版，方式有些粗暴 请见谅)

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
###5. 搭建swagger-ui
下载最新版swagger-ui

https://github.com/swagger-api/swagger-ui


总体来说使用方式很简单，上面的quickstart仅仅只用于单模块项目，也就是说dto跟api在同一个项目里面，多模块复杂项目会在接下俩的文档里面继续讲解

