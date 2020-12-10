package com.controller;

import com.dao.IDepartmentDao;
import com.dao.IEmployeeDao;
import com.domain.Department;
import com.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gg
 * @create 2020-12-10 下午5:08
 */
@RestController
public class DepartmentController {
    
    @Autowired
    IDepartmentDao departmentDao;

    @Autowired
    IEmployeeDao employeeDao;

    @GetMapping("/dept/{id}")
    public Department findById(@PathVariable("id") Integer id){
        return departmentDao.findById(id);
    }

    @GetMapping("/dept")
    public Department saveDepartment(Department dep){
        int i = departmentDao.saveDepartment(dep);
        System.out.println("影响======"+i);
        return dep;
    }

    @GetMapping("/emp/{id}")
    public Employee findEmp(@PathVariable("id") Integer id){
        return employeeDao.findById(id);
    }

    @GetMapping("/emp")
    public Employee saveEmployee(Employee emp){
        int i = employeeDao.saveEmployee(emp);
        System.out.println("影响======"+i);
        return emp;
    }
}
