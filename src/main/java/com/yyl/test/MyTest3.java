package com.yyl.test;

import com.yyl.page.PageResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/5 19:52
 * @Version: 1.0
 */
public class MyTest3 {
    public static void main(String[] args) throws Exception{
        String billingCycle = "201908";
        String createDate = LocalDate.parse(billingCycle + "01", DateTimeFormatter.ofPattern("yyyyMMdd"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00";
        System.out.println(createDate);
    }
    public static void test1(Class clazz){
        System.out.println(clazz == PageResult.class);
    }

}
