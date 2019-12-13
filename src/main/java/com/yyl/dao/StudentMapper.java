package com.yyl.dao;

import com.yyl.bean.Student;
import com.yyl.page.PageInfo;
import com.yyl.page.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 11:22
 * @Version: 1.0
 */
public interface StudentMapper {
    Student findStudentById(Integer studentId);
    Student findStudentByIdForOracleOrMySql(@Param("studentId")Integer studentId, @Param("tableName")String tableName);
    Student findStudentInfo(Integer studentId);
    List<Student> findAllStudentInfo();
    Student findStudentByDynamicSqlTest(Student student);
    Student findStudentByDynamicSqlChoose(Student student);

    Student findStudentByDynamicSqlWhere(Student student);
    Student findStudentByDynamicSqlTrimWhere(Student student);
    int updateStudentByDynamicSqlTrimSet(Student student);
    List<Student> queryBatch(List<Integer> ids);

    Student findStudentByIdAndNote(@Param("studentId") Integer studentId,@Param("note") String note);

    PageResult<Student> findPageStudent(PageInfo pageInfo,@Param("sex") String sex);
    PageResult<Student> findDynamicPageStudent(PageInfo pageInfo,@Param("ids") List<Integer> ids);
    List<Student> findStudentForGithubPageHelper(@Param("ids") List<Integer> ids);
    int insert(Student student);
}
