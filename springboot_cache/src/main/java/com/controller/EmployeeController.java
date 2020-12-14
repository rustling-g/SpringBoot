package com.controller;

import com.domain.Employee;
import com.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentMap;

/**
 * @author gg
 * @create 2020-12-11 下午3:37
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee findById(@PathVariable("id") Integer id){
        Employee employee = employeeService.findById(id);
        return employee;
    }

    @GetMapping("/emp")
    public Employee update(Employee employee){
        employeeService.update(employee);
        return employee;
    }

    @GetMapping("/empd/{id}")
    public String delete(@PathVariable("id") Integer id){
        employeeService.delete(id);
        return "删除成功";
    }
}
