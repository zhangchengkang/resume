package com.resume.util.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 功能：一些通用的与对象相关的操作。 创建者: Cui WenKe 修改者 修改时间
 */
public class ObjectUtil {
    private ObjectUtil() {
    }

    /**
     * 判别两个对象是否相同。
     *
     * @param o1 被判断的第1个对象
     * @param o2 被判断的第2个对象
     * @功能: =true 相等   =false 不相等
     */
    public static boolean equals(Object o1, Object o2) {
        return ((o1 == o2) || ((o1 != null) && (o1.equals(o2))));
    }

    /**
     * 获取不含有包名称的类名称
     *
     * @param className 带包名的类名称
     * @功能: 类名
     */
    public static String getBaseClassName(String className) {
        return StringTool.getLastSuffix(className, ".");
    }

    /**
     * 获取调用的包名称
     *
     * @param className 类名称
     * @功能: 包名称
     */
    public static String getPackageName(String className) {
        return StringTool.getLastPrefix(className, ".");
    }

    /**
     * 获取方法名称
     *
     * @param whole 带类名的方法名称
     * @功能: 方法名
     */
    public static String getMethodName(String whole) {
        return StringTool.getLastSuffix(whole, ".");
    }

    public static List<String> getMethodList(Class c) {
        List<String> methodList = new ArrayList<>();
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            methodList.add(method.getName());
        }
        return methodList;
    }

    public static List<String> getFieldList(Class c) {
        List<String> fieldList = new ArrayList<>();
        Field[] fields = c.getFields();
        for (Field field : fields) {
            fieldList.add(field.getName());
        }
        return fieldList;
    }

    /**
     * 获取对象的hash码。
     *
     * @param o 需要获取hash码的对象
     * @功能: =0 对象为空，否则为hash码
     */
    public static int hashCode(Object o) {
        return ((null == o) ? 0 : o.hashCode());
    }

    /**
     * 获取对象的属性
     *
     * @param object    对象
     * @param sProperty 对象的Bean属性
     * @功能: 对象的属性方法
     */
    public static Object getProperty(Object object, String sProperty) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (null == object || null == sProperty) {
            return null;
        }
        Method method = object.getClass().getMethod("get" + StringTool.upperFirst(sProperty));
        return method.invoke(object);
    }

    /**
     * 将对象转换成字符串输出
     *
     * @param o 对象
     * @功能: 字符串
     */
    public static String toString(Object o) {
        if (null == o) {
            return "";
        }
        if (o instanceof Object[]) {
            return toArrayString((Object[]) o);
        } else if (o instanceof int[]) {
            return NumberUtil.toString((int[]) o);
        } else if (o instanceof long[]) {
            return NumberUtil.toString((long[]) o);
        } else if (o instanceof char[]) {
            return StringTool.toString((char[]) o);
        } else {
            return o.toString();
        }
    }

    /**
     * 将数组转换为字符串。
     *
     * @param array 数组
     * @功能: 字符串
     */
    public static String toArrayString(Object[] array) {
        if (null == array) {
            return "count=0:[]";
        }
        StringBuilder s = new StringBuilder("count=").append(array.length).append(":[ ");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                s.append(", ");
            }
            Object obj = array[i];
            if (null == obj) {
                continue;
            } else if (obj instanceof Object[]) {
                s.append(ObjectUtil.toString((Object[]) obj));
            } else {
                s.append(obj.toString());
            }
        }
        s.append(" ]");
        return s.toString();
    }

    /**
     * 方法用途： 判断一个实体类是否存在某个方法名 <br>
     * 人员：崔文科<br>
     * 时间：2011-8-11<br>
     * 操作：创建<br>
     * 操作 内容：创建方法<br>
     * <p/>
     * 参数说明：
     *
     * @param classz     :实体类
     * @param methodName ：方法名
     */
    public static Boolean containsMethod(Class<?> classz, String methodName) {
        Boolean falg = false;
        Method[] methods = classz.getMethods();
        for (Method method : methods) {
            if (StringTool.equalsVal(methodName, method.getName())) {
                falg = true;
                break;
            }
        }
        return falg;
    }

    public static Boolean isNotNull(Object obj) {
        Boolean falg = null != obj;
        if (falg) {
            falg = StringTool.noEmpty(obj.toString());
        }
        return falg;
    }

    public static String getFieldValue(Object object, String fieldName) {
        String methodName = "get" + StringTool.upperFirst(fieldName);
        if (ObjectUtil.containsMethod(object.getClass(), methodName)) {
            try {
                Method m = object.getClass().getMethod(methodName);
                Object val = m.invoke(object);
                if (val instanceof java.util.Date) {
                    java.util.Date new_name = (java.util.Date) val;
                    val = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new_name);
                }
                if (val instanceof List) {
                    val = ((List) val).get(0).toString();
                }
                return null == val ? new String() : val.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String();
    }

    public static Object invokeNewObjectMethod(String objectClassName, String methodName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = Class.forName(objectClassName);
        Object object = clazz.newInstance();
        Method method = clazz.getMethod(methodName, new Class[]{});
        return method.invoke(object, new Object[]{});
    }

    public static void invokeSetGetMethod(Object object, String fieldName, Object valueObj) {
        String setMethodName = "set" + StringTool.upperFirst(fieldName);
        String getMethodName = "get" + StringTool.upperFirst(fieldName);
        if (ObjectUtil.containsMethod(object.getClass(), setMethodName) && ObjectUtil.containsMethod(object.getClass(), getMethodName)) {
            try {
                Method m = object.getClass().getMethod(getMethodName);
                Method method = object.getClass().getMethod(setMethodName, m.getReturnType());
                method.invoke(object, valueObj);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object typeConvert(Object instance, String fieldName, String fieldValue) {
        Object value = fieldValue;
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (StringTool.equalsVal(fieldName, field.getName())) {
                switch (field.getType().getName()) {
                    case "java.lang.BigDecimal":
                        value = new BigDecimal(fieldValue);
                        break;
                    case "java.lang.Integer":
                        value = Integer.valueOf(fieldValue);
                        break;
                    case "java.lang.Double":
                        value = Double.valueOf(fieldValue);
                        break;
                    case "java.lang.Long":
                        value = Long.valueOf(fieldValue);
                        break;
                    case "java.lang.Boolean":
                        value = Boolean.valueOf(fieldValue);
                        break;
                    case "java.lang.Float":
                        value = Float.valueOf(fieldValue);
                        break;
                }
                break;
            }
        }
        return value;
    }

    /**
     * 方法用途： 根据文件路径获取资源配置文件 <br>
     * 人员：崔文科<br>
     * 时间：2011-7-4<br>
     * 操作：创建<br>
     * 操作 内容：创建方法<br>
     * <p/>
     * 参数说明：
     *
     * @param propertiesFileUrl =文件路径
     */
    public static Properties getPropertiesInfo(String propertiesFileUrl) throws IOException {
        // 声明解析对象
        Properties props = new Properties();
        InputStream in = null;
        in = ObjectUtil.class.getResourceAsStream(propertiesFileUrl);
        props.load(in);
        if (in != null) {
            in.close();
        }
        return props;
    }

    public static Properties getPropertiesInfo(InputStream in) throws IOException {
        // 声明解析对象
        Properties props = new Properties();
        props.load(in);
        if (in != null) {
            in.close();
        }
        return props;
    }
}
