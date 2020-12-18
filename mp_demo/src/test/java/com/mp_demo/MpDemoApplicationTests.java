package com.mp_demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mp_demo.bean.User;
import com.mp_demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MpDemoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void findAll() {
        List<User> users = userMapper.selectList(null);
        for (User user:users)
            System.out.println(user);
    }

    @Test
    void insert(){
        User u1 = new User();
        u1.setAge(8);
        u1.setEmail("cv@qq.com");
        u1.setName("jerry");
        userMapper.insert(u1);
        System.out.println("插入数据");
    }

    @Test
    void update(){
        User user = new User();
        user.setId(1339583157247672321L);
        user.setName("tian");
        user.setAge(66);
        userMapper.updateById(user);
    }

    @Test
    void testOptimisticLocker(){
        User user = userMapper.selectById(1339604480112054273L);
        System.out.println(user.getVersion());
        user.setName("TEST");
        userMapper.updateById(user);
        System.out.println(user.getVersion());
    }

    @Test
    void testPage(){
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPage(page,null);
        System.out.println(page.getCurrent());  //获取当前页码
        System.out.println(page.getRecords());  //获取每页的数据集合
        System.out.println(page.getSize());     //每页显示的数量
        System.out.println(page.getTotal());    //当前表中记录总数
        System.out.println(page.getPages());    //总页数

        System.out.println(page.hasNext());     //下一页
        System.out.println(page.hasPrevious()); //上一页
    }

    @Test
    void testDelete(){
//        //物理删除
//        userMapper.deleteById(4L);
//
//        //批量物理删除
//        userMapper.deleteBatchIds(Arrays.asList(1L,2L));

        //逻辑删除（通过标志位来判断）
        userMapper.deleteById(1339770455917101057L);
    }

    @Test
    void testComplexQuery(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        // >= > <= <
//        wrapper.ge("age",30);
//        //eq == ne !=
//        wrapper.eq("email","cv@qq.com");
//        //between
//        wrapper.between("age",30,50);
//        //like
//        wrapper.like("name","t");

//        //降序排列
//        wrapper.orderByDesc("age");
//        //拼接查询条件
//        wrapper.last("limit 1");
        //指定要查询的列
        wrapper.select("id","name");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);

    }

}
