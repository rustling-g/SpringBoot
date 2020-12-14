package com.service;

import com.domain.Employee;
import com.mapper.IEmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author gg
 * @create 2020-12-11 下午3:07
 */
@Service
public class EmployeeService {
    @Autowired
    IEmployeeDao employeeDao;

    @Cacheable(cacheNames = {"emp"},key = "#id",unless="#result == null")//"#root.methodName+'['+#id+']'"  该key效果为findById[2]
    public Employee findById(Integer id){
        System.out.println("查询【"+id+"】号员工");
        Employee employee = employeeDao.findById(id);
        return employee;
    }

    @CachePut(value = "emp",key = "#employee.id")
    public void update(Employee employee){
        employeeDao.update(employee);
        System.out.println("update方法被调用");
    }

    @CacheEvict(value = "emp",key = "#id")
    public void delete(Integer id){
        employeeDao.delete(id);
        System.out.println("delete方法被调用");
    }
}
