#AEXI
AEXI是一个富文本的编辑器,基于Java平台的Swing库实现.AEXI来自于《Design Pattern》一书中第三章的例子**Jexi**.我觉得这是个非常好的联系各种设计模式的项目,并且Android平台上现有的富文本编辑器都是基于Webview实现的,所以就开发了这样一个框架.选择基于Swing库实现AEXI主要是考虑到Swing可以在PC上调试起来非常的方便.但是AEXI的目标最终是封装成Android平台上的一个库.方便客户端程序员直接使用.由于Android平台可以直接使用Java语言编写,所以AEXI的代码在移植时可以复用绝大部分,只需要更改部分图像绘制和系统功能交互相关的API即可.
将AEXI移植到Android平台的项目是[AEXI-Android](https://github.com/androidfans/Aexi-Android).
目前这一版的AEXI主要实现的功能有:

* 支持文字以及图片在任意位置的插入操作
* 支持任意位置删除操作
* 支持鼠标的拖拽选择
* 插入文字的任意大小,任意字体,任意颜色的调整
* 插入删除等操作的反操作(撤销)
* 纸张,风格等的调整

计划在移植到Android平台后继续开发的功能

* 图片大小的拖拽
* 系统粘贴板的交互
* 开放接口使得应用程序员可以自定义解析逻辑,提供一种显式效果到各种序列化方式自由转换的机制
* 文字或图片的位置拖拽调整
* 支持DOC和Markdown格式的解析和反解析
* 数学公式的编辑支持

下面是当前AEXI的效果图:
![](http://7xntdm.com1.z0.glb.clouddn.com/AEXI-Swing.png)
