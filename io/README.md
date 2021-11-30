# Java I/O

## I/O的五种模型(unix系统)

1. 同步阻塞I/O
2. 同步非阻塞I/O
3. I/O 多路复用
4. 信号驱动 I/O
5. 异步 I/O

### 从以下两个维度来理解UNIX I/O模型
* 区分同步或异步（synchronous/asynchronous）。简单来说，同步是一种可靠的有序运行机制，当我们进行同步操作时，后续的任务是等待当前调用返回，才会进行下一步；而异步则相反，其他任务不需要等待当前调用返回，通常依靠事件、回调等机制来实现任务间次序关系。
* 区分阻塞与非阻塞（blocking/non-blocking）。在进行阻塞操作时，当前线程会处于阻塞状态，无法从事其他任务，只有当条件就绪才能继续，比如 ServerSocket 新连接建立完毕，或数据读取、写入操作完成；而非阻塞则是不管 IO 操作是否结束，直接返回，相应操作在后台继续处理。

## Java I/O

### BIO
> BIO（blocking IO）即阻塞IO。指的主要是传统的`java.io`包，它基于流（stream）模型实现。

#### BIO简介
java.io 包提供了我们最熟知的一些 IO 功能，比如 File 抽象、输入输出流等。交互方式是同步、阻塞的方式，也就是说，在读取输入流或者写入输出流时，在读、写动作完成之前，线程会一直阻塞在那里，它们之间的调用是可靠的线性顺序。

很多时候，人们也把 java.net 下面提供的部分网络 API，比如 Socket、ServerSocket、HttpURLConnection 也归类到同步阻塞 IO 类库，因为网络通信同样是 IO 行为。

BIO 的优点是代码比较简单、直观；缺点则是 IO 效率和扩展性存在局限性，容易成为应用性能的瓶颈。

### NIO
> NIO（non-blocking IO）即非阻塞IO。指的是`java.nio`包

为了解决 BIO 的性能问题， Java 1.4 中引入的 java.nio 包。NIO 优化了内存复制以及阻塞导致的严重性能问题。

java.nio 包提供了 Channel、Selector、Buffer 等新的抽象，可以构建多路复用的、同步非阻塞 IO 程序，同时提供了更接近操作系统底层的高性能数据操作方式。

#### 缓冲区
NIO 与传统 I/O 不同，它是基于块（Block）的，它以块为基本单位处理数据。在 NIO 中，最为重要的两个组件是缓冲区（Buffer）和通道（Channel）。

Buffer 是一块连续的内存块，是 NIO 读写数据的缓冲。Buffer 可以将文件一次性读入内存再做后续处理，而传统的方式是边读文件边处理数据。Channel 表示缓冲数据的源头或者目的地，它用于读取缓冲或者写入数据，是访问缓冲的接口。

### AIO
> AIO（Asynchronous IO）即异步非阻塞IO，指的是Java7中，对NIO有了进一步的改进，也称为NIO2，引入了异步非阻塞IO方式。

异步 IO 操作基于事件和回调机制，可以简单理解为，应用操作直接返回，而不会阻塞在那里，当后台处理完成，操作系统会通知相应线程进行后续工作。

## 传统IO流
流从概念上来说是一个连续的数据流。当程序需要读数据的时候就需要使用输入流读取数据，当需要往外写数据的时候就需要输出流。

BIO 中操作的流主要有两大类，字节流（Byte Streams）和字符流（Character Streams），两类根据流的方向都可以分为输入流和输出流。

### Byte Streams

* 输入字节流：`InputStream`
* 输出字节流：`OutputStream`

字节流主要操作**字节**数据或二进制对象，上述两个是字节流的核心抽象类。所有的字节流类都继承自这两个抽象类。

#### File

`FileOutputStream`和`FileInputStream`提供了读写字节到文件的能力。

文件流操作的一般步骤：

1. 使用`File`类绑定一个对象。
2. 把`File`对象绑定到流对象上。
3. 进行读或写操作。
4. **关闭流。**

```java
package com.nju.edu.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamDemo {

    private static final String FILEPATH = "temp.log";

    public static void main(String[] args) throws Exception {
        write(FILEPATH);
        String res = read(FILEPATH);
        System.out.println("content is " + res);
    }

    public static void write(String filepath) throws Exception {
        // 通过File类找到一个文件
        File file = new File(filepath);

        // 通过子类实例化父类对象
        OutputStream out = new FileOutputStream(file);
        // 实例化时，默认为覆盖原文件内容方式，如果添加true参数，则变为对原文件追加内容的方式
        // OutputStream out = new FileOutputStream(file, true);

        // 进行写操作
        String str = "Hello World\n";
        byte[] bytes = str.getBytes();
        out.write(bytes);

        // 关闭输出流
        out.close();
    }

    public static String read(String filepath) throws Exception {
        // 同上
        File file = new File(filepath);

        InputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        int len = in.read(bytes);
        System.out.println("读入数据的长度: " + len);

        in.close();
        // 这里用String.valueof和new String结果会不一样

        return new String(bytes);
    }
    
}
```

**关于`InputStream.read()`**

`InputStream.read()`一共有三种实现，有着一点区别，下面介绍一下这三种实现方法

1. `read()`：没有任何参数的`read`，此时`InputStream`会一个字节一个字节的读取文件中的数据，并且以`int`的形式返回（以下两种都是如此），即范围是（0 - 255，ACSII），如果遇到了读到了文件的末尾，那么该方法会返回-1.
2. `read(byte[])`：参数中有一个`byte[]`，返回的是读取的数据的长度，如果返回-1表示因为在文件的末尾所以没有读到数据
   1. `byte[]`是要读取的数据存储的数组
3. `read(byte[] int off, int len)`：参数中的三个参数分别指的是：
   1. `byte[]`：读取出的数据所要存放的地方
   2. `off`：偏移量(offset)，即存放的数据从`byte[off]`开始
   3. `len`：所要读取数据的长度`len`

#### Byte

`ByteArrayInputStream`和`ByteArrayOutputStream`是用来完成内存的输入和输出功能的

#### 管道流

`PipedOutputStream`和`PipedInputStream`。管道流的作用主要是可以进行两个线程间的通信

如果要进行管道通信，则必须把`PipedOutputStream`连接到`PipedInputStream`上。为此，`PipedOutputStream`中提供了`connect()`方法

```java
public class PipedStreamDemo {

    public static void main(String[] args) {
        Send s = new Send();
        Receive r = new Receive();
        try {
            s.getPos().connect(r.getPis()); // 连接管道
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(s).start(); // 启动线程
        new Thread(r).start(); // 启动线程
    }

    static class Send implements Runnable {

        private PipedOutputStream pos = null;

        Send() {
            pos = new PipedOutputStream(); // 实例化输出流
        }

        @Override
        public void run() {
            String str = "Hello World!!!";
            try {
                pos.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 得到此线程的管道输出流
         */
        PipedOutputStream getPos() {
            return pos;
        }

    }

    static class Receive implements Runnable {

        private PipedInputStream pis = null;

        Receive() {
            pis = new PipedInputStream();
        }

        @Override
        public void run() {
            byte[] b = new byte[1024];
            int len = 0;
            try {
                len = pis.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                pis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("接收的内容为：" + new String(b, 0, len));
        }

        /**
         * 得到此线程的管道输入流
         */
        PipedInputStream getPis() {
            return pis;
        }

    }

}
```

### Character Streams

* 输入字符流：`Reader`
* 输出字符流：`Writer`