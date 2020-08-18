

## if进阶

### 条件表达式的重要性

#### 糟糕的if语句

```js
if (!((score > 700) || ((income >=40000) && (income <= 100000) && (score > 500)) || (income > 100000)))
	console.log("R");
else
	console.log("A");
```

问题：

根据上面的判断条件，填写以下表格（“R”还是“A”）。

| 		 |  income > 100000  | 100000 >= income >= 40000 | 40000 > income  |
|--------|--------|--------------|--------|----------|
| score > 700  | A	  |   A    |  A   |
| 700 >= score >= 500  | A	  |   A    |  R  |
| 500 > score  | A	  |   R    |  R   |


#### 圈复杂度

大多数公司规定函数不能超过80行！为什么？

太长的函数过于复杂。如何计算复杂度？


 **圈复杂度**（Thomas McCabe 1975）

 定义：

 1. 从1开始，一直往下通过程序
 2. 一旦遇到以下关键字，或者其他同类词，加 1（if / while / for / and / or)
 3. 给 case 语句中的每一种情况加 1

示例：

```java
public void example() {
    if (a == b) {
		if (a1 == b1) {
			fiddle();
		} else if (a2 == b2) {
			fiddle();
		} else {
			fiddle();
		}
	} else if (c == d) {
		while (c == d) {
			fiddle();
		}
	} else if (e == f) {
		for (int n = 0; n < h; n++) {
			fiddle();
		}
	} else {
		switch (z) {
			case 1:
				fiddle();
				break;
			case 2:
				fiddle();
				break;
			case 3:
				fiddle();
				break;
			default:
				fiddle();
				break;
		}
	}
}
```

过去研究：圈复杂度大于 10 的方法存在很大的出错风险。

* 1-4 is low complexity,
* 5-7 indicates moderate complexity,
* 8-10 is high complexity,
* 11- very high complexity.



### 优化和消除条件表达式

#### 以卫语句取代嵌套条件

示例1

```js
function getPayAmount() {
	var result;
	if (_isDead) {
		result = deadAmount();
	} else {
		if (_isSeparated) {
			result = separatedAmount();
		} else {
			if (_isRetired) {
				result = retiredAmount();
			} else {
				result = normalPayAmount();
			}
		}
	}
	return result;
}
```

改为：

```js
function getPayAmount() {
	if (_isDead) return deadAmount();
	if (_isSeparated) return separatedAmount();
	if (_isRetired) return retiredAmount();
	return normalPayAmount();
}
```

示例2：将条件反转

```js
function getCapital() {
	var result = 0.0;
	if (_capital > 0.0) {
		if (_intRate > 0.0 && _duration > 0.0) {
			result = (_income / _duration) * ADJ_FACTOR;
		}
	}
	return result;
}
```

改为：

```js
function getPayAmount() {
	var result = 0.0;
	if (_capital <= 0.0) return result;
	if (_intRate <= 0.0 || _duration <= 0.0) return result;
	return (_income / _duration) * ADJ_FACTOR;
}
```

#### 分解条件表达式

```js
if (date.before(Summer_Start) || date.after(Summer_End)) {
	charge = quantity * _winterRate + _winterServiceCharge;
} else {
	charge = quantity * _summerRate;
}
```

改为

```js
if (notSummer(date)) {
	charge = winterCharge(quantity);
} else {
	charge = summerCharge(quantity);
}
```

#### 合并条件表达式

```js
if (_seniority < 2) return 0;
if (_monthsDisabled > 12) return 0;
if (_isPartTime) return 0;
// compute the disability amount
```

改为

```js
if (isNotEligableForDisability()) return 0;
// compute the disability amount
```

#### 移除控制标记

* 使用`break`取代控制标记

```js
function checkSecurity(peoples) {
	var found = false;
	for (i = 0; i < peoples.length; i++) {
		if (!found) {
			if (people[i] === 'Don') {
				sendAlert();
				found = ture;
			}
		}
	}
}
```

改为：

```js
function checkSecurity(peoples) {
	for (i = 0; i < peoples.length; i++) {
		if (people[i] === 'Don') {
			sendAlert();
			break;
		}
	}
}
```

* 使用`return`返回控制标记

```js
function checkSecurity(peoples) {
	var found = '';
	for (i = 0; i < peoples.length; i++) {
		if (found === '') {
			if (people[i] === 'Don') {
				sendAlert();
				found = 'Don';
			}
		}
	}
	someLaterCode(found);
}
```

改为：

```js
function checkSecurity(peoples) {
	var found = foundMiscreant(people);
	someLaterCode(found);
}
function foundMiscreant(peoples) {
	for (i = 0; i < peoples.length; i++) {
		if (people[i] === 'Don') {
			sendAlert();
			return 'Don';
		}
	}
}
```


#### 引入断言

```java
doublegetExpenseLimit(){
	if (project == null) {
		throw new MyException("test");
	}
	return project.getExpenseLimit();
}
```

改为：

```js
doublegetExpenseLimit(){
	Assert.isTrue(project != null);
	return project.getExpenseLimit();
}
```

#### 引入`Null`对象

```java
if (null != user) {
	return user.getName();
} else {
	return null;
}
```

使用`Optional`创建`Null`对象后：

```java
Optional<User> userOpt = Optional.ofNullable(user);

return userOpt.map(User::getName)
			.orElse(null);
```


#### 以多态取代条件表达式

（略）

#### 使用策略模式

（略）



#### 表驱动

某保险费率程序：

```js
if ('Male' === gendar) {
	if (age < 16) {
		rate = 70.0;
	} else if (age < 30) {
		rate = 95.0
	} else if (age < 55) {
		rate = 400.0
	} else if (age < 80) {
		rate = 600.0
	} else {
		rate = 1000.0
	}
} else {
	if (age < 11) {
		rate = 80.0;
	} else if (age < 31) {
		rate = 90.0
	} else if (age < 60) {
		rate = 400.0
	} else if (age < 85) {
		rate = 600.0
	} else {
		rate = 1000.0
	}
}
```
这是简化的逻辑结构，它应该已经能让你对事情的复杂度有足够的了解了。
这里只展示了保险费率随着年龄、性别、婚姻状况的不同情况而变化。
若再增加一个是否吸烟的情况，可以想象，把整个费率表编写出来该有多复杂。

而更好的做法是把这些费率存入由所有因素索引的数组（或表）里。

上面复杂的逻辑就可以用类似下面这样简单的语句取而代之：

```js
var rateTable = [
		[0,0],
		[1,2],
		... ,
		[70.0,71.0],
		[80.0,85.0]
	];

var rate = rateTable[age][gender];
```

表驱动：

* 直接访问
* 索引访问
* 阶梯访问



***

## 参考
* [《代码大全》](https://book.douban.com/subject/1477390/)
* [《代码整洁之道》](https://book.douban.com/subject/4199741/)
* [《编写可读代码的艺术》](https://book.douban.com/subject/10797189/)
* [《修改代码的艺术》](https://book.douban.com/subject/2248759/)
* [《重构》](https://book.douban.com/subject/4262627/)
