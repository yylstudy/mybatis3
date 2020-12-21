package com.yyl.dao;

import com.yyl.bean.Student;
import com.yyl.objectWrapperFactory.HumpMap;
import com.yyl.page.PageInfo;
import com.yyl.page.PageResult;
import com.yyl.search.Searcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    HumpMap<String,Object> selectGradeBuHumpMap(@Param("studentId")Long studentId);

    HumpMap<String,Object> selectByNoParamSearch(Searcher searcher);
    List<HumpMap<String,Object>> selectByParamSearch(@Param("searcher") Searcher searcher);
    List<HumpMap<String,Object>> selectByParamSearch2(@Param("searcher2") Searcher searcher,@Param("name")String name);
    List<HumpMap<String,Object>> comparatorByDate(Date date);

}
