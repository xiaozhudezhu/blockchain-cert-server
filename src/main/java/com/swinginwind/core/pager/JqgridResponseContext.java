package com.swinginwind.core.pager;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author <a href="mailto:shao.gq@gener-tech.com">ShaoGuoqing</a>
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class JqgridResponseContext {
    private static ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>();

    protected static Object get(String key) {
        Map<String, Object> map = context.get();
        if (null == map) {
            map = new HashMap<String, Object>();
            context.set(map);
        }
        return map.get(key);
    }

    public static <T> JqgridResponse<T> getJqgridResponse(Class<T> clazz) {
        JqgridResponse<T> jqgridResponse = (JqgridResponse<T>) get("jqgridResponse");
        if (jqgridResponse == null) {
            jqgridResponse = new JqgridResponse<T>();
            JqgridResponseContext.set("jqgridResponse", jqgridResponse);
        }
        return jqgridResponse;
    }

    protected static void set(String key, Object value) {
        Map<String, Object> map = context.get();
        map.put(key, value);
    }

    private JqgridResponseContext() {

    }

}
