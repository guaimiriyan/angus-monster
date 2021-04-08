package selfSpring.beans.util;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName BeanNameUtil.java
 * @Description TODO
 * @createTime 2021年03月29日 23:25:00
 */
public class BeanNameUtil {
    public static String toLowerFirst(String name){
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
