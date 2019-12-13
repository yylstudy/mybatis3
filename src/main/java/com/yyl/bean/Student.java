package com.yyl.bean;

import com.yyl.enums.SexEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 11:21
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class Student implements Serializable {
    private Integer id;
    private String cnname;
    private String sex;
    private SexEnum sexEnum;
    private String note;
    private Grade grade;
    private List<Course> courseList;
    private String heights;

}
