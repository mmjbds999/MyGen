# 自动生成SpringMVC项目
稍后补上文档(●'◡'●)

文档补齐中------
# 创建项目
### 1.打开com.hy.tools.main.CreateProject.java | maven版本为CreateMavenProject.java

### 2.修改类文件顶部定义的字段value

```java
    public static String projectName = "Test";//项目名称
	public static String projectNameCN = "测试下噢";//项目名称--中文
	public static String dbName = "test";//数据库名称
	public static String packageName = "com.hy.test";//包名称
	public static String packagePath = "com\\\\hy\\\\test";//包路径
	public static String path = "G:\\\\JavaSave2015\\\\";//需要创建项目的文件夹路径
	public static String user = "root";//数据库用户
	public static String pwd = "root";//数据库密码
```

### 3.RUN，搞定

# 生成具体代码及页面
### 1.导入刚才生成的项目

### 2.随便找个地方写个Main方法用以生成项目代码
    总共分三个步骤进行生成（maven版本在生成类的类名前加Maven，如：GenDao改为MavenGenDao）
    
```java
    第一步：GenEntityForHibernateConf.genAll();//生成实体类，也就是po--依赖数据库
            GenDao.gen();//生成dao--依赖数据库
    第二步：GenVo.genVoList();//生成VO--依赖po
            GenService.genServiceList();//生成service--依赖po
    第三步：GenJsp.genByVo();//生成Action，Form，Jsp--依赖VO
```
    
# 调整界面
    因为我们的界面生成很多都是靠默认规则的，不是太准确，所以这里要对VO进行相应的调整
    1.我们生成vo的时候，vo里会生成一些默认的自定义注解，下面说说这些注解的作用
    
```java
    @AModelName--对当前vo所描述界面的定义
        字段：modelName//模块名称，用于在界面显示当前模块的名称
        字段：pageName//页面名称，一般对应表名，用于标识URL访问路径
        字段：pageType//页面类型，对应PageType枚举，描述页面的默认展现形式
        
    @AValid--定义界面jquery校验
        字段：ValidEnum//验证类型枚举数组，参照jquery-validation进行定义
        字段：param//辅助验证类型用的，现阶段只有类似重复密码的eq验证需要使用
        
    @AListObj--主要界面定义描述--除了ID以外，都要有
        字段：comment//字段中文描述，用于显示界面的列头
        字段：length//长度--默认生成，不解释
        字段：option//需要显示在什么界面--a-add,u-update,-s-select,v-view
        字段：optionName//暂时无用貌似，留待扩展小界面名称
        字段：cType//对应SaveInputType枚举，用于描述保存界面字段默认显示的html标签，详细项自己点进去看，不解释，有注释
        字段：vType//对应SaveInputType枚举，用于描述详情界面字段默认显示的html标签，暂时无用，需要扩展再说
        字段：selectEnum//select，checkedbox，radio如果数据从枚举来，那么这个字段就填入对应的枚举
        字段：selectVo//select，checkedbox，radio如果数据从po来，那么这个字段就填入对应的po
        字段：selectVoName//配合selevtVo使用，默认值对应的是name，如果填写了这个字段，那么对应得就是相应的字段名，用于解释界面对应的字段显示内容来源
    
    @ASearchObj--用于描述列表的查询条件以及form字段
        字段：type//对应SearchInputType枚举，用于描述查询界面字段默认显示的html标签，详细项自己点进去看，不解释，有注释
        字段：selectEnum//同上面一个注解，只是界面对应的是查询条件
```
        
    好了，看了那么多麻烦的东西，请不要感觉很复杂，因为大部分都生成好了，你只需要微调就好。
    
    最后，执行生成代码：GenJsp.genByVo();搞定，收工！
    
# TODO
    生成项目做成可执行程序，配个可视化界面。
    生成后的项目里多生成一个可用于微调vo的web界面
    界面模板配置成可上传形式的
    各种控件都有扩展空间，比如多附件上传的
    各种你想得到想不到的，有兴趣就来胡搞瞎搞吧，一切都是为了更高效便捷
