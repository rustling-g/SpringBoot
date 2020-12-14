import com.alibaba.fastjson.JSON;
import domain.User;
import jdk.nashorn.internal.scripts.JD;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;

/**
 * @author gg
 * @create 2020-12-12 下午2:10
 */
public class TestDemo {
    Jedis jedis;

    @Before
    public void init(){
        jedis = new Jedis("localhost",6379);
    }
    @After
    public void destroy(){
        jedis.close();
    }

    @Test
    public void test1(){
        jedis.select(2);
        jedis.set("name","李四");
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }

    @Test
    //以byte[]形式存储对象
    public void test2(){
        String key = "user";
        User user = new User(1, "张三", new Date());
        byte[] serializeKey = SerializationUtils.serialize(key);
        byte[] serializeUser = SerializationUtils.serialize(user);
        jedis.set(serializeKey, serializeUser);
        byte[] bytes = jedis.get(serializeKey);
        User o = (User)SerializationUtils.deserialize(bytes);
        System.out.println(o);
    }

    @Test
    //以string形式存储对象
    public void test3(){
        String key = "stringUser";
        User user = new User(2, "李四", new Date());
        String s = JSON.toJSONString(user);
        jedis.set(key,s);
        String stringUser = jedis.get("stringUser");
        User o = JSON.parseObject(stringUser, User.class);
        System.out.println(o);
    }
}
