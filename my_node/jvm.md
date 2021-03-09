


垃圾收集器：
* Serial:复制,单线程,新生代
* ParNew:复制,多线程,新生代
* Parallel Scavenge:复制,多线程,高吞吐,新生代,jdk8默认
* Serial Old:标记-整理,单线程
* Parallel Old:标记-整理,多线程,高吞吐,jdk8默认
* CMS(ConcMarkSweep):标记-清除,回收时间短；过程（初始标识-》并发标记-》重新标记-》并发清理）,Incremental Update算法
* G1:分区（region）,高吞吐,停顿时间可控,jdk9默认,mixed gc,SATB算法

类加载：
