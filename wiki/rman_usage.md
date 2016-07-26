---
layout: post
title: "Oracle 备份工具 RMAN 使用教程"
description: "Oracle 备份工具 RMAN 使用教程"
category: wiki
tags: [oracle,rman]
---


## 相关知识

使用 RMAN 进行备份，数据库需要在归档模式下。

### 重做日志

重做日志记录了数据库的所有变更信息，如：INSERT,UPDATE,DELETE,CREATE,ALTER 或者 DROP 等。

每个数据库须最少有两个重做日志组，默认自动创建3个。

每个重做日志均有大小限制，循环使用。

### 归档日志

将重做日志归档保存后即为归档日志。

### 归档模式/非归档模式

数据库可以运行在归档模式或非归档模式下。

归档模式，又分自动存档和手工存档，默认是自动的。

* 非归档模式：若所有重做日志均写满，将自动覆盖。

* 归档模式（自动存档）：若重做日志写满，自动将重做日志保存至指定的归档位置（10g后默认为闪回区，见下）。

* 归档模式（手动存档）：若重做日志写满，数据库将阻止数据写入，提示相关错误，须手工归档后才能继续提供服务。


### 闪回区

10g 后增加了闪回区(flash recovery area)来存放归档日志。

默认路径为数据库路径下的 flash_recovery_area 目录。

默认大小为 2G。

**注意：若超出了大小，也会提示错误，阻止数据写入**。
须进行备份后删除（使用 RMAN）或扩大闪回区大小。数据库才能继续提供服务。


综上，服务器若运行在归档模式下，则必须进行归档日志备份，
否则会因重做日志写满或归档日志满而导致数据库无法提供服务。


## 备份

### 脚本及说明

文件`backupLv0.bat`: 为 window 下的批处理文件，其调用 rman 脚本进行全库备份(0级)。

```shell
# 设置数据库sid
set oracle_sid=orcl
set y=%date:~0,4%
set m=%date:~5,2%
set d=%date:~8,2%
if "%time:~0,2%" lss "10" (set h=0%time:~1,1%) else (set h=%time:~0,2%)
set mi=%time:~3,2%
set s=%time:~6,2%
# 调用 rman 脚本
rman target / log='%y%%m%%d%_%h%%mi%%s%.log' cmdfile='backupWithRmanLv0.rman'
```

文件`backupWithRmanLv0.rman`: 为 rman 备份脚本

```rman
run{
    # 备份集保留策略，保留14天内的备份
    configure retention policy to recovery window of 14 days;

    # 设置自动备份控制文件及路径
	configure controlfile autobackup on;
	configure controlfile autobackup format for device type disk to "F:/OracleBackup/rman/ctrl_%F.bak";

    # 设置备份集通道和路径，表空间全备份（0级），同时备份归档日志，完成后删除已备份的归档日志
	allocate channel ch1 device type disk format "F:/OracleBackup/rman/%d_%T_%t.bak";
	backup incremental level=0 tablespace ELBONLINE_DATA skip inaccessible
	    plus archivelog
		delete all input;
	release channel ch1;
}
allocate channel for maintenance device type disk;
crosscheck backupset;
delete noprompt obsolete;
```

以上脚本为0级备份脚本，1级备份请修改 rman 脚本中的相关参数(`level=*`)。
关于备份级别，请参考下文。

表空间、数据文件备份与全库备份的差别：**非全库备份时，备份的保留策略不会应用于归档日志文件备份集**。

因为归档日志文件里包含其他未备份的数据。

### 备份时间

备份时间由系统执行计划进行设置。（注意全局与差异备份应有时间差，否则同时执行会影响备份执行时间和数据库性能）


### 关于增量备份

Oracle 的增量备份是通过备份级别实现的。

level 0 级是对数据库的全库备份，增量备份必须从 0 级开始。然后才有 1，2，3，4 级。

rman 增量备份分为差异增量备份(默认)和累积增量备份。

**差异增量备份**示例：

我们在星期天执行0级差异增量备份操作，这个备份操作会备份整个数据库。
根据这个0级备份，我们在星期一执行1级差异增量备份操作。该备份操作将备份自周日0级备份以来所有发生变化的数据块。
在周二时1级增量备份将备份所有自周一1级备份以来发生变化的数据块。
如果要执行恢复操作，就需要星期一、星期二生成的备份以及星期天生成的基本备份。

更多相关信息查看文章最后链接。


## 恢复

### 恢复步骤

1.设置数据库状态为：mount 或 open。整库恢复需要在 mount 状态下，表控件或数据文件恢复可以在 open 状态下。

2.执行恢复操作。分完全和不完全，区别是，完全恢复是应用所有的重做日志，不完全则可以指定部分重做日志来恢复到指定的时间点。

3.打开数据库。不完全恢复需额外附加 resetlogs


### 示例

#### 整库恢复（完全介质恢复）

**以下命令无特殊说明，均在 RMAN 提示符下操作**，进入 RMAN 提示符：`rman target user/password@sid`


```
// 启动数据库至 mount 状态
startup mount;
// 恢复
restore database;
recover database delete archivelogs skip tablespace temp;
// 打开数据库
alter database open;
```

其中：

delete archivelogs 表示删除恢复过程中产生的归档日志文件。

skip tablespace temp 标识跳过临时表空间

#### 表空间和数据文件恢复

表空间和数据库文件恢复，可以在 open 状态下进行。

```
// 表空间
sql 'alter tablespace *** offline immediate';
restore tablespace ***;
recover tablespace ***;
sql 'alter tablespace ** online';
// 数据文件，其中 n 为数据文件序号或详细路径
sql 'alter database datafile n offline';
restore datafile n;
recover datafile n;
sql 'alter database datafile n online';

```

若，表空间或数据库文件所在的磁盘出现故障，需要切换至其他路径时(控制文件没有损坏)

```
// 表空间，其中 datafile 1 为原表空间对应的数据文件
run {
	sql 'alter tablespace *** offline immediate';
	set newname for datafile 1 to 'f:\path\***.dbf'
	restore tablespace ***;
	swith tablespace ***;
	recover tablespace ***;
	sql 'alter tablespace ** online';
}
// 数据文件，其中 n 为数据文件序号
run {
	sql 'alter database datafile n offline';
	set newname for datafile n to 'f:\path\***.dbf'
	restore datafile n;
	swith datafile n;
	recover datafile n;
	sql 'alter database datafile n online';
}

```

#### 控制文件的恢复

若控制文件也损坏了，必须先恢复控制文件，因为控制文件记录了备份的信息。

```
// 需先设置 DBID ，如何查询目标数据库的 DBID ，请自行 google
set dbid=***********;
// 恢复控制文件，数据库需在 nomount 状态下
startup nomount;
// 设置控制文件备份所在的路径及名称
set controlfile autobackup format for device type disk to 'f:\OracleBackup\rman\ctrl_%F.bak';
// 恢复至默认路径
restore controlfile from autobackup;
// 或恢复至指定路径
restore controlfile to 'f:\path\control01.ctl' from autobackup;
// 下面这句是应用控制文件备份以后的重做日志，即恢复备份以后的数据库
alter database mount;
recover database;
alter database open resetlogs;
```

又或者

```
set dbid=***********;
startup nomount;
// 直接从指定的备份文件恢复
restore controlfile to 'f:\path\control01.ctl' from 'f:\OracleBackup\rman\ctrl_****.bak';
```

#### 不完全恢复

```
run {
	startup force mount;
	// 以下3选1
	// 恢复到指定时间
	set until time='yyyy-MM-DD HH:Mi:SS';
	// 恢复到指定 SCN
	set until scn=123456;
	// 恢复到指定日志序列号
	set until sequence=10;
	restore database;
	recover database;
	sql 'alter database open resetlogs';
}
```


#### 恢复到其他服务器上

须保证待恢复的目标端目录结构与源端的一致。

须保证数据库的 SID 一致。

须先恢复 SPFILE 文件和控制文件。

其他操作同上。


***

## 参考

* 《涂抹Oracle》
* [Oracle RMAN 使用详解](http://www.linuxidc.com/Linux/2011-04/35279.htm)
* [Oracle数据库备份与恢复 - 增量备份](http://blog.csdn.net/pan_tian/article/details/46780929)
