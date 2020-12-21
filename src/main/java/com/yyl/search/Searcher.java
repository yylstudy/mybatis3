package com.yyl.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2020-09-08
 */
public class Searcher implements Serializable,Cloneable{
    public static final String key = "__condition";
    private List<ConditionExpression> conditionExpressions = new ArrayList<>();

    /**
     * 添加条件
     * @param property
     * @param value
     * @param operator
     */
    public void addCondition(String property,String value,OperatorEnum operator){
        ConditionExpression expression = new ConditionExpression();
        expression.setOperator(operator);
        expression.setProperty(property);
        expression.setValue(value);
        conditionExpressions.add(expression);
    }

    public List<ConditionExpression> getConditionExpressions() {
        return conditionExpressions;
    }

    public void setConditionExpressions(List<ConditionExpression> conditionExpressions) {
        this.conditionExpressions = conditionExpressions;
    }
}
