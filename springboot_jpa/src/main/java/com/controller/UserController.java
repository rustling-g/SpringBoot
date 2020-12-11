package com.controller;

import com.dao.IUserDao;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gg
 * @create 2020-12-10 下午7:48
 */
@RestController
public class UserController {

    @Autowired
    IUserDao userDao;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable("id") Integer id){
        User user = userDao.getOne(id);
        return user;
    }

    @GetMapping("/user")
    public User saveUser(User user){
        User u = userDao.save(user);
        return u;
    }
}
