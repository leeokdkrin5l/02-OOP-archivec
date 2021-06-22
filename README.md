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
如果所示，如果用swagger就会产生这种无用的注解，但是海没法不加OK 继续来看看swagger-doc同样的代码应该怎么写

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

##项目背景
先来说说该项目的背景,如果熟悉swagger的人都知道swagger是一个文档生成器，同时也包含了代码生成器，能够通过注解的形式来实现文档
