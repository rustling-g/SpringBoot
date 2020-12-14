import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

/**
 * @author gg
 * @create 2020-12-12 下午4:41
 */
public class TestDemo2 {
    @Test
    public void test1(){
        JedisPool pool = new JedisPool("localhost",6379);
        Jedis jedis1 = pool.getResource();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            jedis1.incr("pp");
        }
        System.out.println("不使用管道时间："+ (System.currentTimeMillis()-start));
        jedis1.close();

        Jedis jedis2 = pool.getResource();
        Pipeline pipeline = jedis2.pipelined();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            pipeline.incr("pp");
        }
        pipeline.syncAndReturnAll();
        System.out.println("使用管道时间："+ (System.currentTimeMillis()-start2));

    }
}
