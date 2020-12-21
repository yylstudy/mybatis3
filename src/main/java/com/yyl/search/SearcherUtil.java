package com.yyl.search;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2020-09-08
 */
public class SearcherUtil {
    /**
     * 这种方式存在sql注入的风险
     * @param searcher
     * @return
     */
    public static String resolveSearch(Searcher searcher){
        StringBuilder queryCondition = new StringBuilder();
        int i = 0;
        for(ConditionExpression expression:searcher.getConditionExpressions()){
            if(i!=0){
                queryCondition.append(" and ");
            }
            queryCondition.append(expression.getProperty())
                    .append(expression.getOperator().getCode())
                    .append("'"+expression.getValue()+"'");
            i++;
        }
        return queryCondition.toString();
    }
}
