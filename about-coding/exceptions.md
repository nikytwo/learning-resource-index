
## 错误处理与异常

### 1. 抛出异常

* 不要返回**错误码**；

使用错误码：

```java
if (deletePage(page) == E_OK) {
	if (registry.deleteReference(page.name) == E_OK) {
		if (configKeys.deleteKey(page.name.makeKey()) == E_OK) {
			logger.log("page deleted");
		} else {
			logger.log("configKey not deleted");
		}
	} else {
		logger.log("deleteReference from registry failed");
	}
} else {
	logger.log(delete failed);
	return E_ERROR;
}
```

使用异常：

```java
try {
	deletePage(page);
	registry.deleteReference(page.name);
	configKeys.deleteKey(page.name.makeKey());
} catch (Exception e) {
	logger.log(e.getMessage());
}
```

> 错误码会导致更深层次的嵌套结构。当返回错误码时，就是在**要求调用者立刻处理错误**。

> 使用异常，错误处理就能从主路径代码中分离出来。

> 异常包括两种信息：异常消息、异常类型（用于异常处理程序）。

`web api`中的异常处理程序：

```csharp
// 定义过滤器
public class ApiExceptionFilterAttribute : ExceptionFilterAttribute
{
	public override void OnException(HttpActionExecutedContext actionExecutedContext)
	{
		base.OnException(actionExecutedContext);

		if (actionExecutedContext.Exception is NotImplementedException)
		{
			actionExecutedContext.Response = new HttpResponseMessage(HttpStatusCode.NotImplemented);
			// todo 其他处理，如记录日志...
		}
		else if (...)
		{
		    // todo 其他异常类型的处理
		}
		else
		{
			actionExecutedContext.Response = new HttpResponseMessage(HttpStatusCode.NotFound);
		}
	}
}

// 注册过滤器
filters.Add(new ApiExceptionFilterAttribute());

```


* 要通过抛出异常的方式来报告操作失败；

* 遇到严重问题应考虑终止进程；

* 避免在正常控制流程中使用异常；

> 好的框架应尽量让人编写不会引发异常的代码。（使用`Tester-Doer`或`Try-Parse`模式）

```csharp
// Tester-Doer
ICollection<int> numbers = ...
// ...
if (!numbers.IsReadOnly)
{
	numbers.Add(1);
}

// Try-Parse
public struct DateTime
{
	public static DateTime Parse(string dateTime)
	{
		// ...
	}
	public static bool TryParse(string dateTime, out DateTime result)
	{
		// ...
	}
}
```

* 别返回`null`值和传递`null`值；

> 可以考虑抛出异常或返回特例对象替代返回`null`值。

```java
// 返回空列表
public List<Employee> getEmployee() {
	if ( ... no employees ... ) {
		return Collections.emptyList();
	}
}
```

> 可以考虑抛出新异常(`ArgumentNullException`/`InvalidArgumentException`)或使用断言检查`null`值。

```java
// 抛出异常
if (p1 == null) {
	throw new InvalidArgumentException("Invalid argument for 。。。");
}
// 断言
assert p1 != null : "p1 should not be null";
```


### 2. 选择合适的异常类型

#### 2.1. 错误分类：

1. 使用错误： **不需要处理**，而是要修改代码。（如：`InvalidOperationException`,`ArgumentNullException`,`NotSupportedException`...）
2. 程序错误：应用程序**可以处理**。(如：`FileNotFoundException`...)
3. 系统失败：应用程序**无法处理**。(如：`OutOfMemoryException`...)

#### 2.2. 自定义异常的时机

* 程序错误（能够在代码中进行处理的），考虑创建并抛出自定义的异常；
* 错误是独一无二的（已有异常不能传达该错误，或使用已有异常导致错误情况模糊），应创建自定义的异常（优先考虑子类型）；

#### 2.3. 异常处理程序

* 捕获特定类型（已知道错误原因和处理办法）的异常；

> 不要把异常吞了。

```csharp
try
{
	File.Open(...);
}
catch (Exception e)
{
	// do nothing
}
```

* 不随便捕获异常，而是让其向上传递；（曝露不应该捕获的异常，以便测试发现并修复）(早抛出，晚捕获)

> 这可能有悖自觉，但许多情况下都不需要`catch`代码块。

* 捕获异常后需重新抛出时，优先考虑抛出原异常（空的`throw`语句）；

> 保持调用栈不变

```csharp
try
{
	// do some thing
}
catch
{
	// do other thing
	throw;
}


// 没堆栈:
 System.Exception: 用户已冻结

// 有堆栈:
 System.Exception: 用户已冻结
   在 BSY.Recognize.Service.DrivingLicenceRecognizer.ResolveXml(String xmlstring) 位置 E:\Work\Recognize\src\BSY.Recognize.Service\DrivingLicenceRecognizer.cs:行号 144
   在 BSY.Recognize.Service.DrivingLicenceRecognizer.Recognize(Stream imageStream) 位置 E:\Work\Recognize\src\BSY.Recognize.Service\DrivingLicenceRecognizer.cs:行号 68
   在 BSY.Recognize.Web.Controllers.TestController.Index(HttpPostedFileBase imgFile, String imgType) 位置 E:\Work\Recognize\src\Sample\BSY.Recognize.Web\Controllers\TestController.cs:行号 39

```

#### 2.4. 异常的封装

除非底层的异常对高层**没有意义**，否则不要轻易封装异常。
若要封装，必须指定其内部异常(inner exception)。

```csharp
throw new ConfigurationFileMissingException(..., e);
```


### 3. 自定义异常的设计

* 避免继承层次深；
* 可序列化；
* 覆盖`ToString`来报告与安全性有关的信息；
* 确保只有可信赖的代码才能获得安全信息；
* 考虑自定义属性来让程序获得异常有关的额外信息(异常发生的环境说明，如：失败的操作和失败类型)；


***

## 参考：

* [《.NET 设计规范》](https://book.douban.com/subject/4805165/)
* [《代码整洁之道》](https://book.douban.com/subject/4199741/)
