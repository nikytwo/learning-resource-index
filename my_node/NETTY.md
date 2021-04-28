
5大网络I/O模型

NETTY： 
* I/O多路复用
* AIO 的支持
* 主要接口和类

主要概念/类：
* Channel: 通道/频道，一般代表一个连接，每个请求都会对应到具体的一个Channel
* ChannelPipeline: 管道，每一个Channel有且只有一个，里面可以有各种handler
* handler: 处理器，用于处理接收的消息或事件，具体的业务逻辑在这里实现
* EvenLoopGroup: 负责接收请求，并以事件的形式分发出去，I/O线程池
* Bootstrap/ServerBootStrap: 启动辅助类
* ChannelInitializer: Channel的初始化器
* ChannelFuture: 执行结果，可以通过添加监听器，执行对应操作
* ByteBuf: 字节缓冲对象，用于操作handler中接收到的字节

客户端步骤：
a. 创建handler类
    1. 添加Shrable注解（代表线程安全）
    2. **继承SimpleChannelInboundHandler类**
    3. **重写channelRead0方法，处理接收到的消息**
    4. 重写exceptionCaught方法，处理异常
b. 创建启动类
    1. 创建EvenLoopGrop实例
    2. **创建Bootstrap实例**
    3. **初始化Bootstrap**
    4. **连接服务器**，生成ChannelFuture对象
    5. 关闭连接，释放资源

服务端步骤：
a. 创建handler类
    1. 添加Shrable注解（代表线程安全）
    2. **继承ChannelInboundHandlerAdapter类**
    3. **重写channelRead方法，处理接收到的消息**
    4. 重写exceptionCaught方法，处理异常
b. 创建启动类
    1. 创建EvenLoopGrop实例
    2. **创建ServerBootstrap实例**
    3. **初始化ServerBootstrap**
    4. **绑定端口**，生成ChannelFuture对象
    5. 关闭连接，释放资源

接口/类详细解释：
ChannelInboundHandleerAdapter下的方法
* handlerAdded: ChannelGroup 增加了 handler
* handlerRemoved: ChanelGroup 移除了 handler
* channelActive: 通道连接建立激活（在Channel建立时执行一次）
* channelInactive: 通道连接断开（在Channel失效时执行一次）


超时和连接空闲：
首先定义何为超时。
我们常说的超时，一般对于请求方是，当在指定时间内未收到服务方的响应，即对应下面的读超时；而对于服务方则是，当在指定时间内未完成数据发送，即对应下面的写超时。
其他类型的超时：
* 在读取数据时，超过指定时间？(参考 channelFuture 的监听器)
* 连接超时？

Netty 提供了下列几种超时。
* 空闲超时(包括：读空闲超时、写空闲超时、空闲超时)：
    - 读空闲超时：在指定时间内没有收到消息（inbound）
    - 写空闲超时：在指定时间内没有发送消息（outbound）
    - 空闲超时：在指定时间内没有发送和接收到消息
* 读超时(=读空闲超时)：在指定时间内没有收到消息（inbound）
* 写超时(区别空闲写超时)：在指定时间内没有完成消息的发送（outbound）

具体接口与类：
* IdleStateHandler(空闲状态处理器)，对应空闲超时的处理。
其中它的三个参数readIdleTime、writeIdleTime和allIdleTime对应读空闲时间、写空闲时间、空闲时间，当超过这些时间时会触发IdleStateEvent事件。
我们可以通过重写接口ChannelInboundHandler的方法userEvenTriggered来处理这个事件。 
* ReadTimeoutHandler(读超时处理器，继承IdleStateHandler)，对应上面的读超时。当超时发生时会抛出ReadTimeoutException，我们可以通过重写接口ChannelInboundHandler的excptionCaught方法来处理这个异常。
* WriteTimeoutHandler(写超时处理器)，对应上面的写超时。当超时发生时会抛出WriteTimeoutException，我们可以通过重写接口ChannelInboundHandler的excptionCaught方法来处理这个异常。

ChannelFuture 的 listener:
    待整理

编解码
* StringEncoder/StringDecoder
* FixedLengthFrameDecoder
* LineBasedFrameDecoder
* DelimiterBasedFrameDecoder
* LengthFieldBasedFrameDecoder

常见序列化
* MessagePack
* Protobuf
* Thrift
* JBoss Marshalling

异步与定时任务
* EventLoop/EventExecutorGroup
    - execute
    - schedule(只执行一次)
    - scheduleAtFixdeRate(会顺延)
    - scheduleWithFixedDelay(不会顺延)
* 自定义线程池: 耗时的业务任务应使用这个，避免阻塞EventLoop线程

ByteBuf
优点：
* 区分了读写index，不需要flip()操作
* 提供Composite缓冲
* 自动扩容
* 更快的响应速度
分类1：
* 堆内(Heap): 快速分配和释放，但会有内存复制
* 堆外(Direct): 不占用堆空间，但分配释放复杂
* 复合(Composite)
分类2：
* 池化(Pooled***): 提高内存使用率，降低GC频率
* 非池化(Unpooled***): 
操作：
* get/set***操作不会移动读写index，且不会触发扩容。
* read/write***会移动index。
* 0 <= readerIndex <=writerIndex <= capacity 永远成立
* discardReadBytes会清空已读字节并将readerIndex置零，但涉及的内存复制，影响性能，一般在需要马上释放内存的时候使用收益才大。
* clear会将读写index置零，比discardReadBytes成本更低。
* duplicate/slice/readOnly/order返回的是视图，而copy返回的是副本。
* nioBuffer: 返回ByteBuf的ByteBuffer视图，但无法感知扩容操作。
主要的类：
* AbstractByteBuf
* ResourceLeakDetector
*

Bootstrap

Channel

ChannelPipeline

ChannelHandler

