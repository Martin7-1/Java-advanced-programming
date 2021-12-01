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

字符流主要操作字符，一个字符等于两个字节（In Java）

#### File

* `FileReader`和`FileWriter`可以向文件读写文本数据



## NIO

### NIO简介

NIO是一种同步非阻塞的I/O模型，在Java1.4中引入了NIO框架，对应`java.nio`包，提供了`Channel`、`Selector`、`Buffer`等抽象。

NIO 中的 N 可以理解为 Non-blocking，不单纯是New。它支持面向缓冲的，基于通道的 I/O 操作方法。 NIO 提供了与传统 BIO 模型中的 `Socket` 和 `ServerSocket` 相对应的 `SocketChannel` 和 `ServerSocketChannel` 两种不同的套接字通道实现,两种通道都支持阻塞和非阻塞两种模式。阻塞模式使用就像传统中的支持一样，比较简单，但是性能和可靠性都不好；非阻塞模式正好与之相反。对于低负载、低并发的应用程序，可以使用同步阻塞 I/O 来提升开发速率和更好的维护性；对于高负载、高并发的（网络）应用，应使用 NIO 的非阻塞模式来开发。

#### Non-blocking IO(非阻塞)

**BIO 是阻塞的，NIO 是非阻塞的**。

BIO 的各种流是阻塞的。这意味着，当一个线程调用 `read()` 或 `write()` 时，该线程被阻塞，直到有一些数据被读取，或数据完全写入。在此期间，该线程不能再干其他任何事。

NIO 使我们可以进行非阻塞 IO 操作。比如说，单线程中从通道读取数据到 buffer，同时可以继续做别的事情，当数据读取到 buffer 中后，线程再继续处理数据。写数据也是一样的。另外，非阻塞写也是如此。一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情。

#### Buffer(缓冲区)

**BIO 面向流(Stream oriented)，而 NIO 面向缓冲区(Buffer oriented)**。

Buffer 是一个对象，它包含一些要写入或者要读出的数据。在 NIO 类库中加入 Buffer 对象，体现了 NIO 与 BIO 的一个重要区别。在面向流的 BIO 中可以将数据直接写入或者将数据直接读到 Stream 对象中。虽然 Stream 中也有 Buffer 开头的扩展类，但只是流的包装类，还是从流读到缓冲区，而 NIO 却是直接读到 Buffer 中进行操作。

在 NIO 厍中，所有数据都是用缓冲区处理的。在读取数据时，它是直接读缓冲区中的数据; 在写入数据时，写入到缓冲区中。任何时候访问 NIO 中的数据，都是通过缓冲区进行操作。

最常用的缓冲区是 ByteBuffer,一个 ByteBuffer 提供了一组功能用于操作 byte 数组。除了 ByteBuffer,还有其他的一些缓冲区，事实上，每一种 Java 基本类型（除了 Boolean 类型）都对应有一种缓冲区。

#### Channel (通道)

NIO 通过 Channel（通道） 进行读写。

通道是双向的，可读也可写，而流的读写是单向的。无论读写，通道只能和 Buffer 交互。因为 Buffer，通道可以异步地读写。

#### Selector (选择器)

NIO 有选择器，而 IO 没有。

选择器用于使用单个线程处理多个通道。因此，它需要较少的线程来处理这些通道。线程之间的切换对于操作系统来说是昂贵的。 因此，为了提高系统效率选择器是有用的。

### NIO 的基本流程

通常来说 NIO 中的所有 IO 都是从 Channel（通道） 开始的。

- 从通道进行数据读取 ：创建一个缓冲区，然后请求通道读取数据。
- 从通道进行数据写入 ：创建一个缓冲区，填充数据，并要求通道写入数据。

### NIO 核心组件

NIO 包含下面几个核心的组件：

- **Channel(通道)**
- **Buffer(缓冲区)**
- **Selector(选择器)**







## Reference

1. [JAVACORE](https://dunwu.github.io/javacore/io/java-nio.html#nio-%E5%92%8C-bio-%E7%9A%84%E5%8C%BA%E5%88%AB)

2. Thinking in Java
3. On Java 8
4. 南京大学2021秋季《Java高级程序设计》