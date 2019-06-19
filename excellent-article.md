
# 优秀网文


## 1. 后端

### 1.0. Other

* [分布式Unique ID的生成方法一览](http://calvin1978.blogcn.com/articles/uuid.html)
* [服务端开发那些事儿(七牛CEO许式伟)](http://mp.weixin.qq.com/s?__biz=MjM5NzAwNDI4Mg==&mid=400623317&idx=1&sn=5a9c5b75148dd308ff35eec6ae041cab)：锁的使用，模块化设计，服务器测试(工具)、可维护性。
* [关于分布式事务、两阶段提交协议、三阶提交协议](http://blog.jobbole.com/95632/)
* [学习笔记：The Log（我所读过的最好的一篇分布式技术文章）](http://blog.jobbole.com/88301/): **待研究**
* [分布式系统阅读清单](http://blog.jobbole.com/84575/): **待研究**
* [一个复杂系统的拆分改造实践](http://blog.jobbole.com/109409/): 各子系统的设计与调用（怀疑第三方，防备使用方，做好自己），SOP化，每一步可测试可回滚

### 1.1. 基础

* [ kerberos认证原理](http://blog.csdn.net/wulantian/article/details/42418231): 身份认证。**待整理**[敞开的地狱之门：Kerberos协议的滥用](http://www.freebuf.com/articles/system/45631.html)
* [日志：每个软件工程师都应该知道的有关实时数据的统一抽象](https://github.com/oldratlee/translations/tree/master/log-what-every-software-engineer-should-know-about-real-time-datas-unifying)
* [互联网新人如何入门和掌握 Java 开发](https://zhuanlan.zhihu.com/p/21371311?refer=notageek): 没看的看，看过的再看
* [服务端技术选型](http://xielong.me/2015/04/17/%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8A%80%E6%9C%AF%E9%80%89%E5%9E%8B/): 小米在用的技术.**待整理**
* [40个Java集合面试问题和答案](http://www.sanesee.com/article/40-java-collections-interview-questions-and-answers)
* [如何更好的使用 Java](http://blog.smoker.cc/translation/20160511.html): **待整理**
* [HashMap深度分析](http://www.jianshu.com/p/8b372f3a195d)
* [slf4j、jcl、jul、log4j1、log4j2、logback大总结](http://my.oschina.net/pingpangkuangmo/blog/410224)
* [Spring 核心框架体系结构](http://www.importnew.com/22859.html)

### 1.2. 架构

* [谈谈互联网后端基础设施](http://www.rowkey.me/blog/2016/08/27/server-basic-tech-stack/)
* [从技术细节看美团的架构](http://mp.weixin.qq.com/s?__biz=MzA5Nzc4OTA1Mw==&mid=408215395&idx=1&sn=cc49792ef0948a140c37d99306363774&scene=0#wechat_redirect)([视频地址](http://www.infoq.com/cn/presentations/see-meituan-architecture-from-technical-details#rd)): 构架是变化的，公共组件的提炼（以便专注业务），业务构架的优化（标准化，自动化），用户精准运营。
* [每个架构师都应该研究下康威定律](http://mp.weixin.qq.com/s?__biz=MzA5Nzc4OTA1Mw==&mid=408286995&idx=1&sn=1634698023c48b754d42af69cee2ab32&scene=0#wechat_redirect): 利益相关者，迭代、演化、反馈，与组织文化的关系。
* [大型网站架构技术一览](http://www.hollischuang.com/archives/1132)
* [大型网站架构演化历程](http://www.hollischuang.com/archives/728)
* [电商前端交易型系统设计原则](http://jinnianshilongnian.iteye.com/blog/2312284):使用状态机处理订单状态，使用日志(redis)+task 进行流量缓冲、等幂（防重）等。
* [分布式系统设计的求生之路](http://wetest.qq.com/lab/view/105.html): 消息中间件的通信模式（req/rep,pub/sub,push/pull,dealer/router）、自定义消息协议。
* [设计消息中间件时我关心什么?](http://mp.weixin.qq.com/s?__biz=MzAwMDU1MTE1OQ==&mid=2653547492&idx=1&sn=be78ff0389fd5a8d8991abc567a191e2&scene=0#wechat_redirect): 数据一致性（事务+task、两段提交），隔离、治理。
* [消息队列设计精要](http://tech.meituan.com/mq-design.html): 应用场景：解耦、数据一致性（日志+task）、广播、错峰与流控，功能：**待整理**
* [推荐几个自己写的Java后端相关的范例项目](http://wosyingjun.iteye.com/blog/2312553): Spring+SpringMVC+Mybatis+页面分离+nginx负载均衡+tomcat集群+基于dubbo+zookeeper
* [跟着 Github 学习 Restful HTTP API 设计](http://cizixs.com/2016/12/12/restful-api-design-guide)

### 1.3. 网络

* [简明网络I/O模型---同步异步阻塞非阻塞之惑](http://www.jianshu.com/p/55eb83d60ab1): 5种I/O模型比较图

### 1.4. DB

* [数据库软件架构设计些什么](http://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=400465735&idx=1&sn=8d7067de4cc8f73ea5558f07e0a9340e&scene=0#wechat_redirect): 由单库到集群，简单实现哈希一致的方法。
* [如果有人问你数据库的原理，叫他看这篇文章](http://blog.jobbole.com/100349/): **未看完**
* [20 个数据库设计最佳实践](http://www.oschina.net/question/28_37512): **待研究**
* 

### 1.5. 移动开发

* [从零开始搭建架构实施Android项目](http://www.cnblogs.com/lao-liang/p/5122425.html): App工程结构，轮子（第三方库）
* [阅读 Android 源码的一些姿势](http://zhuanlan.zhihu.com/kaede/20564614): Andriod 核心类,第三方开源项目
* [移动网络下的性能优化之省电篇](https://blog.wilddog.com/?p=948): 合并请求、预取数据、避免轮询、重连机制（心跳）
* [Android 微信智能心跳方案](http://mp.weixin.qq.com/s/ghnmC8709DvnhieQhkLJpA)

## 2. 前端

### 2.0. Other

* [移动H5前端性能优化指南](https://isux.tencent.com/h5-performance.html): 加载优化，图片优化，脚本优化，css优化，渲染优化
* [浏览器缓存知识小结及应用](http://www.cnblogs.com/lyzg/p/5125934.html): 强缓存，协商缓存
* [动态加载js文件的正确姿势](https://github.com/someus/how-to-load-dynamic-script): **待研究**
* [做一个高效的前端攻程狮之经验总结](http://www.imooc.com/article/1974): 文章最后的东西**待研究**

### 2.1. React

* [如何学习React](https://github.com/petehunt/react-howto/blob/master/README-zh.md)
* [一本浓缩的 React 煮书](https://blog.oyanglul.us/javascript/react-cookbook-mini.html)
* [React的设计哲学-简单之美](http://www.infoq.com/cn/articles/react-art-of-simplity)


## 3. 工程

* [12要素](https://12factor.net/zh_cn/): 网络应用程序/微服务（或SaaS）开发方法论。（[12-Factor(英文)](https://12factor.net/),[微服务12要素](http://www.361way.com/12factor/5608.html)）
* [语义化版本](http://semver.org/lang/zh-CN/): 版本号的规范：x.y.z-。
* [Maven最佳实践：版本管理](http://juvenshun.iteye.com/blog/376422): SNAPSHOT 是什么。
* [Commit message 和 Change log 编写指南](http://www.ruanyifeng.com/blog/2016/01/commit_message_change_log.html): 规范 commit message，自动生成 Change log
* [优秀的Java程序测试是什么样的？](http://www.codeceo.com/article/good-java-test.html): 表达清晰
* [前端工程相关问题](https://github.com/fouber/blog)： 系列文章**待研究**：开发体系，集成，部署，测试，性能优化等。


## 4. AI

* [神经网络浅讲：从神经元到深度学习](http://blog.jobbole.com/109748/)


## 0. Other

* [学习学习再学习前言](http://xiaolai.li/post/106391858120/%E5%AD%A6%E4%B9%A0%E5%AD%A6%E4%B9%A0%E5%86%8D%E5%AD%A6%E4%B9%A0%E5%89%8D%E8%A8%80): 摆脱负递归（急于求成），专家方式做事，最少够用知识，不懂也要读完，教是最好的学习，马太效应
* [程序员如何优雅度过一生的15个建议](http://www.jianshu.com/p/e15c1435675f): 启动项目，采取主动，抓住机遇，敢于冒险
* [程序员的知识管理](http://blog.xiaohansong.com/2016/01/16/kownledge-Management/): 知识积累(笔记)->碎片整理(wiki)->思考加工(博客)
* [作为一个软件工程师，在接下来的5-10年内应该掌握的技术](http://webres.wang/the-best-skill-set-to-have-for-the-next-5-10-years/): 基本原理(数据库、网络技术、编译器、数据结构、算法、操作系统),分析能力,问题分解(大化小)，工作优先级，团队合作，专注
* [做技术，应该先有深度再谈广度](http://mp.weixin.qq.com/s?__biz=MzA5Nzc4OTA1Mw==&mid=408703236&idx=1&sn=3f78abcca906d5edee06ff85b2d22416&scene=0#wechat_redirect): 架构的本质（抽象能力、分类能力、算法/性能能力）；跨界思维（开放、创新、发散）；架构师的软技能树 = 产品经理的软技能树 => 让正确的事情相继发生 => 持续地解决问题；主动学习和总结，承担责任和接受挑战，客观认识自己和学习他人；高效获取信息(活在当下、高信息熵和有传递价值、选择榜样并持续学习)。
* [在高科技行业，只有失败者采用“业界最佳实践”](http://www.ituring.com.cn/tupubarticle/8160): 
* [程序员，你的安全感呢？](http://www.linkedme.in/2015/10/12/how-developer-feel-safe): 拖延症,制作平衡表(你知道/你能影响/你不知道/你不能影响)，匹配外部期待和个人能力
* [技术的执念](http://icodeit.org/2016/02/pitfall-of-technology/): 知识框架,过载处理（做减法、深入、优先级）
* [最容易被忽略的三种学习方式](http://www.gtdlife.com/2016/4376/three-study-way/): 模仿、心态、信任
* [论一线技术领导者的基本素养](http://mp.weixin.qq.com/s?__biz=MzA4ODgwNjk1MQ==&mid=2653788410&idx=1&sn=2c281e0502719cec78085e1c285f3e72&scene=0#wechat_redirect): 利用有限的了解做出正确的决定；利用有限的工程师资源高质量完成关键项目（调动工程师潜力，帮助工程师成长）。
* [一家初创公司的 CTO 应当做什么？](http://www.zcfy.cc/article/what-does-a-startup-cto-actually-do-1146.html): 技术选型、全局把控、提供选择、8/2原则（）、培养人才、开发方法论。
* [程序员技能图谱](https://github.com/TeamStuQ/skill-map)

