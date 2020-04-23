package com.aias.learn.design.pattern.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhy
 * @since 2020/4/23
 * 网站工厂
 */
public class WebSiteFactory {

    private static Map<String, ConcreteWebSite> pool = new HashMap<>();

    public WebSite getWebSite(String key) {

        if (null == pool.get(key)) {
            pool.put(key, new ConcreteWebSite(key));
        }
        return pool.get(key);
    }

    public int webSiteCount() {
        return pool.size();
    }

}
