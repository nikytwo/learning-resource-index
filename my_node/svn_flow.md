
# svn 工作流及实践

## 基础

* [语义化版本](http://semver.org/lang/zh-CN/): 如何定义项目版本号

* 保证系统有合理边界，即：
	- 可单独演进
	- 可单独测试
	- 可单独部署
	- ......

* 每个项目下需包含以下3个目录
	- trunk : 主干（代码是稳定的，不负担开发）
	- branches : 开发分支（功能，bugfix）
	- tags : 稳定版（只读），用于发布 。


## 流程

svn分支方案建议

![svn分支方案建议][svn_work_flow]

本方案 svn 目录结构：


```
root
|-- trunk
|-- branches
|   |-- v1.0-dev
|   |-- v1.0-stage
|   |-- v2.1-dev
|   |-- v2.1-stage
|   |-- v2.1.1-stage
|   `-- ...
`-- tags
    |-- v1.0.0
    |-- v1.1.0
    |-- v1.1.1
    |-- v2.0.0
    `-- ...
```

其中：

trunk 主干是代码稳定的。每次上线时都是从 trunk 上创建一个对应版本号的 tag 分支。

branchs 上有两种分支 dev 和 stage ，均从同一稳定的 trunk 上创建。
其中 dev 用于代码开发，开发完成后合并至 stage 分支并测试，最后当 stage 测试稳定了，再合并回 trunk 主干。


## 具体操作（java）

### 功能开发(项目迭代)

每增加一个（大的）功能或迭代

1. 从最新的稳定的 trunk 建立两个 branche 分支，分别为开发分支（命名格式：v<主版本号>.<次版本号>-dev）和 stage 分支（命名格式：v<主版本号>.<次版本号>-stage）。
2. 在开发分支上进行开发并测试后提交代码至 svn。
3. 当该分支的所有功能点均完成后，将该分支的所有提交合并至 stage 分支上。
4. 对 stage 分支上的功能进行测试。测试不通过的返回步骤 2，通过的跳至步骤 5。
5. 当 stage 分支上的功能达到稳定状态时，将其合并至 trunk 主干进行上线操作：
	1. 将程序（pom.xml）中的版本号`<主版本号>.<次版本号>.<修订号>-SNAPSHOT`更改为`<主版本号>.<次版本号>.<修订号>`，并提交 svn。
	2. 打标签（tag）：从 trunk 中建立一个 tag 分支。(命名格式：v<主版本号>.<次版本号>.<修订号>)
	3. 程序（pom.xml）中的版本号的次版本号（或修订号）+1，并在版本号后添加`-SNAPSHOT`，然后提交 svn。
	4. 从 svn 中检出刚刚的 tag 分支，对该分支代码打包、上线。

注：上线操作可以考虑使用 `maven-release-plugin` 插件


### 修复缺陷(bug fix)

缺陷的修复分两种情况：

#### 对未上线的版本进行修复

 走功能开发流程

#### 对已经上线的版本进行修复（紧急修复）

流程如下：

1. 在 dev 分支上进行开发，当所有 bug 修复完成后，提交至 svn。
2. 从最新的稳定的 trunk 建立一个 branche 分支，用于测试此次修复的 stage 分支（命名格式：v<主版本号>.<次版本号>.<修订号>-stage）。
3. 将提交（仅修复的代码）合并进此 stage 分支，并对其进行测试。测试不通过的返回步骤1，通过的跳至步骤 4.
4. 当 stage 分支上的功能达到稳定状态时，将其合并至 trunk 主干进行上线操作：
	1. 将程序（pom.xml）中的版本号的修订号+1，并提交 svn。
	2. 打标签（tag）：从 trunk 中建立一个 tag 分支。(命名格式：v<主版本号>.<次版本号>.<修订号>)
	3. 从 svn 中检出刚刚的 tag 分支，对该分支代码打包、上线。


## 流程简化

世界上没有银弹，任何实际问题都要具体分析、解决。
每个项目、每个团队都有适合自己的流程。
可以根据自己项目和团队的特点相应简化开发流程。

### 简化点1

不必每次增加功能都重新创建 dev 和 stage 分支。

### 简化点2

紧急修复时可以不建立新的 stage 分支。
但必须保证原 stage 分支上的代码不影响修改的代码。

### 简化点3

可舍弃 stage 分支，直接在 trunk 上测试。
但这时必须清楚 trunk 上的代码是不稳定的，以及有合适的紧急修复方案。

### 关于标签

不建议取消打标签，标签可以清楚划分系统的各个版本，有利于维护。
版本相关知识参加下面链接。



## 其他

### git 工作流简介

分支模型：

![git-branch-model](http://www.xyula.com/assets/images/git-branch-model.png)

两个主分支：

* 产品分支： master
* 开发分支： develop

其他分支：

* 功能分支： feature/***
* 修复分支： hotfix/***
* 发布分支： release/***

关键操作：

* 每个功能均要建立一个功能分支，单独演进，完成后合并至 develp
* 修复分支必须从 master 中建立，完成后必须同时合并至 master 和 develop

具体参见：[一个成功的 Git 分支模型](http://blog.jobbole.com/81196/)


### git 与 svn 的一些差异

* 分支对于 git 来说成本非常低，而且建立快且方便，git 官方也鼓励多建立分支。但这并不适用与 svn。
* git 上的合并会将两个分支上的所有更改都进行合并；而在 svn 上可以只合并分支上的某次或多次提交。


***

## 参考

* [SVN版本控制推荐使用方法](http://blog.csdn.net/flyfish1986/article/details/47131879)
* [svn代码版本管理总结](http://blog.csdn.net/xiaomu_fireant/article/details/6195622)
* [语义化版本](http://semver.org/lang/zh-CN/)
* [Maven最佳实践：版本管理](http://juvenshun.iteye.com/blog/376422)
* [一个成功的 Git 分支模型](http://blog.jobbole.com/81196/)


[svn_work_flow]: http://www.xyula.com/assets/images/svn_work_flow.jpg


