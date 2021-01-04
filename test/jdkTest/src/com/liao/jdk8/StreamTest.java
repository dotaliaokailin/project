package com.liao.jdk8;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        StreamTest streamTest = StreamTest.getInstance(StreamTest::new);
        streamTest.serial();
        streamTest.parallel();
        streamTest.list();
        streamTest.sort();
        streamTest.parallelSort();
    }

    /**
     * 构造实例
     */
    public static StreamTest getInstance(final Supplier<StreamTest> supplier){
        return supplier.get();
    }

    /**
     * 判断是否是质数
     */
    public Boolean isPrime(int num){
        int tmp = num;
        if(tmp < 2){
            return false;
        }
        for(int i = 2; Math.sqrt(tmp) >= i; i++){
            if(tmp % i == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 串行
     */
    public void serial(){
        long beginTime = System.currentTimeMillis();
        long count = IntStream.range(0, 1000000).filter(this::isPrime).count();
        System.out.println(count);
        long endTime = System.currentTimeMillis();
        System.out.println("串行花费时间："+ (endTime - beginTime));
    }

    /**
     * 并行
     */
    public void parallel(){
        long beginTime = System.currentTimeMillis();
        long count = IntStream.range(0, 1000000).parallel().filter(this::isPrime).count();
        System.out.println(count);
        long endTime = System.currentTimeMillis();
        System.out.println("并行花费时间："+ (endTime - beginTime));
    }

    /**
     * 并行集合
     */
    public void list(){
        List<Integer> list = new ArrayList<>();
        PrimitiveIterator.OfInt iterator = IntStream.range(0, 1000000).iterator();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        System.out.println(list.parallelStream().filter(num -> num % 2 != 0).count());;
    }

    /**
     * 排序
     */
    public void sort(){
        int[] nums = new int[]{2,1,5,6,4,3,9,8,7,0};
        Arrays.stream(nums).sorted().forEach(System.out::print);
    }

    /**
     * 并行排序
     */
    public void parallelSort(){
        int[] nums = new int[50000];
        Random random = new Random();
        for (int i = 0; i < 50000; i++) {
            nums[i] = random.nextInt(1000);
        }
        Arrays.parallelSort(nums);
        Arrays.stream(nums).forEach(System.out::println);
    }
}
