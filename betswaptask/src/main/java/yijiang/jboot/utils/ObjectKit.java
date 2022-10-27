package yijiang.jboot.utils;

import com.jfinal.kit.ReflectKit;

import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ObjectKit {
    private static final String NULL = "null";
    private static final String EMPTY_STR = "";

    /**
     * 定义toString时忽略的属性名称字符串集合
     */
    private static final String[] IGNORE_FIELD_NAMES = {"log", "logger",
            "serialVersionUID", // 实现序列化接口时自动添加的字段
    };

    /**
     * 定义toString时忽略的属性类名字符串集合
     */
    private static final String[] IGNORE_FIELD_CLASSES = {
            "org.apache.log4j.Logger",
            "org.slf4j.Logger",
            "org.slf4j.LoggerFactory",
            "java.kit.logging.Logger", // 日志记录器声明的属性
            "java.lang.Object", "java.lang.Class",
            "java.lang.reflect.AccessibleObject", "java.lang.reflect.Field",
            "java.lang.reflect.Method", "java.lang.reflect.Constructor",};

    // =====================================================
    // isNull(); isNotNull();
    // isEmpty(); isNotEmpty();
    // =====================================================

    /**
     * 判断指定的对象是否为null。
     *
     * @param obj 要判断的对象实例
     * @return true：为null false：非null
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断指定的对象是否不为null。
     *
     * @param obj 要判断的对象实例
     * @return true：非null false：为null
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断指定的对象数组是否为空。
     *
     * @param objs 要判断的对象数组实例
     * @return true：对象数组为空 false：对象数组非空
     */
    public static boolean isEmpty(Object[] objs) {
        return isNull(objs) || objs.length == 0;
    }

    /**
     * 判断指定的对象数组是否非空。
     *
     * @param objs 要判断的对象数组实例
     * @return true：对象数组非空 false：对象数组为空
     */
    public static boolean isNotEmpty(Object[] objs) {
        return !isEmpty(objs);
    }

    /**
     * 判断指定的集合类是否为空。<BR>
     * 为空的含义是指：该集合为null，或者该集合不包含任何元素。
     *
     * @param coll 要判断的集合实例
     * @return true：集合为空 false：集合非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * 判断指定的集合类是否非空。 <BR>
     * 非空的含义是指：该集合非null并且该集合包含元素。
     *
     * @param coll 要判断的集合实例
     * @return true：集合非空 false：集合为空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断指定的Map类是否为空。 <BR>
     * 为空的含义是指：该Map为null，或者该Map不包含任何元素。
     *
     * @param map 要判断的Map实例
     * @return true：Map为空 false：Map非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断指定的Map类是否非空。<BR>
     * 非空的含义是指：该Map非null并且该Map包含元素。
     *
     * @param map 要判断的Map实例
     * @return true：Map非空 false：Map为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断指定的CharSequence类是否为空。 <BR>
     * 为空的含义是指：该CharSequence为null，或者该CharSequence长度为0。<BR>
     * 对String类型，虽然也属于CharSequence，<BR>
     * 但因为通常字符串都有特殊的处理要求，所以使用方法isEmpty(String str);
     *
     * @param charSeq 要判断的CharSequence实例
     * @return true：CharSequence为空 false：CharSequence非空
     */
    public static boolean isEmpty(CharSequence charSeq) {
        return isNull(charSeq) || charSeq.length() == 0;
    }

    /**
     * 判断指定的CharSequence类是否非空。 <BR>
     * 非空的含义是指：该CharSequence非null并且该CharSequence包含元素。<BR>
     * 对String类型，虽然也属于CharSequence， <BR>
     * 但因为通常字符串都有特殊的处理要求，所以使用方法isNotEmpty(String str);
     *
     * @param charSeq 要判断的CharSequence实例
     * @return true：CharSequence非空 false：CharSequence为空
     */
    public static boolean isNotEmpty(CharSequence charSeq) {
        return !isEmpty(charSeq);
    }

    /**
     * 判断指定的String类是否为空。 <BR>
     * 为空的含义是指：该String为null，或者该String为空串""。
     *
     * @param str 要判断的String实例
     * @return true：String为空 false：String非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || EMPTY_STR.equals(str);
    }

    /**
     * 判断指定的String类是否非空。 <BR>
     * 非空的含义是指：该String非null并且该String不为空串。
     *
     * @param str 要判断的String实例
     * @return true：String非空 false：String为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断指定的String类是否为空。<BR>
     * 为空的含义是指：该String为null，或者该String为空串""。
     *
     * @param str 要判断的String实例
     * @return true：String为空 false：String非空
     */
    public static boolean isTrimEmpty(String str) {
        return isNull(str) || EMPTY_STR.equals(str.trim());
    }

    /**
     * 判断指定的String类是否非空。 <BR>
     * 非空的含义是指：该String非null并且该String不为空串。
     *
     * @param str 要判断的String实例
     * @return true：String非空 false：String为空
     */
    public static boolean isTrimNotEmpty(String str) {
        return !isTrimEmpty(str);
    }

    // =============================================================
    // toString()
    // 1. Java 基本型别，包括基本对象型别 直接调用对象本身的toString()
    // 2. 简单的Bean类型 采用反射机制取得每个属性的值
    // 3. Java集合类型 如：Collection，Map以及对象数组
    // 4. 非Java基本型别的对象类型作为属性
    // 5. enum处理：能正确调用object.toString()，归类为Java基本类型
    // 6. 继承关系处理：先取得所有父类，再取得所有属性 递归toString
    // 7. 内部类处理：内部类包含了一个对外部类的默认引用，其名称会包含"this$",忽略即可
    // =============================================================



    public static <T> void addAll(List<T> list, T[] array) {
        if (isNull(list) || isNull(array)) {
            return;
        }

        for (T t : array) {
            list.add(t);
        }
    }



    /**
     * 根据字段的Name判断是否该字段为已定义的忽略属性
     *
     * @param field 需要判断是否忽略属性的字段。
     * @return true：为忽略属性 false：非忽略属性
     */
    private static boolean isIgnoreFieldByName(Field field) {
        // 如果是已定义的需要忽略的属性
        for (String fieldName : IGNORE_FIELD_NAMES) {
            if (fieldName.equals(field.getName())) {
                return true;
            }
        }

        // 如果是需要模糊匹配需要忽略的属性
        // 说明：如果一个类定义了内部类，内部类保留一个对外部类的默认引用
        // 这会导致递归toString时堆栈溢出，而这个默认引用又不需要toString，所以忽略
        return field.getName().indexOf("this$") != -1;

    }



    /**
     * clone方法。需要克隆的对象必须实现Serializable接口
     *
     * @param obj 需要克隆的对象。
     * @return 克隆的对象。
     */
    public static <T> T clone(T obj) {
        if (isNull(obj)) {
            return null;
        }

        T cloneObj = null;
        if (obj instanceof Serializable) {
            ByteArrayOutputStream baos = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;

            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                ois = new ObjectInputStream(new ByteArrayInputStream(baos
                        .toByteArray()));

                cloneObj = (T) ois.readObject();
            } catch (Exception e) {
                return cloneObj;
            } finally {
                try {
                    if (baos != null)
                        baos.close();
                    if (oos != null)
                        oos.close();
                    if (ois != null)
                        ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cloneObj;
    }



}

