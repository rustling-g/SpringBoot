package com.dao;

import com.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gg
 * @create 2020-12-10 下午7:39
 */
//继承JpaRepository
public interface IUserDao extends JpaRepository<User,Integer> {
}
