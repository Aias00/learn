package com.aias.learn.cache;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/25
 */
public class CacheTest {

    public static void main(String[] args) throws InterruptedException {
        Cache cache = LocalCacheImpl.getInstance();

        int intValue = cache.set("price", 1000);
        System.out.println(intValue);
        String strValue = cache.set("name", "手机");
        System.out.println(strValue);
        Date dateValue = cache.set("date", new Date());
        System.out.println(dateValue);
        double doubleValue = cache.set("double", 0.22);
        System.out.println(doubleValue);

        Set<String> keys = cache.keys();

        for (String key : keys) {
            System.out.println(key);
        }

        cache.set("test",1000000,10, TimeUnit.SECONDS);

        System.out.println(cache.exists("test"));

        TimeUnit.SECONDS.sleep(10);

        System.out.println(cache.exists("test"));



    }

}
