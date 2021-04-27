package processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JsonFormatProcessor.java
 * @Description TODO
 * @createTime 2021年04月27日 21:58:00
 */
public class JsonFormatProcessor implements formatProcessor{
    @Override
    public String format(Object obj) {
        return JSON.toJSONString(obj);
    }
}
