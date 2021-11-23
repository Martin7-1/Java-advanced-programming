# Java Concurrency

> 先来看几个概念



## “线程”与“进程”的区别

1. **进程**：进程是系统进行资源分配和调度的一个独立单位，也是一个具有独立功能的程序；
2. **线程**：线程<span style = 'color: red'>**依托进程而存在**</span>，是CPU调度和分派的基本单位，它是比进程更小的能独立运行的基本单位。线程自己基本上不拥有系统资源，只拥有一点在运行中必不可少的资源（如`PC(Program Counter)`，一组寄存器和栈），但是它与同属一个进程的其他线程共享进程所拥有的的全部资源。

> 以Java虚拟机（JVM）来举例，在虚拟机中，以下东西是**线程共享**的：
>
> 1. **堆**（Heap）：存储对象实例（new）
> 2. **方法区**（Method Area）：存储被虚拟机加载的类信息、常量、静态变量等
>
> 以下东西是每个**线程独有**的：
>
> 1. **栈**（Stack）：保存线程执行方法时的信息
> 2. **本地方法栈**（Native Method Stack）：执行native方法，注意此时PC为空
> 3. **程序计数器**（Program Counter Register）



## “并发”与“并行“的区别

1. **并发**

	> 并发指的是**关于正确有效地控制对共享资源的访问**，即我们并没有将资源扩大或者翻倍，而是通过对不同线程的控制来实现对相同资源的访问，只不过我们通过巧妙的处理让不同线程之间的切换速度达到了一个阙值，从而使得在外部看起来这几个线程确实在同时运行。

2. **并行**

	> 并行指的是**使用额外的资源来更快地产生结果**。即我们增加了任务的处理速度，通过更多的资源来实现真正的同时处理任务。这在计算机内部表现为通过“多处理器”的方式来分配不同的任务，从而实现并行计算。

**Tips:** 当然，并发与并行在很多时候是存在重叠的，并发通常表示“不止一个任务正在执行”。而“并行”则表示“不止一个任务同时执行”。可以看出并发其实是一种特殊的并行。此外，为并行编写的程序其实也可以在单处理器上运行，而并发编写的程序也可以利用多处理器。



## 并发的新定义（On Java 8）

> 并发性是一系列专注于减少等待的性能技术

1. **何为减少等待**

	在我看来，减少等待的意思是在你拥有多个任务的时候，如果其中某个任务需要等待其他任务的完成，通过并发，你可以将该任务在等待的这段时间运用起来，从而提高效益。例：I/O中十分常见的堵塞就是一种等待的情况，如果有这种情况发生，我们可以通过并发来充分利用该时间去处理其他的任务。

> 值得强调的是，这个定义的有效性取决于“等待”这个词。如果没有什么可以等待，那就没有机会去加速。如果有什么东西在等待，那么就会有很多方法可以加快速度，这取决于多种因素，包括系统运行的配置，你要解决的问题类型以及其他许多问题。 -- 《On Java 8》



## Java并发的四条格言

> 1. 不要用它（避免使用并发）
> 2. 没有什么是真的，一切可能都有问题
> 3. 仅仅是它能运行，并不意味着它没有问题
> 4. 你必须理解它（逃不掉并发）



### 1.不要用它（避免使用并发）

> 切记不要自己去实现它

**Q：**为什么要使用并发？

**A：**唯一正当理由就是为了<span style = 'color: red'>**速度**</span>，只有在没有办法实现其他方法来优化它的时候，才应该去考虑有并发来提高程序的运行速度。 -- 我们应该首先用一个分析器来发现你是否可以执行其他一些优化

> 如果你被迫使用并发，请采取最简单，最安全的方法来解决问题。使用知名的库并尽可能少地自己编写代码。



### 2.没有什么是真的，一切可能都有问题

> 在并发世界中，我们并不能够确定那些事情是真的哪些事情是假的。我们必须对一切保持怀疑的态度，因为我们并不知道内部是如何分配任务给各个线程的，可能再一次运行就会出现不同的结果。

在并发编程中，不同的线程可能会修改共享的数据，这就会造成我们的程序出现错误，但很多时候我们并不能够轻易的知道错误究竟发生在何处，这时候我们就需要深刻了解一些重要的机制。

> 在非并发编程中你可以忽略的各种事情，在并发下突然变得很重要。例如，你必须了解处理器缓存以及保持本地缓存与主内存一致的问题，你必须理解对象构造的深层复杂性，这样你的构造函数就不会意外地暴露数据，以致于被其它线程更改。这样的例子不胜枚举。 -- 《On Java 8》



### 3.仅仅是它能运行，并不意味着它没有问题

> - 你不能验证出并发程序是正确的，你只能（有时）验证出它是不正确的。
> - 大多数情况下你甚至没办法验证：如果它出问题了，你可能无法检测到它。
> - 你通常无法编写有用的测试，因此你必须依靠代码检查和对并发的深入了解来发现错误。
> - 即使是有效的程序也只能在其设计参数下工作。当超出这些设计参数时，大多数并发程序会以某种方式失败。



### 4.你必须理解它

Java 是一种多线程语言，尽管我们在尽力的避免使用并发编程，但我们终究不能够避免它。Web 系统是常见的 Java 应用之一，本质上是多线程的 Web 服务器，通常包含多个处理器。



## 并行流（Parallel Streams）







## ExecutorService

> Java.util.concurrent包



### Runnable

`Runnable`只是定义了**所需要做的任务**，它是一种函数式接口（**Functional Interface**），创建线程并不能单单通过实现`Runnable`接口来创建，而是要通过`Thread`

```java
package com.nju.edu.concurrency;

public class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id +"(" + (countDown > 0 ? countDown : "LiftOff!") + "), ";
    }

    @Override
    public void run() {
        // runnable接口仅仅布置任务
        while (countDown-- > 0) {
            System.out.println(status());
            Thread.yield();
        }
    }
}
```



### newCachedThreadPool

`Executors.newCachedThreadPool`创建的线程池会根据需要来创建线程。

1. 如果在提交任务的时候现有线程没有可用的，就创建一个新线程并添加到线程池中。
2. 如果在提交任务的时候有**被使用完但是还没有被销毁的线程**，`newCachedThreadPool`就会复用该线程。

``` java
package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.nju.edu.concurrency.LiftOff;

public class CachedThreadPool {

    public static final int THREAD_AMOUNT = 3;

    public static void main(String[] args) {
        // 根据需要创建线程
        // 如果现有线程没有可用的，就创建一个新线程并添加到池中
        // 如果有被使用完但还没有被销毁的，就复用该线程
        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            // 根据需要创建新线程的线程池
            exec.execute(new LiftOff(10));
        }
        exec.shutdown();

    }
    
}
```



### FixedThreadPool

``` java
package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.nju.edu.concurrency.LiftOff;

public class FixedThreadPool {

    public static final int THREAD_AMOUNT = 5;

    public static void main(String[] args) {
        // 创建固定线程数的线程池
        // 任何时候都只有固定的线程数被创建
        // 如果所有线程都在活动状态时有其他任务提交，会在等待队列中直到有线程可用
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_AMOUNT);

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            exec.execute(new LiftOff(10));
        }
        exec.shutdown();
    }
    
}
```



### Callable and Future

`Callable`和`Runnable`是用处比较接近的两个接口，不同的是以下几点：

1. `Callable`接口能够返回异步执行的任务结果，而且可以抛出异常
2. `Runnable`接口没办法返回结果和抛出一个检查到的异常



`Future<V>`是一种用来保存异步计算结果的接口，其中的`get()`方法会阻塞直到`Callable`返回当前线程执行的结果。

#### 示例代码

> 定义一个属于自己的`Callable`类并覆盖其中的`Call()`方法

```java
package com.nju.edu.concurrency;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("做一些耗时的任务...");
        // sleep()方法参数为毫秒
        Thread.sleep(5000);

        return "OK";
    }
    
}
```

```java
package com.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.nju.edu.concurrency.MyCallable;

public class FutureSimpleDemo {
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 提交任务
        Future<String> future = executorService.submit(new MyCallable());

        System.out.println("do something...");
        try {
            // future.get()会堵塞直到Callable的任务做完并且返回结果
            System.out.println("得到异步任务返回结果: " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Completed!");
    }
}
```



> 上述代码的`future.get()`会一直阻塞直到当前线程的`Call()`方法返回结果。



### Thread.yield()

* `Thread.yield()`方法会临时暂停当前线程，让有同样优先级但是正在等待的线程有机会开始执行。

* 若没有正在等待的线程或者所有正在等待的线程的优先级都比较低，则当前执行`yield()`方法的线程可能会继续执行

* 执行`yield()`的线程何时会继续运行是由操作系统的线程调度器来决定的，我们无法去判断其什么时候会再次执行

* `yield()`方法不保证当前的线程会暂停或停止，但是可以保证当前线程在调用`yield()`方法时会放弃CPU

	> 即：在调用`yield()`方法的时候，虽然当前线程确实因为该方法暂停了，但是由于在所有线程中可能并没有其他线程的优先级是高于该线程的，所以该线程仍然可能继续被线程调度器选中从而继续执行，对于这种情况`yield()`并没有发挥我们设想中的作用。

<span style = 'color: red'>**注意：**</span> `yield()`方法**并不会释放**该对象的锁，即对加了锁的对象如果我们使用了`yield()`方法，即使该线程放弃了CPU，但因为该线程仍然持有该对象的锁，所以其他的线程仍然是访问不到该对象的，只有当线程调度器再次“唤醒”该线程的时候，该线程执行完某个加锁方法后才会将锁还给对象以供其他的线程来使用。



### Thread.sleep() -- old style

> `sleep()`方法与`yield()`方法大致相同，有以下几点不同：

* 当线程调用`sleep()`方法的时候，该线程将会被阻塞挂起**固定的时间**，在这期间线程调度器不会去调度该线程。
* 当线程调用`yield()`方法的时候，线程只是让出了自己剩余的时间片，但其状态仍然是**准备就绪**，即一直在等待线程调度器来调用它，也就会出现上面的刚`yield()`就马上又被调度的情况，从而导致`yield()`方法并没有发挥其应该有的作用。

#### 示例代码

```java
package com.nju.edu.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask implements Runnable {

    private int countDown = 10;

    @Override
    public void run() {
        // TimeUnit.MILLISECONDS.sleep()方法可能会抛出异常
        // Thread.sleep()方法是过去使用的，现在都用上面这个方法
        try {
            while (countDown-- > 0) {
                // Old-style: Thread.sleep(100);
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(countDown);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted!");
        }
    }

    public static final int THREAD_AMOUNT = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("before sleeping...");

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            executorService.execute(new SleepingTask());
        }

        executorService.shutdown();
    }
    
}
```



#### new-style

现在经常使用`concurrent`包下面的`TimeUnit`来代替`Thread.sleep()`方法

> 原因：`TimeUnit`能够指定时间的单位，而不是跟`sleep()`一样一直保持毫秒，且没有在明面上体现毫秒

**例**：

1. `TimeUnit.SECOND.sleep(1) // sleep一秒`
2. `TimeUnit.MILLISECOND.sleep(100) // sleep一百毫秒`



## Priority 优先级

> 线程是有优先级的，但是我们尽量不要去更改各个线程的优先级，而是让线程调度器来分配究竟什么线程的优先级比较高

### 实例代码

``` java
package com.nju.edu.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimplePriorities implements Runnable {

    private int countDown = 5;
    // Java中轻量级的锁
    private volatile double d;
    private int priority;

    public SimplePriorities(int priority) {
        this.priority = priority;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(this.priority);

        while (true) {
            for (int i = 0; i < 100000; i++) {
                d += (Math.PI + Math.E) / (double) i;
                if (i % 1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if (--countDown == 0) {
                return;
            }
        }
    }

    @Override
    public String toString() {
        return Thread.currentThread() + ": " + this.countDown;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }
        executorService.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        executorService.shutdown();
    }
}
```



## Daemon线程





## 资源共享问题

### Synchronized

`Synchronized`修饰某个方法意味着某个线程在调用该方法的时候必须要获得该**对象**的锁。





## 多线程异常

在**并发编程**中，异常处理与平时的异常处理是不同的，我们来看一下按照平常的普通`try-catch`方法来捕获多线程的异常会出现什么情况：

### 示例代码

```java
package com.nju.edu.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NaiveExceptionHandler {
    public static void main(String[] args) {
        // 常规的异常处理方式
        // 在多线程中没有用处

        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
            exec.shutdown();
        } catch (RuntimeException e) {
            // 这句永远都执行不到
            System.out.println("Exception has been handled!");
        }
    }
    
}

class ExceptionThread implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }
}
```

我们来看一下结果：

```java
Exception in thread "pool-1-thread-1" java.lang.RuntimeException
        at com.nju.edu.exception.ExceptionThread.run(ExceptionThread.java:10)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:834)
```

这说明我们并没有捕获到异常，这是为什么呢？原因如下：

1. 异常(Exception)在线程中不共享，**抛出异常**是在线程池中的某个线程抛出的，但**捕获异常**却是由主线程(Thread Main)在进行的。
2. 抛出与捕获线程的不同导致了我们没有办法捕获异常。

那么我们该如何在多线程中捕获异常呢？答案是这个借口：`Thread.UncaughtExceptionHandler`。这个类提供了默认的`uncaughtException(Thread t, Throwable e)`方法来为多线程捕获异常提供帮助。我们只需要自己实现这个借口或者使用默认的即可。示例代码如下：

### 示例代码

```java
package com.nju.edu.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class HandlerThreadFactory implements ThreadFactory {
    
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread.");
        Thread t = new Thread(r);

        System.out.println("created " + t);
        // 设置捕获异常
        // 这里是自己实现了Thread.UncaughtExceptionHandler
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh = " + t.getUncaughtExceptionHandler());

        return t;
    }
}

public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new AnotherExceptionThread());
        exec.shutdown();
    }
    
}

class AnotherExceptionThread implements Runnable {
    
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught " + e);
    }
}

```

运行结果如下：

```java
com.nju.edu.exception.HandlerThreadFactory@7382f612 creating new Thread.
created Thread[Thread-0,5,main]
eh = com.nju.edu.exception.MyUncaughtExceptionHandler@3caeaf62
run() by Thread[Thread-0,5,main]
eh = com.nju.edu.exception.MyUncaughtExceptionHandler@3caeaf62
caught java.lang.RuntimeException
```

可以看到异常已经被成功捕获~

### 拓展 -- Java中的异常捕获机制

1. 首先我们知道，Java中并不需要声明会抛出`RuntimeException`，调用会抛出`RuntimeException`的方法时我们也不需要去捕获这个异常，`RuntimeException`（运行时异常）是在Java虚拟机执行期间会抛出的一类异常，比如`NullPointerException`和`IndexOutOfBoundException`都是`RuntimeExcpetion`的其中一种。
2. 其次，对于除了`RuntimeException`之外的异常，如果我们在某个方法中**显式**的抛出了异常（`throw new xxxException`），那么我们在方法的接口上就必须声明我们抛出了该异常（`throws xxException`）。

#### 示例代码

```java
package com.nju.edu.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExceptionTest {

    public void throwRuntimeException() {
        // 直接抛出一个异常
        // 这里抛出的是RuntimeException，不需要在方法处声明
        throw new ArithmeticException();
    }

    public void runWithoutCatch() throws InterruptedException {
        // 抛出一个非RuntimeException的异常
        // 需要在方法上声明抛出了这个异常，以便调用者能够捕获
        throw new InterruptedException();
    }

    public void runWithCatch() {
        // 用try-catch来捕获异常
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = in.readLine();
        } catch (IOException e) {
            System.out.println("catch IOException successfully");
        }
    }

    public static void main(String[] args) {
        ExceptionTest test = new ExceptionTest();
        try {
            // 如果这句有异常抛出，那么接下来的语句都不会执行
            test.throwRuntimeException();
        } catch (RuntimeException e) {
            // 实际上我们不需要捕获，因为JVM会帮我们捕获运行时异常
            System.out.println("catch RuntimeException successfully!");
        }

    }
    
}
```

其他有关异常的详细机制大家可以自己查阅资料~这里主要是想要介绍一下并发编程中的异常捕获机制和普通的不同。



## 创建和运行任务 -- 有关线程池

Java的早期版本，我们可以通过创建自己的`Thread`对象来使用线程，甚至我们可以创建`Thread`的子类对象来创建自己的特定“任务线程”对象。

但在现在，尤其是Java5之后，主动创建线程的方法并被采用，我们开始采用**线程池**的概念来创建线程（`java.util.concurrent`包下）。你可以将任务创建为单独的类型，然后将其交给 ExecutorService 以运行该任务，而不是为每种不同类型的任务创建新的 Thread 子类型。ExecutorService 为你管理线程，并且在运行任务后重新循环线程而不是丢弃线程。

### 第一个线程池 -- SingleThreadPool

#### 示例代码

```java
package com.nju.edu.threadpool;

public class NapTask implements Runnable {

    final int id;

    public NapTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        new Nap(0.1); // seconds
        System.out.println(this + " " + Thread.currentThread().getName());
    }

    @Override
    public String toString() {
        return "NapTask[" + id + "]";
    }
}
```

```java
package com.nju.edu.threadpool;

import java.util.concurrent.TimeUnit;

public class Nap {

    public Nap(double t) {
        try {
            TimeUnit.SECONDS.sleep((int)t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Nap(double t, String msg) {
        this(t);
        System.out.println(msg);
    }
    
}

```

`Nap`类调用了`TimeUnit.SECONDS.sleep()`方法，该方法获取“当前线程”并在参数中将其置于休眠状态，这意味着该线程被挂起。这并不意味着底层处理器停止。操作系统将其切换到其他任务，例如在你的计算机上运行另一个窗口。OS 任务管理器定期检查 **sleep()** 是否超时。当它执行时，线程被“唤醒”并给予更多处理时间。

```java
package com.nju.edu.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SingleThreadExecutor {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10)
            .mapToObj(NapTask::new)
            .forEach(exec::execute);
        
        System.out.println("All tasks submitted");
        exec.shutdown();

        while (!exec.isTerminated()) {
            System.out.println(Thread.currentThread().getName() + " awaiting termination");
            new Nap(0.1);
        }
    }
    
}
```

首先请注意，没有 **SingleThreadExecutor** 类。**newSingleThreadExecutor()** 是 **Executors** 中的一个工厂方法，它创建特定类型的 **ExecutorService** 

我创建了十个 NapTasks 并将它们提交给 ExecutorService，这意味着它们开始自己运行。然而，在此期间，main() 继续做事。当我运行 callexec.shutdown() 时，它告诉 ExecutorService 完成已经提交的任务，但不接受任何新任务。此时，这些任务仍然在运行，因此我们必须等到它们在退出 main() 之前完成。这是通过检查 exec.isTerminated() 来实现的，这在所有任务完成后变为 true。

请注意，main() 中线程的名称是 main，并且只有一个其他线程 pool-1-thread-1。此外，交错输出显示两个线程确实同时运行。

如果你只是调用 exec.shutdown()，程序将完成所有任务。也就是说，不需要 **while(!exec.isTerminated())** 。



> 一旦你调用了 exec.shutdown()，尝试提交新任务将抛出 `RejectedExecutionException`。



### CachedThreadPool

使用线程的重点是（几乎总是）更快地完成任务，那么我们为什么要限制自己使用 SingleThreadExecutor 呢？查看执行 **Executors** 的 Javadoc，你将看到更多选项。例如 CachedThreadPool：

```java
package com.nju.edu.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CachedThreadPool {
    
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
            .mapToObj(NapTask::new)
            .forEach(exec::execute);
            exec.shutdownNow();
    }
}
```

当你运行这个程序时，你会发现它完成得更快。这是有道理的，每个任务都有自己的线程，所以它们都并行运行，而不是使用相同的线程来顺序运行每个任务。这似乎没毛病，很难理解为什么有人会使用 SingleThreadExecutor。



### 比较

看了以上的代码，为什么我们还要用`SingleThreadExecutor`呢？我们来看接下来的代码：

#### 示例代码

```java
package com.nju.edu.threadpool;

public class InterferingTask implements Runnable {
    
    final int id;
    private static Integer val = 0;

    public InterferingTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            val++;
        }
        System.out.println(id + " " + Thread.currentThread().getName() + " " + val);
    }
}
```

如果我们使用多个线程（`newCachedThreadPool`），这些线程都会使用到`run()`方法，改变`val`的状态，这时候我们称这是**线程不安全**的。

如果我们使用了`SingleThreadExecutor`，我们每次都得到一致的结果，尽管 **InterferingTask** 缺乏线程安全性。这是 SingleThreadExecutor 的主要好处 - 因为它一次运行一个任务，这些任务不会相互干扰，因此强加了线程安全性。这种现象称为**线程封闭**，因为在单线程上运行任务限制了它们的影响。线程封闭限制了加速，但可以节省很多困难的调试和重写。



### Callable

`implements Runnable`并不能够产生返回值。避免竞争条件的最好方法是避免可变的共享状态。我们可以称之为自私的孩子原则：什么都不分享。
