# Java流式编程
> 集合优化了对象的存储，而流（Streams）则是关于一组组对象的处理。

流（Streams）是与任何特定存储机制无关的元素序列——实际上，我们说流是"**没有存储的**"。

取代了在集合中迭代元素的做法，使用流即可从管道中提取元素并对其操作。这些管道通常被**串联**在一起形成一整套的管线，来对流进行操作。 

流的核心好处：使程序更加短小并且更加容易被理解。我们通常将Lambda表达式，方法引用（method references）和流一起使用。

## 代码示例：

下面我们用流来随机展示5至20之间不重复的整数并进行排序

```java
// streams/Randoms.java
import java.util.*;

public class Randoms {
    public static void main(String[] args) {
        new Random(47)
            .ints(5, 20)
            .distinct()
            .limit(7)
            .sorted()
            .forEach(System.out::println);
    }
}
```

1. `Random(47)`是给**Random**对象一个种子值47，目的是为了让程序再次运行时时产生相同的输出。
2. `ints()`方法产生一个流并且有多种重载 -- 该示例中的两个参数限定了产生的数值的边界。这将生成一个随机整数流。
3. *流的中间操作*（intermediate stream operation）`distinct()`可以使流中的整数不重复。
4. `limit(int size)`方法的参数可以获得前**size**个元素。
5. `sorted()`方法是用来对流中的元素进行排序的。
6. `forEach()`方法便利输出，它会根据传递给它的函数对流中的每个对象执行操作，在这里我们传递了一个可以在控制台显示每个元素的方法引用：`System.out::println`。

> 注意我们在该示例代码中并没有声明任何的变量。这就是流的好处了，可以在不曾使用赋值或者可变数据的情况下，对有状态的系统进行建模，这非常的有用。

## 声明式编程（Declarative programming）
*声明式编程*（Declarative programming）是一种编程风格 -- 它声明了要做什么，而不是指明（每一步）如何做。这正是我们在函数式编程中所看到的（编程风格）。命令式编程（Imperative programming）的形式（指明每一步如何做）会更难理解，我们来看以下的示例代码：

### 示例代码
```java
// streams/ImperativeRandoms.java
import java.util.*;

public class ImperativeRandoms {
    public static void main(String[] args) {
        Random rand = new Random(47);
        // 使用命令式编程，我们并不能直接的按照我们想做的步骤来实现程序
        SortedSet<Integer> rints = new TreeSet<>();
        
        while(rings.size() < 7) {
            // 生成一个0-20的整数
            int r = rand.nextInt(20);
            if (r < 5) {
                continue;
            }
            rints.add(r);
        }
        System.out.println(rints);
    }
}
```

我们可以来对比一下`Randoms.java`和`ImperativeRandoms.java`的过程，在前者的代码之中，我们并不需要定义任何变量，但在后者中我们定义了三个变量：`rand`, `rints`, `r`。这两段代码**最主要**的差别并不是我们定义变量多少的区别，而是我们在这两段代码中的实现思路是不同的，在前者的代码中我们可以十分清晰的知道代码在做什么：
> `Random`创建了整数，并且是互不相同的，同时将数量限制在了7个，然后排序，最后输出。

我们可以通过声明式编程来清晰的观察代码究竟在干什么，在`Random.java`中，我们看不到任何的迭代过程，因为流中的迭代过程是在内部进行的，这被称为**内部迭代（internal iteration）**，这是流式编程的一个**核心特征**。而在`ImperativeRandoms.java`中那样显式编写迭代过程的方式被称为**外部迭代（external iteration）**。内部迭代产生的代码可读性更强，而且可以使用**并行流**来简单的使用并发编程来使用多核处理器。

另一个重要方面，流是**懒加载**的。这代表着它只在绝对必要时才计算。你可以将流看作"延迟列表"。由于延迟计算，流能够表示非常大（甚至无限）的序列，而不需要考虑内存的问题。

## 流支持

全新的概念往往意味着对以往机制兼容的困难，特别是在实现了接口的类之中，如果Java开发者在某个接口内直接的加入了关于流的接口，那么原本实现了该接口的类没有实现我们新增加的方法，就会出错。

因此Java8采用的解决方案是：在接口中添加被`default`（默认）修饰的办法。通过这种方案，就可以将流式方法添加到现有类之中，而且可以兼容早期的各种版本。流方法预置的操作几乎可以满足我们平常所有的需求。流操作类型有以下三种：

1. 创建流
2. 修改流元素（中间操作，Intermediate Operations）
3. 消费流元素（终端操作，Terminal Operations）。

最后一种类型意味着收集流元素（通常是汇入一个集合）。

## 流创建
1. `Stream.of()`

我们可以通过`Stream.of()`很容易地将一组元素转化成为流，我们来看以下的示例代码：

### 示例代码
```java
// streams/StreamOf.java

import java.util.stream.*;

public class StreamOf {
    public static void main(String[] args) {
        Stream.of(new Bubble(1), new Bubble(2), new Bubble(3))
            .forEach(System.out::println);
        Stream.of("It's ", "a ", "wonderful ", "day ", "for ", "pie!")
            .forEach(System.out::print);
        System.out.println()
        Stream.of(3.14159, 2.718, 1.618)
            .forEach(System.out::println);
    }
}
```

除此之外，每个集合都可以通过调用`stream()`方法来产生一个流：

### 示例代码
```java
// streams/CollectionToStream.java
import java.util.*;
import java.util.stream.*;

public class CollectionToStream {
    public static void main(String[] args) {
        List<Bubble> bubbles = Arrays.asList(new Bubble(1), new Bubble(2), new Bubble(3));
        System.out.println(bubbles.stream
            .mapToInt(b -> b.i)
            .sum());
            
        Set<String> w = new HashSet<>(Arrays.asList("It's a wonderful day for pies!".split(" ")));
        w.stream()
         .map(x -> x + " ")
         .forEach(System.out::print);
        System.out.println();
        
        Map<String, Double> m = new HashMap<>();
        m.put("pi", 3.14159);
        m.put("e", 2.718);
        m.put("phi", 1.618);
        m.entrySet().stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .forEach(System.out::println);
    }
}
```

在创建`List<Bubble>`对象之后，对集合对象我们只需要简单调用`stream()`。中间操作`map()`会获取流中的所有元素，并且对流中元素应用操作从而产生新的元素，并将其传递到后续的流中。通常`map()`会获取对象并产生新的对象，但这里我们使用了产生特殊的用于数值类型的流：`mapToInt()`方法会将一个对象流转换成为包含整型数字的`IntStream`。



### 随机数流

`Random`类被一组生成流的方法增强了。我们来看以下代码：

#### 实例代码

```java
package com.nju.edu.generate;

import java.util.*;
import java.util.stream.*;

public class RandomGenerators {

    public static <T> void show(Stream<T> stream) {
        stream.limit(4).forEach(System.out::println);
        System.out.println("++++++++");
    }
    
    public static void main(String[] args) {
        Random rand = new Random(47);
        show(rand.ints().boxed());
        show(rand.longs().boxed());
        show(rand.doubles().boxed());

        // 控制上限和下限:
        show(rand.ints(10, 20).boxed());
        show(rand.longs(50, 100).boxed());
        show(rand.doubles(20, 30).boxed());

        // 控制流大小
        show(rand.ints(2).boxed());
        show(rand.longs(2).boxed());
        show(rand.doubles(2).boxed());

        // 控制流的大小和界限
        show(rand.ints(3, 3, 9).boxed());
        show(rand.longs(3, 12, 22).boxed());
        show(rand.doubles(3, 11.5, 12.3).boxed());
    }
}
```

####