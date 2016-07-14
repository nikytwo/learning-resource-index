
# 《深入解析Oracle.DBA入门进阶与诊断案例》-- 第 7 章（重做）


## 重做日志的作用与原理

概念：

* Redo Log Buffer
* LGWR
* 日志文件(Redo Log File)
* 日志组(至少两个，默认3个，循环使用)
* Log Switch
* 检查点(checkpoint)
* 归档模式(archivelog)


## 重做日志与锁（latch)

锁：

* Redo Copy Latch：复制日志到 Redo Log Buffer
* Redo Allocation Latch：分配内存空间

...

```sql
select * from v$latch;

select addr,latch#,child#,name,gets,immediate_gets,immediate_misses 
  from v$latch_children where name = 'redo writing'; 
  
  SELECT  substr(ln.name, 1, 20), gets, misses, immediate_gets, immediate_misses  
FROM v$latch l, v$latchname ln  
WHERE   ln.name in ('redo allocation', 'redo copy')  and ln.latch# = l.latch#;

select * from v$version where rownum <2; 

select name from v$event_name where upper(name) like '%STRAND%'; 

select addr,latch#,child#,name,gets,immediate_gets,immediate_misses 
  from v$latch_children where name = 'redo allocation'; 
```

## 重做日志内容

概念：

* 改变向量
* 重做记录：由一组改变向量组成
* 操作代码


## 重做日志的大小
  
```sql
 select a.name,b.value 
  from v$statname a,v$mystat b 
  where a.STATISTIC# = b.STATISTIC# and a.name = 'redo size'; 
  
  select * from v$mystat;
  select * from v$statname;
  
  select name,value 
  from v$sysstat where name='redo size';
  select startup_time from v$instance; 
  
 select trunc(COMPLETION_TIME),sum(Mb)/1024 DAY_GB from 
  (select name,COMPLETION_TIME,BLOCKS*BLOCK_SIZE/1024/1024 Mb from v$archived_log 
  where COMPLETION_TIME between trunc(sysdate) -2 and trunc(sysdate) -1) 
  group by trunc(COMPLETION_TIME) ;
  
  SELECT   TRUNC (completion_time), SUM (mb) / 1024 day_gb 
      FROM (SELECT NAME, completion_time, blocks * block_size / 1024 / 1024 mb 
              FROM v$archived_log) 
  GROUP BY TRUNC (completion_time); 
```

## 重做日志触发条件

* 每 3 秒
* 阈值
* 提交（commit）
* DBWn 写之前
  

## Redo Log Buffer 大小设置

 Redo Log Buffer 的写出是很频繁的，过大的 Log Buffer 是没有必要的。

当 Log Buffer Space 等待事件出现较为显著时，可以适当增大 Log Buffer 大小以减少竞争。

```sql
-- 查询 Log Buffer Space 事件
select event#,name from v$event_name where name='log buffer space'; 
-- 
show parameter log_buffer;
```

不是明显的性能问题，一般缺省设置(Max(512 KB , 128 KB * CPU_COUNT))是足够的。


## Log File Sync

commit 流程：

1. 用户进程向 LGWR 进程提交写请求；
2. LGWR 执行写出(日志)，用户进程处于 Log File Sync 等待；
3. LGWR 进程发出日志写动作；
4. LGWR 写完成；
5. LGWR 通知用户进程；
6. 用户进程标记提交完成。


## 重做日志状态

* current
* active
* inactive
* unused
  
```sql
  select name,value from v$sysstat 
  where name in ('redo size','redo wastage','redo blocks written');
```

## 重做日志块大小

512K

## 重做日志文件大小及调整

原则：把日志切换(Log Switch)的时间控制在 30 分钟左右。


## 热备产生的重做日志
  
```sql
-- 查询热备信息
select * from v$backup;
```

## 不生成重做日志

| 数据库模式     | 表模式     | 插入模式  | redo 生成
|----------------|------------|-----------|----------
| archive log    | logging    | append    | 是
| archive log    | logging    | no append | 是
| archive log    | nologging  | append    | 否
| archive log    | nologging  | no append | 是
| no archive log | logging    | append    | 否
| no archive log | logging    | no append | 是
| no archive log | nologging  | append    | 否
| no archive log | nologging  | no append | 是

  
```sql
-- 查询表模式
select table_name,logging 
from dba_tables where table_name='TEST'; 
```

## 数值在 Oracle 的内部储存
  
```sql
  select dump(0),dump(0,16) from dual; 
```

