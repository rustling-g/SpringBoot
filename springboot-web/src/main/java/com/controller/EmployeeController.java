package com.controller;

import com.dao.DepartmentDao;
import com.dao.EmployeeDao;
import com.domain.Department;
import com.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author gg
 * @create 2020-12-08 下午3:33
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("employees",employees);
        return "/employee/list";
    }

    @GetMapping("/emp")
    public String toAddPage(Model model){
        //先查出所有的部门
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "/employee/add";
    }

    @PostMapping("/emp")
    public String add(Employee employee){
        System.out.println(employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String toUpdatePage(@PathVariable("id") Integer id,Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee",employee);
        return "/employee/update";
    }

    @PutMapping("/emp")
    public String update(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @DeleteMapping("/emp/{id}")
    public String delete(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
