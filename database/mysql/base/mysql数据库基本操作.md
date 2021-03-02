### mysql数据库基本操作

#### 一、使用MYSQL
***
1. 使用数据库:use [database]
2. 查看数据库：show databases;
3. 查看数据表格：show tables;
4. 查看表列信息：show colunms from [database] 或者 describe [database];
5. 查看某个变量值：show status where Variable_name = ?;
6. 查询授权用户的安全权限：show grants;
7. 查询服务器错误和警告：show errors; 与 show warnnings;

#### 二、检索数据
*** 
1. 检索单列数据：select [column] from [table];
2. 检索多列数据：select [column],[column],[column] from [table];
3. 检索所有列数据：select * from [table];
4. 检索不同的列：select distinct [column] from [table];
5. 限定结果：select * from [table] limit [startLine-1],[show_column_num];</br>
   或者使用 select * from [table] limit [show_column_num] offset [startLine-1]; 
6. 使用全限定表名：select [table].[column] from [table];

#### 三、排序检索数据
*** 
1. 排序数据：select * from [table] order by [column];
2. 按多个字段排序：select * from [table] order by [column],[column];
3. 执行排序方向：select * from [table] order by [column] [asc/desc];</br>
   * asc为默认顺序，意思为升序，desc为降序。
   * 如果多列降序，需要每一列指定desc。
   * LIMIT必须在ORDER之后出现。

#### 四、数据过滤
***
1. 过滤使用where子句：select * from [table] where [condition];
2. 使用AND表达"&"：select * from [table] where [condition1] And [condition2];
3. 使用OR表达"|"：select * from [table] where [condition1] or [condition2];
4. 关联词的优先级：()>And>OR
5. IN操作符：select * from [table] where [column] in ([value1],[value2]);
6. NOT操作符：MySQL支持使用NOT对IN、BETWEEN和 EXISTS子句取反，这与多数其他DBMS允许使用NOT对各种条件 取反有很大的差别。

#### 五、使用通配符进行过滤
*** 
1. 使用LIKE操作符进行过滤：select * from [table] where [column] like %[value]%;
2. %通配符匹配一个到多个字符，_通配符匹配一个字符

#### 六、正则表达式进行搜索
***
1. 使用REGEXP进行正则表达式搜索：select * from [table] where [column] regexp [正则表达式];
2. 基本字符使用：regexp 'str' == like '%str%'
3. 进行OR匹配：regexp 'value1|value2'
4. 进行几个字符之一匹配：regexp '[123]str' = regexp '[1|2|3]str'
5. 匹配范围：regexp '[0-5][a-z]'
6. 特殊字符需要 \\进行转义
7. 匹配字符类：
   ````
   * [:alnum:] 任意字母和数字（同[a-zA-Z0-9]）
   * [:alpha:] 任意字符（同[a-zA-Z]）
   * [:blank:] 空格和制表（同[\\t]）
   * [:cntrl:] ASCII控制字符（ASCII 0到31和127）
   * [:digit:] 任意数字（同[0-9]）
   * [:graph:] 与[:print:]相同，但不包括空格
   * [:lower:] 任意小写字母（同[a-z]）
   * [:print:] 任意可打印字符
   * [:punct:] 既不在[:alnum:]又不在[:cntrl:]中的任意字符
   * [:space:] 包括空格在内的任意空白字符（同[\\f\\n\\r\\t\\v]）
   * [:upper:] 任意大写字母（同[A-Z]）
   * [:xdigit:] 任意十六进制数字（同[a-fA-F0-9]）
   ````
8. 匹配多个实例： regexp '[:alnum:]{3}'
   ````
   * \* 0个或多个匹配
   * \+ 1个或多个匹配（等于{1,}）
   * ? 0个或1个匹配（等于{0,1}）
   * {n} 指定数目的匹配
   * {n,} 不少于指定数目的匹配
   * {n,m} 匹配数目的范围（m不超过255）
    ````
9. 定位符
   ````
   * ^ 文本的开始
   * $ 文本的结尾
   * [[:<:]] 词的开始
   * [[:>:]] 词的结尾
   ````
#### 七、创建计算字段
*** 
1. 使用Concat拼接字段:select concat([column1],'和',[column2]) from [table];
2. 使用As进行字段的别名展示：select concat([column1],'和',[column2]) AS [newName] from [table];
3. 执行计算：select [column1]*[column2] AS [newName] from [table];</br> 
   (1)\+ 加  (2)- 减 (3)\* 乘 (4)/ 除
   
#### 八、函数
*** 
1. 字符处理函数：
````
   * Left() 返回串左边的字符
   * Length() 返回串的长度
   * Locate() 找出串的一个子串
   * Lower() 将串转换为小写
   * LTrim() 去掉串左边的空格
   * Right() 返回串右边的字符
   * RTrim() 去掉串右边的空格
   * Soundex() 返回串的SOUNDEX值
   * SubString() 返回子串的字符 
   * Upper() 将串转换为大写
  ````  
2. 日期处理函数：
````
    * AddDate() 增加一个日期（天、周等）
    * AddTime() 增加一个时间（时、分等）
    * CurDate() 返回当前日期
    * CurTime() 返回当前时间
    * Date() 返回日期时间的日期部分
    * DateDiff() 计算两个日期之差
    * Date_Add() 高度灵活的日期运算函数
    * Date_Format() 返回一个格式化的日期或时间串
    * Day() 返回一个日期的天数部分
    * DayOfWeek() 对于一个日期，返回对应的星期几
    * Hour() 返回一个时间的小时部分
    * Minute() 返回一个时间的分钟部分
    * Month() 返回一个日期的月份部分
    * Now() 返回当前日期和时间
    * Second() 返回一个时间的秒部分
    * Time() 返回一个日期时间的时间部分
    * Year() 返回一个日期的年份部分
  ````  
3. 数值处理函数：
````
   * Abs() 返回一个数的绝对值
   * Cos() 返回一个角度的余弦
   * Exp() 返回一个数的指数值
   * Mod() 返回除操作的余数
   * Pi() 返回圆周率
   * Rand() 返回一个随机数
   * Sin() 返回一个角度的正弦
   * Sqrt() 返回一个数的平方根
   * Tan() 返回一个角度的正切
````

#### 九、汇总数据
1. 聚集函数
````
//忽略为null的行，只限制于单列
AVG() 返回某列的平均值 
//count([column])忽略null,count(*)不忽略
COUNT() 返回某列的行数 
MAX() 返回某列的最大值
MIN() 返回某列的最小值
//可以进行多列的计算sum([column1*column2])
SUM() 返回某列值之和
````

2. 聚集不同值：使用[聚集函数]\(distinct [column])

#### 十、数据分组
1. 分组使用group by子句：select [函数]|[column],[聚合函数] group by [函数]|[column];
2. 分组汇总WITH ROLLUP：select [函数]|[column],[聚合函数] group by [函数]|[column] WITH ROLLUP;
3. 分组过滤HAVING：select [函数]|[column],[聚合函数] group by [函数]|[column] having [函数]|[column],[聚合函数]的表达式;
4. 分组与排序：select [函数]|[column],[聚合函数] group by [函数]|[column] having [函数]|[column],[聚合函数]的表达式 order by [函数]|[column],[聚合函数];
5. select子句顺序：
   ````
   SELECT 要返回的列或表达式 是
   FROM 从中检索数据的表 仅在从表选择数据时使用
   WHERE 行级过滤 否
   GROUP BY 分组说明 仅在按组计算聚集时使用
   HAVING 组级过滤 否
   ORDER BY 输出排序顺序 否
   LIMIT 要检索的行数 否
   ````
   
#### 十一、子查询
1. 作为条件：select * from [table] where [column] in (selet *|[column] from ......);
2. 作为字段: select [column],(select * from ...) As [newName] from [table];

#### 十二、连接查询
1. 多个子表进行连接：select a.*,b.* from [table1],[table2] where [condition];</br>
这种方式使用的table1的每一行去匹配table2的每一行，没有where条件结果就是笛卡尔积table1.size*table2.size;
2.内连接使用另一种方式连接与第一种是一样：select a.*,b.* from table1 inner join table2 on [condition] where [condition];
3. 自然连接使用的是相同的字段进行连接：select a.* ,b.* from [table1] natural join [table2]
4. 左外连接和右外连接，会以左表或右表为基础进行连接，保证左表或右边的数值：select a.* ,b.* from [table1] left|right join [table2] on [conditions]
5. mysql不支持全外连接 full outter join

#### 十三、组合查询
1. 使用union对多个语句进行组合：select union select 
2. union的规则：
   * 必须包含两条以上的select语句，他们之间使用union
   * select句子必须包含相同的列、表达式、聚合函数
   * 列之间必须符合类型要求，至少能隐式转换
3. union会去掉重复的行，union all则不会
4. 可以在最后一个select语句后进行order by 进行排序



