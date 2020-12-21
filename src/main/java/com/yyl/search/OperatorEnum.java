package com.yyl.search;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2020-09-08
 */
public enum OperatorEnum {
    EQUALS("=","等于"),
    NOT_EQUALS("<>","不等于"),
    LEFT_LIKE("like","左包含"),
    RIGHT_LIKE("like","右包含"),
    LIKE("like","包含"),
    IS_NULL("is null","空"),
    IS_NOT_NULL("is not null","不为空"),
    GREATER(">","大于"),
    LESS("<","小于"),
    GREATEREQUAL(">=","大于等于"),
    LESSEQAUL("<=","小于等于"),
    ;

    private String code;
    private String name;

    OperatorEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }}
