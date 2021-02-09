###新建JAVA_HOME 变量
    变量名：JAVA_HOME
    变量值：电脑上JDK安装的绝对路径［C:\Program Files\Java\jdk1.8.0_131］

###新建/修改 CLASSPATH 变量
    变量名：CLASSPATH
    变量值：.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;

###修改Path 变量
    %JAVA_HOME%\bin
    %JAVA_HOME%\jre\bin


###解压版数据库安装 gGyK2tSucH,g
1、mysqld --initialize --console </br>
2、mysqld install </br>
3、net start mysql </br>
4、mysql -u root -p </br>
5、ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';</br>
    