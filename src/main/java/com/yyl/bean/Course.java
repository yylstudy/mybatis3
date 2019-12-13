package com.yyl.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/2 10:58
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class Course implements Serializable {
    private String id;
    private String name;
}
