package com.aias.learn.design.pattern.flyweight;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class FlyWeightTest {

    public static void main(String[] args) {
        WebSiteFactory factory = new WebSiteFactory();

        WebSite site = factory.getWebSite("淘宝网");
        site.visit();
        WebSite site1 = factory.getWebSite("京东");
        site1.visit();
        WebSite site12 = factory.getWebSite("京东");
        site12.visit();
        WebSite site2 = factory.getWebSite("百度");
        site2.visit();
        WebSite site3 = factory.getWebSite("腾讯");
        site3.visit();
        System.out.println(factory.webSiteCount());

    }

}
