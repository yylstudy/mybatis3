package com.yyl.enums;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 12:20
 * @Version: 1.0
 */
public enum SexEnum {
    MAN("1","男"),
    WOMAN("2","女"),
    UNKNOW("3","未知性别");
    private String code;
    private String name;

    SexEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SexEnum getSexEnum(String code){
        for(SexEnum sexEnum:SexEnum.values()){
            if(sexEnum.code.equals(code)){
                return sexEnum;
            }
        }
        return null;
    }
}
