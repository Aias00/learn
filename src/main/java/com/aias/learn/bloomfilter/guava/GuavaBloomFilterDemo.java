package com.aias.learn.bloomfilter.guava;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author liuhy
 * @since 2020/4/19
 * Guava Bloom Filter 测试
 * 布隆过滤器不是万能的，但是他能帮我们抵挡掉大部分不存在的数据
 */
public class GuavaBloomFilterDemo {

    //插入多少数据
    private static final int insertions = 1000000;

    //期望的误判率
    private static double fpp = 0.02;

    /**
     * 这里先往布隆过滤器里面存放100万个元素，然后分别测试100个存在的元素和9900个不存在的元素他们的正确率和误判率
     *
     * @param args
     */
    public static void main(String[] args) {
        // 初始化一个存储string数据的布隆过滤器,如果不设置误判率,默认误判率是0.03,不可以设置为0
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), insertions, fpp);
        // 用于存放所有实际存在的key，用于是否存在
        Set<String> sets = new HashSet<>(insertions);
        // 用于存放所有实际存在的key，用于取出
        List<String> lists = new ArrayList<>(insertions);
        //插入随机字符串        
        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            sets.add(uuid);
            lists.add(uuid);
        }
        int rightNum = 0;
        int wrongNum = 0;

        for (int i = 0; i < 10000; i++) {
            // 0-10000之间，可以被100整除的数有100个（100的倍数）从list 里面取出100个真实存在的数进行判断
            String data = i % 100 == 0 ? lists.get(i / 100) : UUID.randomUUID().toString();
            // 这里用了might,看上去不是很自信，所以如果布隆过滤器判断存在了,我们还要去sets中实锤
            if (bf.mightContain(data)) {
                if (sets.contains(data)) {
                    // 如果是真的错在 说明判断正确
                    rightNum++;
                    continue;
                }
                wrongNum++;
            }
        }
        BigDecimal percent = new BigDecimal(wrongNum).divide(new BigDecimal(9900), 2, RoundingMode.HALF_UP);
        BigDecimal bingo = new BigDecimal(9900 - wrongNum).divide(new BigDecimal(9900), 2, RoundingMode.HALF_UP);
        System.out.println("在100W个元素中，判断100个实际存在的元素，布隆过滤器认为存在的：" + rightNum);
        System.out.println("在100W个元素中，判断9900个实际不存在的元素，误认为存在的：" + wrongNum + "，命中率：" + bingo + "，误判率：" + percent);
    }

}