# Java流（Stream）

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

#### 示例代码

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

#### 代码分析

1. 用泛型方法`show(Stream<T> stream)`来实现代码复用，虽然`Random`类型只能够生成基本类型的流，但是`stream`提供了`boxed()`流操作将基本类型自动包装成为对应的装箱类型，从而使得`show()`能够接受流。



### 循环

`IntStream`类提供了`range()`方法用于生成整型序列的流。我们可以来看一下使用了流之后我们的循环代码会有怎样的变化：

#### 示例代码

```java
package com.nju.edu.generate;

import static java.util.stream.IntStream.*;

public class Ranges {

    public static void main(String[] args) {
        int result = 0;

        // 最普通的循环
        for (int i = 10; i < 20; i++) {
            result += i;
        }

        System.out.println("use normal loop, result = " + result);

        int rangeResult = 0;
        for (int i : range(10, 20).toArray()) {
            rangeResult += i;
        }

        System.out.println("apply stream to loop, rangeResult = " + rangeResult);

        // 或者我们还可以更简单一点
        // 使用流的好处
        System.out.println("the simple way to use IntStream.range() = " + range(10, 20).sum());
    }
    
}
```

#### 代码分析

1. 第一种循环是我们传统编写`for`循环的方式。
2. 第二种方式，我们使用了`range()`来创建了流并且用`toArray()`将其转化成了数组，然后再`for-in`代码块中使用。
3. 第三种方式是使用流的最好方式，直接用`sum()`方法来对`range()`创建的流中的数字进行操作求和。
4. 我们可以看到我们使用了`import static`，如果不用的话`range()`需要用类名来调用。



### generate()

```java
package com.nju.edu.generate;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Generator implements Supplier<String> {
    Random rand = new Random(47);
    char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    
    public String get() {
        return "" + letters[rand.nextInt(letters.length)];
    }
    
    public static void main(String[] args) {
        String word = Stream.generate(new Generator())
                            .limit(30)
                            .collect(Collectors.joining());
        System.out.println(word);
    }
}

```



### iterate()

我们来看一下文档中对该方法的解释：

> `static IntStream iterate(int seed, IntUnaryOperator f)`
>
> Returns an infinite sequential ordered IntStream produced by iterative application of a function f to an initial element seed, producing a Stream consisting of seed, f(seed), f(f(seed)), etc.

可以看到该方法实际上是将第一个参数作为**初始值**，传入到第二个参数（实际上是一个方法），然后再将该方法返回的值作为第一个参数继续执行下去。因此该方法实际上是一个不断迭代的过程，我们可以用该方法来很简单的实现斐波那契数列（注意与`limit(int newLimit)`相结合），代码如下：

#### 示例代码

```java
package com.nju.edu.iterate;

import java.util.stream.Stream;

public class Fibonacci {

    int x = 1;

    public Stream<Integer> iterateNumber() {
        return Stream.iterate(0, i -> {
            int result = x + i;
            x = i;
            return result;
        });
    }

    public static void main(String[] args) {
        new Fibonacci().iterateNumber()
                        .skip(20) // 跳过前20个
                        .limit(10) // 将数量限制在10个
                        .forEach(System.out::println);
    }
    
}
```

#### 代码分析

1. 斐波那契数列有两个需要记录的变量，因此这里我们需要用`x`来记录另一个变量
2. 这里使用了`skip()`方法，它会根据参数来丢弃指定数量的流元素

### Arrays.stream()

我们可以使用`Arrays.stream()`来将数组转化成流，看以下示例代码：

#### 示例代码

```java
package com.nju.edu.arrays;

import java.util.Arrays;

public class ArraysStream {

    public static void main(String[] args) {
        Arrays.stream(new double[] {
            3.14159, 2.718, 1.618
        }).forEach(n -> System.out.printf("%f ", n));

        System.out.println();

        Arrays.stream(new int[] {
            1, 3, 5, 7, 9, 11
        }).forEach(n -> System.out.printf("%d ", n));

        System.out.println();

        // 选择一个子数组进行输出
        // 这里选择了[3, 6)的子数组进行输出，注意左闭右开
        Arrays.stream(new int[] {
            1, 5, 9, 56, 32, 22, 95, 65
        }, 3, 6).forEach(n -> System.out.printf("%d ", n));
    }
    
}
```



## 中间操作

中间操作用于从一个流中获取对象，并将对象作为另一个流从后端输出，以连接到其他操作。

### 跟踪和调试 peek()

`peek()`操作的目的是帮助调试。它允许你无修改的查看流中的元素。

### 流元素排序 sorted()

`sorted()`在前面已经用到过了，它能够对流中的元素进行排序，除此之外，`sorted()`还能够传入一个`Comparator`参数来实现自己定义的比较。

### 移除元素 distinct()和filter()

1. `distinct()`也在前面已经用过了，它用来消除流中的重复元素。相比创建一个`Set`来消除重复，这种方法的工作量要少的多。
2. `filter(Predicate)`顾名思义，就是进行过滤操作，保留传递给过滤函数后产生的结果为`true`的元素

对于`filter()`的用法，我们用以下示例来说明，该示例用了`isPrime()`来判断某个元素是不是质数，该方法会作为`filter()`的过滤函数。

#### 示例代码

```java
package com.nju.edu.operation;

import java.util.stream.*;
import static java.util.stream.LongStream.*;

public class Prime {

    public static boolean isPrime(long n) {
        // 判断一个数是不是质数
        // 最朴素的方法，牛顿平方根
        return rangeClosed(2, (long)(Math.sqrt(n))).noneMatch(i -> n % i == 0);
    }

    public LongStream numbers() {
        return iterate(2, i -> i + 1).filter(Prime::isPrime);
    }

    public static void main(String[] args) {
        new Prime().numbers()
        .limit(10)
        .forEach(n -> System.out.printf("%d ", n));

        System.out.println();

        new Prime().numbers()
        .skip(90)
        .limit(10)
        .forEach(n -> System.out.printf("%d ", n));
    }
    
}
```

#### 代码分析

1. `rangeClosed()`相比`range()`的区别在于前者的区间是左闭右闭的，而后者是左闭右开的。
2. `noneMatch()`是只要其参数中有一次lambda表达式不满足的话就返回`false`，在这个例子中就是只要有一次余数为0，那么就返回`false`。同时，只要有一次是`false`，那么该方法就会退出。
3. 在这段代码中我们使用了`filter(Prime::isPrime)`来作为过滤器移除元素，`isPrime`是过滤函数，这里用方法引用来体现。



### 应用函数到元素 map()

接下来要说的可能是中间操作中最重要的一种操作：`map(Function)`，该方法将函数操作应用在输入流的元素中，并将返回值传递到输出流中。

有几类比较特殊的`map()`

1. `mapToInt(ToIntFunction)`，返回的结果是`IntStream`
2. `mapToLong(ToLongFunction)`，返回的结果是`LongStream`
3. `mapToDouble(ToDoubleFunction)`，返回的结果是`DoubleStream`

#### 示例代码

```java
package com.nju.edu.operation;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class FunctionMap {
    static String[] elements = {"12", "23", "45"};

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String description, Function<String, String> func) {
        System.out.println(" ---( " + description + " )--- ");
        testStream().map(func).forEach(System.out::println);
    }

    public static void main(String[] args) {
        test("add brackets", s -> "[" + s + "]");

        test("Increment", s -> {
            try {
                return Integer.parseInt(s) + 1 + "";
            } catch (NumberFormatException e) {
                return s;
            }
        });

        test("replace", s -> s.replace("2", "9"));

        test("Take last digit", s -> s.length() > 0 ? s.charAt(s.length() - 1) + "" : s);
    }
}
```

除了`map()`之外，我们还有`flatMap()`来每个流都“扁平化”为元素（这里我理解为将每个元素都从流中取出）。其一开始的功能与`map()`大同小异，两者相差的仅仅是最后`map()`返回的是一个流，而`flatMap()`产生的仅仅是元素而已。



## Optional类

> skip



## 终端操作

> 终端操作（Terminal Operations）总是我们在流管道中所做的最后一件事

### 数组

* `toArray()`：将流转换成适当类型的数组
* `toArray(generator)`：在特殊情况下，生成自定义类型的数组

当我们需要得到数组类型的数据以便于后续操作时，上面的方法就很有用。假设我们需要复用流产生的随机数时，就可以这么使用。

#### 示例代码

```java
package com.nju.edu.terminal;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class RandInts {

    private static int[] rints = new Random(47).ints(0, 1000).limit(100).toArray();

    public static IntStream rands() {
        return Arrays.stream(rints);
    }
    
}
```

上例将100个数值范围在 0 到 1000 之间的随机数流转换成为数组并将其存储在 `rints` 中。这样一来，每次调用 `rands()` 的时候可以重复获取相同的整数流。



### 循环

* `forEach(Consumer)`，常见如`System.out::println`作为**Consumer**函数
* `forEachOrdered(Consumer)`：保证`forEach`按照原始流顺序操作

#### 示例代码

```java
package com.nju.edu.terminal;

import static com.nju.edu.terminal.RandInts.*;

public class ForEach {

    static final int SZ = 14;

    public static void main(String[] args) {
        rands().limit(SZ)
                .forEach(n -> System.out.format("%d ", n));
        System.out.println();

        rands().limit(SZ)
                .parallel()
                .forEach(n -> System.out.format("%d ", n));
        System.out.println();
        
        rands().limit(SZ)
                .parallel()
                .forEachOrdered(n -> System.out.format("%d ", n));
    }
    
}
```

为了方便测试不同大小的流，我们抽离出了 `SZ` 变量。然而即使 `SZ` 值为14也产生了有趣的结果。在第一个流中，未使用 `parallel()` ，因此以元素从 `rands()`出来的顺序输出结果。在第二个流中，引入`parallel()` ，即便流很小，输出的结果的顺序也和前面不一样。这是由于多处理器并行操作的原因，如果你将程序多运行几次，你会发现输出都不相同，这是多处理器并行操作的不确定性造成的结果。

在最后一个流中，同时使用了 `parallel()` 和 `forEachOrdered()` 来强制保持原始流顺序。因此，对非并行流使用 `forEachOrdered()` 是没有任何影响的。



### 集合

- `collect(Collector)`：使用 **Collector** 收集流元素到结果集合中。
- `collect(Supplier, BiConsumer, BiConsumer)`：同上，第一个参数 **Supplier** 创建了一个新的结果集合，第二个参数 **BiConsumer** 将下一个元素收集到结果集合中，第三个参数 **BiConsumer** 用于将两个结果集合合并起来。

在这里我们只是简单介绍了几个 **Collectors** 的运用示例。实际上，它还有一些非常复杂的操作实现，可通过查看 `java.util.stream.Collectors` 的 API 文档了解。例如，我们可以将元素收集到任意一种特定的集合中。

假设我们现在为了保证元素有序，将元素存储在 **TreeSet** 中。**Collectors** 里面没有特定的 `toTreeSet()`，但是我们可以通过将集合的构造函数引用传递给 `Collectors.toCollection()`，从而构建任何类型的集合。



我们也可以在流中创建`Map`

#### 示例代码

```java
package com.nju.edu.terminal;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pair {
    public final Character c;
    public final Integer i;

    Pair(Character c, Integer i) {
        this.c = c;
        this.i = i;
    }

    public Character getC() {
        return this.c;
    }

    public Integer getI() {
        return this.i;
    }

    @Override
    public String toString() {
        return "Pair(" + c + ", " + i + ")";
    }
}

class RandomPair {
    Random rand = new Random(47);
    // an infinite iterator of random capital letters
    Iterator<Character> capChars = rand.ints(65, 91)
            .mapToObj(i -> (char)i)
            .iterator();
    
    public Stream<Pair> stream() {
        return rand.ints(100, 1000).distinct()
                .mapToObj(i -> new Pair(capChars.next(), i));
    }
}

public class MapCollector {
    
    public static void main(String[] args) {
        Map<Integer, Character> map = new RandomPair().stream().limit(8)
                    .collect(Collectors.toMap(Pair::getI, Pair::getC));
        
        System.out.println(map);
    }
}
```

**Pair** 只是一个基础的数据对象。**RandomPair** 创建了随机生成的 **Pair** 对象流。在 Java 中，我们不能直接以某种方式组合两个流。所以我创建了一个整数流，并且使用 `mapToObj()` 将整数流转化成为 **Pair** 流。 **capChars**的随机大写字母迭代器创建了流，然后`next()`让我们可以在`stream()`中使用这个流。就我所知，这是将多个流组合成新的对象流的唯一方法。

在这里，我们只使用最简单形式的 `Collectors.toMap()`，这个方法只需要两个从流中获取键和值的函数。还有其他重载形式，其中一种当是键发生冲突时，使用一个函数来处理冲突。



### 组合

- `reduce(BinaryOperator)`：使用 **BinaryOperator** 来组合所有流中的元素。因为流可能为空，其返回值为 **Optional**。
- `reduce(identity, BinaryOperator)`：功能同上，但是使用 **identity** 作为其组合的初始值。因此如果流为空，**identity** 就是结果。
- `reduce(identity, BiFunction, BinaryOperator)`：更复杂的使用形式（暂不介绍），这里把它包含在内，因为它可以提高效率。通常，我们可以显式地组合 `map()` 和 `reduce()` 来更简单的表达它。

#### 代码示例

```java
package com.nju.edu.terminal;

import java.util.Random;
import java.util.stream.Stream;

class Frobnitz {
    int size;

    public Frobnitz(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Frobnitz(" + size + ")";
    }

    // Generator
    static Random rand = new Random(47);
    static final int BOUND = 100;

    static Frobnitz supply() {
        return new Frobnitz(rand.nextInt(BOUND));
    }
}

public class Reduce {
    
    public static void main(String[] args) {
        Stream.generate(Frobnitz::supply)
                .limit(10)
                .peek(System.out::println)
                .reduce((fr0, fr1) -> fr0.size < 50 ? fr0 : fr1)
                .ifPresent(System.out::println);
    }
}
```

我们使用了没有“初始值”作为第一个参数的 `reduce()`方法，所以产生的结果是 **Optional** 类型。`Optional.ifPresent()` 方法只有在结果非空的时候才会调用 `Consumer<Frobnitz>` （`println` 方法可以被调用是因为 **Frobnitz** 可以通过 `toString()` 方法转换成 **String**）。

Lambda 表达式中的第一个参数 `fr0` 是 `reduce()` 中上一次调用的结果。而第二个参数 `fr1` 是从流传递过来的值。

`reduce()` 中的 Lambda 表达式使用了三元表达式来获取结果，当 `fr0` 的 `size` 值小于 50 的时候，将 `fr0` 作为结果，否则将序列中的下一个元素即 `fr1`作为结果。当取得第一个 `size` 值小于 50 的 `Frobnitz`，只要得到这个结果就会忽略流中其他元素。这是个非常奇怪的限制， 但也确实让我们对 `reduce()` 有了更多的了解。

























