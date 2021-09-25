
### 1 demo 项目里面的SwaggerController 配置了MappingJackson2HttpMessageConverter，把null值过滤掉了，如果仅仅只让SwaggerController过滤null怎么解决
可以通过gson去把wrapswagger转换成json 然后spring 返回一个string即可

### 2 swagger ui版本支持
目前只适配到swagger-ui 3.0.8，其他新版本支持，但是可能会存在多出一些无用的参数

### 3 Spring版本 

spring版本目前只支持Spring 4.3.x以上的版本

### 4 JDK版本

目前只支持1.8以上的jdk，其他版本因为调用api解析的问题暂时无法替代，正在解决中努力支持到1.7

