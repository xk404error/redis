package redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

import static java.lang.Thread.sleep;

public class App {
    public static void main(String[] args) throws InterruptedException {
        RedisClient redisClient = new RedisClient();
        //redisClient.show();
        Jedis jedis = redisClient.getJedis();
        jedis.set("xikaiRedis","haoren");
        jedis.set("key001","val001");
        //jedis.set("xikaiRedis","haoren1");
        //jedis.expire("xikaiRedis", 5);
        //jedis.persist("xikaiRedis");
        //sleep(1000*6);
        System.out.println(jedis.ttl("xikaiRedis"));
        Set<String> keys = jedis.keys("*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
            System.out.println(jedis.get(key));
        }
        System.out.println("系统中删除xikaiRedis: "+jedis.del("xikaiRedis","key001"));
        System.out.println(jedis.exists("key001"));
    }
}
