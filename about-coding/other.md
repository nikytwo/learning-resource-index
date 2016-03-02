

## 关于注释

一些新人喜欢在每行代码前面写一句注释，比如：

```js
// 成员列表的长度>0并且<200
if(memberList.size() > 0 && memberList.size() < 200) {
    // 返回当前成员列表
    return memberList;
}
```

看起来似乎很好懂，但是几年之后，这段代码就变成了：

```js
if(memberList.size() > 0 && memberList.size() < 200 || (tmp.isOpen() && flag)) {
    // 返回当前成员列表
    return memberList;
}
```

再之后可能会改成这样：

```js
// edit by axb 2015.07.30
// 成员列表的长度>0并且<200
//if(memberList.size() > 0 && memberList.size() < 200 || (tmp.isOpen() && flag)) {
//     返回当前成员列表
//    return memberList;
//}
if(tmp.isOpen() && flag) {
    return memberList;
}
```

随着项目的演进，无用的信息会越积越多，最终甚至让人无法分辨哪些信息是有效的，哪些是无效的。

如果在项目中发现好几个东西都在做同一件事情，比如通过注释描述代码在做什么，或者依靠注释替代版本管理的功能，那么这些代码也不能称为好代码。



***

## 参考

* [关于烂代码的那些事（上）](http://blog.2baxb.me/archives/1343)
* [关于烂代码的那些事（中）](http://blog.2baxb.me/archives/1378)
* [关于烂代码的那些事（下）](http://blog.2baxb.me/archives/1499)
