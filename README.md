# 如何使用idea使用github管理项目

## 一、git的基本配置
1. https://git-scm.com/download/win进入该页面下载PortableGit
2. 安装完成之后使用git-bash.exe，生成ssh密钥对
3. 设置提交时的密码
4. 完成之后在git上进行简历仓库
5. 在idea中添加git.exe
6. 在本地添加使用如下命令进行初始化

```git
git init

git add src

git commit -m  "first commit"

git remote add origin https://github.com/mw138/TestGit.git

git push  -u origin master
```
