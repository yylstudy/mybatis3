package com.yyl.search;

import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.ognl.PropertyAccessor;
import org.apache.ibatis.scripting.xmltags.DynamicContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description 自定义Searcher的Ognl表达式解析，大致逻辑是如果存在参数为Searcher
 * 那么需要改变Searcher为Map结构，并且包含key为__condition，值为Searcher中条件的解析值
 * @createTime 2020-09-08
 */
public class MyContextAccessor implements PropertyAccessor {
    public static final String PARAMETER_OBJECT_KEY = "_parameter";
    @Override
    public Object getProperty(Map context, Object target, Object name) throws OgnlException {
        Map map = (Map) target;
        Object result = map.get(name);
        if (map.containsKey(name) || result != null) {
            return result;
        }
        Object parameterObject = map.get(PARAMETER_OBJECT_KEY);
        //做个标识，判断是否已经转化过了
        if(!map.containsKey("_searcher_convert")) {
            map.put("_searcher_convert", "Y");
            if(parameterObject instanceof Searcher){
                Map<String,Object> newSearcher = new HashMap<>();
                newSearcher.put(Searcher.key,SearcherUtil.resolveSearch((Searcher)parameterObject));
                map.put(DynamicContext.PARAMETER_OBJECT_KEY, parameterObject);
            }else if(parameterObject instanceof Map){
                Map<String,Object> param = (Map)parameterObject;
                for(Map.Entry entry : param.entrySet()){
                    if(entry.getValue() instanceof Searcher){
                        String queryCondition = SearcherUtil.resolveSearch((Searcher)entry.getValue());
                        Map<String,Object> newMap = new HashMap<>();
                        newMap.put(Searcher.key,queryCondition);
                        entry.setValue(newMap);
                    }
                }
            }
        }
        if (parameterObject instanceof Map) {
            return ((Map)parameterObject).get(name);
        }

        return null;
    }

    @Override
    public void setProperty(Map context, Object target, Object name, Object value) {
        Map<Object, Object> map = (Map<Object, Object>) target;
        map.put(name, value);
    }

    @Override
    public String getSourceAccessor(OgnlContext ognlContext, Object o, Object o1) {
        return null;
    }

    @Override
    public String getSourceSetter(OgnlContext ognlContext, Object o, Object o1) {
        return null;
    }
}
