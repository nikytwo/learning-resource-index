
隔离级别
1.read_uncommitted
2.read_committed
3.repeatable_read
4.serializable

事务传播行为：
1. require: 加入，没有创建
2. suports：加入，没有不创建
3. mandatory：加入，没有异常
4. require_new：不加入，另外创建
5. not_suported：不加入，也不创建
6. never：已有事务，抛异常
7. nested：有没有都创建新事务

执行计划
type列性能好坏：
> system : 系统表
> const ：命中 primary /unique 且比对值是常量
> eq_ref ：命中 primary /unique 非NULL索引
> ref ：命中非唯一性索引
> ref_or_null ：命中含NULL索引
> index_merge ：使用两个以上的索引
> unique_subquery ：子查询使用唯一索引
> index_subquery ：子查询使用非唯一索引
> range ：使用索引进行范围查询
> index ：遍历索引
> ALL
Extra列特例：
* using index：使用覆盖索引
* using where
* using temporary
* using filesort
* using join buffer：联表查询缓冲区
* Impossible where：where不太正确

索引

