# 复现 OOM
 
 像 Java 这样具有垃圾回收功能的语言的好处之一，就是程序员无需手动管理内存分配。
 这减少了段错误（segmentation fault）导致的闪退，也减少了内存泄漏导致的堆空间膨胀，让编写的代码更加安全。
 然而，Java 中依然有可能发生内存泄漏。所以安卓 APP 依然有可能浪费了大量的内存，甚至由于内存耗尽（OOM）导致闪退。

## 常见的几种内存泄漏实例

###  1.单例造成的内存泄露

    /**
     * 用于演示单例引起的内存泄漏
     * 由于例中的 instance 持有了context，所以被传进来的 activity（或者 service 或 broadcast）没有办法释放，也就造成了内存泄露。
     * <p>
     * Created by Mouzhai on 2017/3/1.
     */

    public class SingletonDemo {
        private static volatile SingletonDemo instance;
        private Context context;

        private SingletonDemo(Context context){
            this.context = context;
        }

        public static SingletonDemo getInstance(Context context){
            if (instance == null){
                synchronized (SingletonDemo.class){
                    if (instance == null)
                        instance = new SingletonDemo(context);
                }
            }
            return instance;
        }
    }
 
### 2.InnerClass匿名内部类

在 Java 中，非静态内部类和匿名类都会潜在的引用它们所属的外部类，但是，静态内部类却不会。
如果这个非静态内部类实例做了一些耗时的操作，就会造成外围对象不会被回收，从而导致内存泄漏。

    class InnerClass extends Thread {
            @Override
            public void run() {
                //由于这里的死循环，此内部类一直不会被回收，它持有的外部 Activity 的引用也就不会被释放
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
### 3.静态变量的引用

泄漏 activity 最简单的方法就是在 activity 类中定义一个 static 变量，并且将其指向一个运行中的 activity 实例。
如果在 activity 的生命周期结束之前，没有清除这个引用，那它就会泄漏了。
这是因为 activity（例如 MainActivity） 的类对象是静态的，一旦加载，就会在 APP 运行时一直常驻内存，
因此如果类对象不卸载，其静态成员就不会被垃圾回收。

    static Activity staticActivity;
    static View staticView;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test);

            //一个静态的 Activity 可能会引起内存泄漏，如果此 Activity 结束之前没有清除此引用，这个对象就一直不会被回收
            staticActivity = this;

            //静态的 View 会持有 Context 的强引用，可能引起内存泄漏
            staticView = findViewById(R.id.tv_test);
      }
   

### 4.Handler引起的内存泄漏

    //当 Handler 中有延迟的的任务或是等待执行的任务队列过长，由于消息持有对 Handler 的引用，
    //而 Handler 又持有对其外部类的潜在引用，会导致 Activity 无法被垃圾回收器回收，造成内存泄露。
     void createHandler() {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            }.postDelayed(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, Long.MAX_VALUE >> 1);
        }

### 5.注册监听器的泄漏

系统服务可以通过 context.getSystemService 获取，它们负责执行某些后台任务，或者为硬件访问提供接口。
如果 context 对象想要在服务内部的事件发生时被通知，那就需要把自己注册到服务的监听器中。
然而，这会让服务持有 activity 的引用，如果程序员忘记在 activity 销毁时取消注册，那就会导致 activity 泄漏了。

### 6.Cursor，Stream 没有 close，View 没有 recyle

资源性对象比如(Cursor，File 文件等)往往都用了一些缓冲，我们在不使用的时候，应该及时关闭它们，以便它们的缓冲及时回收内存。
它们的缓冲不仅存在于 java 虚拟机内，还存在于 java 虚拟机外。
如果我们仅仅是把它的引用设置为 null,而不关闭它们，往往会造成内存泄漏。
因为有些资源性对象，比如 SQLiteCursor(在析构函数 finalize(),如果我们没有关闭它，它自己会调 close() 关闭)，
如果我们没有关闭它，系统在回收它时也会关闭它，但是这样的效率太低了。
因此对于资源性对象在不使用的时候，应该调用它的 close() 函数，将其关闭掉，然后才置为 null。
在我们的程序退出时一定要确保我们的资源性对象已经关闭。

### 7.集合中对象没清理造成的内存泄漏

我们通常把一些对象的引用加入到了集合容器（比如 ArrayList）中，
当我们不需要该对象时，并没有把它的引用从集合中清理掉，这样这个集合就会越来越大。
如果这个集合是 static 的话，那情况就更严重了。
所以要在退出程序之前，将集合里的东西 clear，然后置为 null，再退出程序。

### 8.WebView造成的泄露

当我们不要使用 WebView 对象时，应该调用它的 destory() 函数来销毁它，并释放其占用的内存，否则其占用的内存长期也不能被回收，从而造成内存泄露。

### 9.帧动画造成的 OOM

一般的帧动画可以简单地用 animation-list 来完成，例如下面：

    <?xml version="1.0" encoding="utf-8"?>
    <animation-list xmlns:android="http://schemas.android.com/apk/res/android"
        android:oneshot="false">

        <item
            android:drawable="@drawable/a"
            android:duration="200" />

        <item
            android:drawable="@drawable/b"
            android:duration="200" />

        <item
            android:drawable="@drawable/c"
            android:duration="200" />

        <item
            android:drawable="@drawable/d"
            android:duration="200" />

        <item
            android:drawable="@drawable/e"
            android:duration="200" />

        <item
            android:drawable="@drawable/f"
            android:duration="200" />

        <item
            android:drawable="@drawable/g"
            android:duration="200" />

    </animation-list>
    
但是假如想要一口气加载几百张图片，或是图片过大，就会造成卡顿甚至 OOM。
如果一定要加载大量大图，就要想办法处理 Bitmap，例如图片压缩、手动回收释放资源、缓存、使用其他方式播放动画等等。

## 参考资料

* [常见的八种导致 APP 内存泄漏的问题](http://blog.nimbledroid.com/2016/05/23/memory-leaks-zh.html)
* [Android 内存泄漏分析心得](https://zhuanlan.zhihu.com/p/25213586)
* [Android 内存优化之 OOM](http://hukai.me/android-performance-oom/)
* [利用 Android Studio、MAT 对 Android 进行内存泄漏检测](https://joyrun.github.io/2016/08/08/AndroidMemoryLeak/)
