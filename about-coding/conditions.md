

## 复杂表达式

### 复杂条件表达式

#### 示例 1

```js
if (!((score > 700) || ((income >=40000) && (income <= 100000) && (score > 500)) || (income > 100000)))
	console.log("反对");
else
	console.log("接受");
```

问题：

根据上面的判断条件，填写以下表格（“接受”还是“反对”）。

|  | income > 100000 | 100000 >= income >= 40000 | 40000 > income |
| :------: | :--: | :--: | :------: |
|  score > 700  |    |    |    |
|   700 >= score >= 500   |    |    |    |
|  500 > score  |    |    |    |

#### 示例 2

某保险费率程序：

```js
if ('Male' === gendar) {
	if ('single' === maritalStatus) {
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
	} else
		if (age < 13) {
			rate = 250.0;
		} else if (age < 23) {
			rate = 300.0
		} else if (age < 60) {
			rate = 600.0
		} else if (age < 80) {
			rate = 900.0
		} else {
			rate = 1000.0
		}
	}
} else {
	if ('single' === maritalStatus) {
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
	} else
		// 其他
	}
}
```

#### 示例 3

```js
if (rate < 0.37963) {
	console.log("a1");
} else if (rate < 0.39003) {
	console.log("a2");
} else if (rate < 0.48925) {
	console.log("a3");
} else if (rate < 0.51298) {
	console.log("a4");
} else if (rate < 0.55702) {
	console.log("a5");
} else if (rate < 0.61293) {
	console.log("a6");
} else if (rate < ...) {
	// 其他
	// ...
	// ...
	// ...
} else {
	console.log("an");
}


```

### 总结

表驱动：

* 直接访问
* 索引访问
* 阶梯访问

### 其他方法

#### 分解条件表达式

```js
if (date.before(Summer_Start || date.after(Summer_End)) {
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

（略）

#### 合并重复的条件片段

（略）

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

更复杂的例子：

```js
public string WeiXin_WapBinding()
{
    string uiId = Session_User_InfoEntity.UI_ID;
    string openId = Session_User_InfoEntity.Open_ID;
    if (!string.IsNullOrEmpty(uiId) && !string.IsNullOrEmpty(openId))
    {
        string tmpUiId = User_InfoHandle.GetWeiXinUser(openId);
        string oppen_id = User_InfoDAL.GetWeixinID(uiId);
        int Mark = 0;//标记,为下边做区分
        if (tmpUiId != "" && tmpUiId != uiId)
        {
            return "此微信id已被绑定,不能一个微信号绑定多个账户。";
        }
        else if (tmpUiId == uiId)//标记,为下边做区分(自己绑自己)
        {
            Mark = 1;
        }

        string WeiXinNick = "成功更换微信";
        if (oppen_id == "")
        {
            WeiXinNick= "成功绑定微信";
        }
        
        //绑定微信id--是否判断唯一微信id(目前不要求)
        string result= WeiXin_WapBinding(Session_User_InfoEntity.NickName, openId, uiId);
        //是否是虚拟推荐人.是关联推荐人记录表//是否是被推荐人.是关联推荐人记录表
        if (Mark == 0 && WeiXinNick != "成功绑定微信")//不是自己绑自己原有的微信id,并且不是首次绑定,才可以删除重复数据
        {
            //删除重复的推荐人虚拟id
            USER_WEIXIN_TOTALDAL.GetRecommendOpendId(open_Id);
        }
        if (WeiXinNick == "成功绑定微信")
        {
            //是否是虚拟推荐人.是关联推荐人记录表
            USER_WEIXIN_TOTALDAL.SetRelateTotal(open_Id, User_Id);
        }
        else//再次绑定
        {
            USER_WEIXIN_TOTALDAL.UpdateRelateTotal(open_Id, User_Id);
        }
        
        return result != "操作失败" ? "成功" : result;
    }
    else
    {
        return "系统不能检测到您当前的微信！";
    }
}
```

改为:

```js
public string WeiXin_WapBinding()
{
    string uiId = Session_User_InfoEntity.UI_ID;
    string openId = Session_User_InfoEntity.Open_ID;

    if (string.IsNullOrEmpty(uiId) || string.IsNullOrEmpty(openId))
    {
        return "系统不能检测到您当前的微信！";
    }
    string uiIdOfCurWeixin = User_InfoHandle.GetWeiXinUser(openId);
    if ("" != uiIdOfCurWeixin && uiIdOfCurWeixin != uiId)
    {
        return "此微信id已被绑定,不能一个微信号绑定多个账户。";
    }
        
    string openIdOfCurUser = User_InfoDAL.GetWeixinID(uiId);
    string result = WeiXin_WapBinding(Session_User_InfoEntity.NickName, openId, uiId);
    if (openIdOfCurUser == "")
    {
        //是否是虚拟推荐人.是关联推荐人记录表
        USER_WEIXIN_TOTALDAL.SetRelateTotal(open_Id, User_Id);
    }
    else if (uiIdOfCurWeixin == "")
    {
        //删除重复的推荐人虚拟id
        USER_WEIXIN_TOTALDAL.GetRecommendOpendId(open_Id);
        USER_WEIXIN_TOTALDAL.UpdateRelateTotal(open_Id, User_Id);
    }
    else
    {
        // 待绑定的微信号与已绑定的微信号相同
        USER_WEIXIN_TOTALDAL.UpdateRelateTotal(open_Id, User_Id);
    }

    return result != "操作失败" ? "成功" : result;
}
```
 
#### 以卫语句取代嵌套条件

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

#### 以多态取代条件表达式

（略）

#### 引入`Null`对象

（略）

#### 引入断言

（略）

***

## 参考
* [《代码大全》](https://book.douban.com/subject/1477390/)
* [《代码整洁之道》](https://book.douban.com/subject/4199741/)
* [《编写可读代码的艺术》](https://book.douban.com/subject/10797189/)
* [《修改代码的艺术》](https://book.douban.com/subject/2248759/)
* [《重构》](https://book.douban.com/subject/4262627/)
