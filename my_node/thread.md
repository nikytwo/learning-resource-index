
产生死锁的4个必备条件：
1. 互斥：改资源任意一个时刻只能由一个线程占用。
2. 请求与保持：一个线程因请求资源阻塞时，对已获得的资源保持不放。
3. 不剥夺：线程已获得的资源在未使用完之前不能被其他线程强行剥夺。
4. 循环等待：若干线程之间形成一种头尾相接的循环等待资源关系。

并发编程的三个重要特性：
1. 原子性
2. 可见性
3. 有序性（指令重排）

多线程入门：https://www.cnblogs.com/Qian123/p/5670304.html
深入浅出Java多线程：http://redspider.group:4000/

JUC 中类原理：volatile + CAS(campare and swap) 和 native 方法来保证原子性。
CAS 深入理解自旋锁: https://blog.csdn.net/qq_34337272/article/details/81252853
synchronized vs CAS
锁的升级(锁可以升级, 但不能降级)：
1. 偏向锁：同步块只有一个线程访问；锁对象(MarkWord)保存当前线程ID及偏向标志位；Java6默认开启
2. 轻量级锁：多个线程通过CAS竞争锁(写MarkWord)；线程竞争不阻塞；自旋会消耗CPU；
3. 重量级锁：多个线程CAS失败后升级为重量级；线程堵塞；上下文切换；

AQS(AbstractQueuedSynchronizer): 实现同步器的基础
原理与设计模式:
https://www.cnblogs.com/waterystone/p/4920797.html
https://www.cnblogs.com/chengxiao/archive/2017/07/24/7141160.html
LockSupport: https://www.jianshu.com/p/ceb8870ef2c5
Unsafe: https://www.cnblogs.com/pkufork/p/java_unsafe.html
常见同步器：
* ReentrantLock
* Mutex
* Semaphore
* CountDownLatch
* CyclicBarrier
* ReentrantReadWriteLock
* FutureTask


jdk1.8 CompletableFuture<T> 


ThreadPoolExecutor 
原理:http://www.throwable.club/2019/07/15/java-concurrency-thread-pool-executor
Java线程池实现原理及其在美团业务中的实践:https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
特色：使用1个AtomicInteger同时保存线程池状态和线程数
线程池状态：
1. RUNNING
2. SHUTDOWN
3. STOP
4. TIDYING
5. TERMINATED

Runable vs Callable
