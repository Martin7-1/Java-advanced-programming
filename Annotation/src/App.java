import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class App {

    @MyAnnotation
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    // Retention的三种形式
    // SOURCE：源代码中看的见，其他看不见
    // CLASS:源代码和字节码看得见，其他看不见
    // RUNTIME: 源代码和字节码和运行时都看的见

    // Target:
    // 限制注解能够标识的区域：
    // 如：CLASS/METHOD/FIELD等
}

