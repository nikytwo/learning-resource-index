

# Oracle 备份工具 RMAN 使用教程

## 备份

	// todo


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
