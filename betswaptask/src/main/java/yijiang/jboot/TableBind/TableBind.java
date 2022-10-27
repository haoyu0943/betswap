package yijiang.jboot.TableBind;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableBind {
    /**表名*/
    String name();
    /**主键名*/
    String pk() default "";
    /**外键名*/
    String fk() default "";
}
