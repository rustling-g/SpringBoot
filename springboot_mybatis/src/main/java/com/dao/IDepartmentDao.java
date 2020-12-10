package com.dao;

import com.domain.Department;
import org.apache.ibatis.annotations.*;

/**
 * @author gg
 * @create 2020-12-10 下午4:55
 */
public interface IDepartmentDao {

    @Select("select * from department where id = #{id}")
    public Department findById(Integer id);

    @Delete("delete from department where id = #{id}")
    public Integer deleteById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department (departmentName) values(#{departmentName})")
    public int saveDepartment(Department dep);

    @Update("update department set departmentName = #{departmentName} where id = #{id}")
    public int updateDepartment(Department dep);
}
