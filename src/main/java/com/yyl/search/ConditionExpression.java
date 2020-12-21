package com.yyl.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2020-09-08
 */
@Getter
@Setter
@ToString
public class ConditionExpression implements Serializable {
    private String property;
    private OperatorEnum operator;
    private Object value;
}
