package com.mapper;

import com.domain.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author gg
 * @create 2020-12-11 下午2:19
 */
public interface IEmployeeDao {
    @Select("select * from employee where id = #{id}")
    public Employee findById(Integer id);

    @Update("update employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} where id=#{id}")
    public void update(Employee emp);

    @Delete("delete from employee where id=#{id}")
    public void delete(Integer id);

    @Insert("insert into employee (lastName,email,gender,d_id) values (#{lastName},#{email},#{gender},#{dId})")
    public void insert(Employee emp);
}
