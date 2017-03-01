#复现 ANR

## 什么是 ANR？
ANR:Application Not Responding，即应用无响应

## ANR一般有三种类型：
* KeyDispatchTimeout(5 seconds) --主要类型按键或触摸事件在特定时间内无响应
* BroadcastTimeout(10 seconds) --BroadcastReceiver在特定时间内无法处理完成
* ServiceTimeout(20 seconds) --小概率类型 Service在特定的时间内无法处理完成

## ANR产生的原因
ANR 产生的根本原因是 APP 阻塞了 UI 线程。在 Android 系统中每个 App 只有一个 UI 线程，是在 App 创建时默认生成的，UI 线程默认初始化了一个消息循环来处理 UI 消息，ANR 往往就是处理 UI 消息超时了。

## 一些避免 ANRs 的技巧
* 建议使用 AsycnTask 来异步处理后台数据
* 相比起 AsycnTask 来说，创建自己的线程或者 HandlerThread 稍微复杂一点。如果你想这样做，你应该通过 Process.setThreadPriority() 并传递 THREAD_PRIORITY_BACKGROUND 来设置线程的优先级为"background"。
* 如果你的程序需要响应正在后台加载的任务，在你的 UI 中可以显示 ProgressBar 来显示进度。
* 对游戏程序，在工作线程执行计算的任务。
* 如果你的程序在启动阶段有一个耗时的初始化操作，可以考虑显示一个闪屏，要么尽快的显示主界面，然后马上显示一个加载的对话框，异步加载数据。无论哪种情况，你都应该显示一个进度信息，以免用户感觉程序有卡顿的情况。
* 使用性能测试工具，例如 Systrace 与 Traceview 来判断程序中影响响应性的瓶颈。

## 参考资料
* [Android ANR 分析解决方法](http://www.cnblogs.com/purediy/p/3225060.html)
* [中文版文档-避免出现程序无响应ANR](http://hukai.me/android-training-course-in-chinese/performance/perf-anr/index.html)
* [ANR完全解析](https://blog.saymagic.tech/2014/03/25/anr-analyze.html)
