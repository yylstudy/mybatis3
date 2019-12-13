package com.yyl.test;

import com.github.pagehelper.PageHelper;
import com.yyl.bean.Student;
import com.yyl.configuration.SqlSessionUtil;
import com.yyl.dao.StudentMapper;
import com.yyl.page.PageInfo;
import com.yyl.page.PageResult;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/2 19:35
 * @Version: 1.0
 */
public class MyTest2 {
    /**
     * mybatis多参数查询
     */
    @Test
    public void test1(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = studentMapper.findStudentByIdAndNote(1,"22");
        System.out.println(student);
    }
    /**
     * mybatis分页查询
     */
    @Test
    public void test2(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        PageResult<Student> students = studentMapper.findPageStudent(new PageInfo(),"1");
        System.out.println(students.getTotal());
        students.stream().forEach(System.out::println);
    }
    /**
     * mybatis分页查询带动态sql
     */
    @Test
    public void test3(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Integer> lists = Arrays.asList(4,5,6,7,8);
        PageResult<Student> students = studentMapper.findDynamicPageStudent(new PageInfo(),lists);
        System.out.println(students.getTotal());
        students.stream().forEach(System.out::println);
    }

    /**
     * 使用github-pagehelper分页查询测试
     */
    @Test
    public void test4(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Integer> lists = Arrays.asList(1,2,3,4,5,6,7,8);
        PageHelper.startPage(2, 2);
        List<Student> students = studentMapper.findStudentForGithubPageHelper(lists);
        students.stream().forEach(System.out::println);
    }

    /**
     * 测试insert操作是否会使用分页拦截器创建代理对象
     */
    @Test
    public void test5(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setNote("1");
        student.setId(new Random().nextInt(1000));
        student.setCnname("hahahha");
        student.setSex("1");
        studentMapper.insert(student);
        sqlSession.commit();
    }

}
