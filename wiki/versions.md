
## 1.2.3
增加authorizations支持，自定义header
增加通用params
## 1.2.2 
1.支持按照包名忽略Controller,并且兼容Spring bean name


## 1.2.1
1. 修复spring mvc中 不忽略参数bug
2. 修复文件夹中识别多余文件bug
3. 支持@RequestBody 是List<DemoDto> 这种形式的参数
4. 修复@RequestMapping 中 不填写url就无法识别的bug
5. 支持spring 中直接使用 test(@RequestBody String str)这种方式

##1.2.0 2017-09-30
1. 增加Controller中对@deprecated注解的支持
2. 增加对spring mvc war包的完美支持
3. 解决spring MVC war中乱码问题
4. 优化部分逻辑
5. 解决一些已知bug
##1.1.0 2017-7-30
1.增加对JSR303 @Length 注解的支持
2.增加对@RequestParam的支持 
##1.0.0  2017-7-20
1.扫描注释生成swagger 的json

