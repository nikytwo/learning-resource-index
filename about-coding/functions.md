
## 函数

### 为什么需要函数

* 降低和隔离复杂度
* 易理解
* 提高移植性
* 改善性能
* 隐藏实现细节，隐藏全局变量
* 限制变化带来的影响
* 形成中央控制点
* 达到特定的重构目的


### 高质量函数

* 短小：函数第一规则是要短小，第二规则是**还要更短小**
* 好名字：详见《代码整洁之道》第2章，《代码大全》
    - 别害怕长名字
	- 别害怕花时间取名字
* 只做一件事：做好这件事，只做这件事

> 每个函数一个抽象层次

```python
def save
	business = Business();
	business.name = request.POST["name"];

	url_path_name = business.name.lower();
	url_path_name = re.sub(r"['\.']", "", url_path_name);
	url_path_name = re.sub(r"[^a-z0-9]+", "-", url_path_name);
	url_path_name = url_path_name.strip("-");
	business.url = "/biz/" + url_path_name;

	business.date_create = datetime.utcnow();
	business.save_to_datebase();

```

* 参数
    - 顺序
	- 名字
	- 数量，7是个很神奇的数字，让你的参数保持在7个以内。(《代码整洁之道》则为 4)
	- 不要使用标识参数
	- 不要作为工作变量（输入参数）
	- 不要使用输出参数

```csharp
// 参数过多
public static ResultState InsertMessageInfo1(string UI_ID, string trueName, string idCardNO, string mobile, 
            string SN_ID, string parms, string APP_Code, int? APP_Type, int Send_State, int ValidateTime, 
            ref decimal Send_ID, string Gather, int payType, string use, string orderNo, params bool[] arrSendState)
```

```java
// 使用标识参数
public void render(bool isSuite){
	// ...
}

// 改为
public void renderForSuite(){
	// ...
}
public void renderForSingle(){
	// ...
}

```

```java
// 作为工作变量（输入参数）
public void doSomething(string args){
	// do some thing
	args = ...
	// do some thing
	return args;
}
```

```csharp
// 使用输出参数
GetInfoData(num, ref infoList, ref user);

// 改为
User user = userService.FindBy(num);
ICollection<ShowInfo> infoList = userService.FindInfos(user);
```

* 嵌套层：不该多于两层
* 无副作用：

```java
public boolean checkPassword(string name, string password){
	User user = UserRepository.findByName(name);
	if (null != user) {
		string codePhrase = user.getPhraseEncodeByPassword();
		string phrase = cryptographer.decrypt(codePhrase, password);
		if ("Valiad Password".equals(phrase)) {
			Session.initialize();
			return true;
		}
	}
	return false;
}
```

* 分隔指令与询问：函数要么做什么事，要么回答什么事

```java
public boolean set(string attribute, string value);

if (set("username", "unclebob")){
	// ...
}
```

```java
if (attributeExists("username")){
	setAttribute("username", "unclebob");
	// ...
}
```

* 使用异常代替返回错误码：见[提供代码质量：错误处理与异常](./about-coding/exceptions.md)



### 改进函数的其他方法

* 优化复杂表达式（见[提供代码质量：表达式](./about-coding/conditions.md)）
* 抽取子函数：详见《编写可读代码的艺术》第10章
* 减少变量
* 使用不可变的变量
* 变量跨度（生存代码行数）
* 减小变量作用域
* 相关操作放在一起
* 正确处理异常（见[提供代码质量：错误处理与异常](./about-coding/exceptions.md)）



### 为什么函数不能太长？

长 -》 复杂 -》 难以理解 -》 难以修改。

长就一定复杂？
多长才算复杂？
如何计算复杂？

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


最后，
函数应该多长？

如果你的函数超过了200行，那你就要小心了。

所有函数不能超过80行！



### 最后的最后

**大师级程序员把系统当作故事来讲**，而不是当作程序来写！



***

## 参考

* [提高代码质量：如何编写函数](http://luopq.com/2016/02/21/write-good-function/)
* [《代码大全》](https://book.douban.com/subject/1477390/)
* [《代码整洁之道》](https://book.douban.com/subject/4199741/)
* [《编写可读代码的艺术》](https://book.douban.com/subject/10797189/)

