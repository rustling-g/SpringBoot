package com.dao;

import com.domain.Department;
import com.domain.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

/**
 * @author gg
 * @create 2020-12-10 下午6:05
 */
//@Mapper or @MapperScan
public interface IEmployeeDao {

    public Employee findById(Integer id);

//    public Integer deleteById(Integer id);

    public int saveEmployee(Employee emp);

//    public int updateEmployee(Employee emp);
}
