package com.yyl.objectWrapperFactory;

import com.yyl.search.Searcher;
import com.yyl.search.SearcherUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description 对于返回HumpMap的进行下划线转驼峰的转化
 * @createTime 2020-09-03
 */
public class MyObjectWrapperFactory extends DefaultObjectWrapperFactory {

    /**
     * 虽然HumpMap也是Map，但是通过查看MetaObject的构造器可知，hasWrapperFor的方法是在
     * object instanceof Map 之前的
     * @param object
     * @return
     */
    @Override
    public boolean hasWrapperFor(Object object) {
        if(object instanceof HumpMap){
            return true;
        }else if(object instanceof Searcher){
            return true;
        }else if(object instanceof Map){
            return ((Map) object).values().stream().anyMatch(obj->obj instanceof Searcher);
        }

        return super.hasWrapperFor(object);
    }

    /**
     * 获取ObjectWrapper 这里需要注意的是如果是对mapper中的查询结果进行转化，
     * 那么此时object是一个只执行了空构造的对象，所以不能再这里对其原有的key进行转化
     * @param metaObject
     * @param object
     * @return
     */
    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if(object instanceof HumpMap){
            HumpMap<String, Object> map = (HumpMap)object;
            return new MyMapWrapper(metaObject, map);
        }else if(object instanceof Searcher){
            String queryCondition = SearcherUtil.resolveSearch((Searcher)object);
            Map<String,Object> param = new HashMap<>();
            param.put(Searcher.key,queryCondition);
            Map<String,Object> paramMap = new MapperMethod.ParamMap<>();
            paramMap.put("searcher",param);
            return  new MapWrapper(metaObject, paramMap);
        }else if(object instanceof Map){
            Map<String,Object> paramMap = (Map)object;
            String updateKey = null;
            String addCondition = "";
            for(Map.Entry<String,Object> entry:paramMap.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof Searcher){
                    updateKey = key;
                    addCondition = SearcherUtil.resolveSearch((Searcher)value);
                    break;
                }
            }
            if(StringUtils.isNotBlank(addCondition)){
                Map<String,Object> newSearch = new HashMap();
                newSearch.put(Searcher.key,addCondition);
                paramMap.put(updateKey,newSearch);
            }
            return new MapWrapper(metaObject, paramMap);
        }
        return super.getWrapperFor(metaObject,object);
    }





}
