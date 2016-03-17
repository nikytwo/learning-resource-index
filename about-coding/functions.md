
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

* 好名字
* 只做一件事
* 参数
    - 顺序
	- 名字
	- 数量，7是个很神奇的数字，让你的参数保持在7个以内。(《代码整洁之道》则为 4)
	- 不要作为工作变量
* 嵌套层


### 改进函数的其他方法

* 优化复杂表达式（见[提供代码质量：表达式](./conditions.md)）
* 减少变量
* 变量跨度（生存代码行数）
* 减小变量作用域
* 使用不可变的变量



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

```
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


### 其他

关于注释：见[提供代码质量：注释](./comments.md)


***

## 参考

* [提高代码质量：如何编写函数](http://luopq.com/2016/02/21/write-good-function/)
