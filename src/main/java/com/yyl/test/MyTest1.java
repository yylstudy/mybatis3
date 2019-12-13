package com.yyl.test;

import com.yyl.bean.Student;
import com.yyl.configuration.SqlSessionUtil;
import com.yyl.dao.StudentMapper;
import com.yyl.enums.SexEnum;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 12:07
 * @Version: 1.0
 */
public class MyTest1 {
    /**
     * 测试全局和局部的TypeHandler，及自定义枚举的TypeHandler
     */
    @Test
    public void test1(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = studentMapper.findStudentById(1);
        //需要注意的是，如果需要使用mybatis的二级缓存，这里需要调用sqlSession.commit()方法
        sqlSession.commit();
        System.out.println(student);
        studentMapper.findStudentById(1);
    }
    /**
     * 在同一个mapper文件中，根据不同的数据库厂商（同一条sql，语法不同）执行不同的语句
     */
    @Test
    public void test2(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = studentMapper.findStudentByIdForOracleOrMySql(1,"student");
        System.out.println(student);
    }

    /**
     * mybatis 嵌套查询
     */
    @Test
    public void test3(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = studentMapper.findStudentInfo(1);
        System.out.println(student);
    }

    /**
     * mybatis 嵌套查询 id列数据重复测试,可以看到，即使
     * 数据库中的用户有三个，但是因为cnname不是唯一的，所以也被合并了
     * 在联合查询的时候最好指定id 否则，则以所有的resultMap中的列 创建rowkey
     */
    @Test
    public void test4(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.findAllStudentInfo();
        System.out.println(students.size());
        System.out.println(students);
    }


    /**
     * mybatis 动态sql test
     */
    @Test
    public void test5(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setCnname("yyl");
        student.setId(1);
        Student student2 = studentMapper.findStudentByDynamicSqlTest(student);
        System.out.println(student2);
    }

    /**
     * mybatis 动态sql choose 可以看到choose和if的区别就是test是一直判断，choose
     * 是只要查询到一个就退出choose标签，所以下面即使设置cnname为yyl1也能查询到数据，因为这个条件没用
     */
    @Test
    public void test6(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setCnname("yyl1");
        student.setId(1);
        Student student2 = studentMapper.findStudentByDynamicSqlChoose(student);
        System.out.println(student2);
    }

    /**
     * mybatis 动态sql where标签
     */
    @Test
    public void test7(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setCnname("yyl");
        student.setId(1);
        Student student2 = studentMapper.findStudentByDynamicSqlWhere(student);
        System.out.println(student2);
    }

    /**
     * mybatis 动态sql trim标签 trim标签就是去除多余出来的前缀或者后缀
     */
    @Test
    public void test8(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setCnname("yyl");
        Student student2 = studentMapper.findStudentByDynamicSqlTrimWhere(student);
        System.out.println(student2);
    }

    /**
     * mybatis 动态sql trim标签
     */
    @Test
    public void test9(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setCnname("yyl");
        student.setId(1);
        student.setNote("22");
        int i = studentMapper.updateStudentByDynamicSqlTrimSet(student);
        System.out.println(i);
        sqlSession.commit();
    }

    /**
     * mybatis 动态sql 批量查询
     */
    @Test
    public void test91(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.queryBatch(Arrays.asList(1,2,3));
        System.out.println(students);
    }

}
