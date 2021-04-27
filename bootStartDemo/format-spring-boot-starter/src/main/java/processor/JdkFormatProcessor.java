package processor;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JdkFormatProcessor.java
 * @Description TODO
 * @createTime 2021年04月27日 22:01:00
 */
public class JdkFormatProcessor implements formatProcessor {

    @Override
    public String format(Object obj) {
        return obj.toString();
    }
}
