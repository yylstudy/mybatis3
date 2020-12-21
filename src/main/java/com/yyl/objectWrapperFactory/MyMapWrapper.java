package com.yyl.objectWrapperFactory;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description 自定义MapWrapper
 * @createTime 2020-09-03
 */
public class MyMapWrapper extends MapWrapper {
    public static final char UNDERLINE = '_';
    /**
     * 通过构造器可知 这个map和其父类中的map是同一个对象
     */
    private final Map<String, Object> superMap;

    public MyMapWrapper(MetaObject metaObject, HumpMap<String, Object> map) {
        super(metaObject,map);
        superMap = map;
    }

    /**
     * 重写set方法，这个方法的调用主要是在对ObjectFactory构造出来的返回结果对象进行赋值
     * @param prop
     * @param value
     */
    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, superMap);
            setCollectionValue(prop, collection, value);
        } else {
            superMap.put(underlineToCamel(prop.getName()), value);
        }
    }

    private String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        param = param.toLowerCase();
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (i == 0) {//首字母大写
                char c1 = param.charAt(0);
                sb.append(Character.toLowerCase(c1)); //此处控制首字母大小写
                continue;
            }
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
