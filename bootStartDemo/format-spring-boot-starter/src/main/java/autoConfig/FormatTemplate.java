package autoConfig;

import processor.formatProcessor;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName autoConfig.FormatTemplate.java
 * @Description TODO
 * @createTime 2021年04月27日 22:09:00
 */
public class FormatTemplate {
    formatProcessor formatProcessor;
    public FormatTemplate(formatProcessor processor){
        this.formatProcessor = processor;
    }

    public String format(Object obj){
        StringBuilder sb = new StringBuilder();
        sb.append("使用的是"+formatProcessor.getClass().getSimpleName()+"进行数据的格式化！</br>");
        sb.append("转换之后的数据是").append(formatProcessor.format(obj));
        return sb.toString();
    }
}
