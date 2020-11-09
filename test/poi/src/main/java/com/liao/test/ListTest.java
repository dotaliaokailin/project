package com.liao.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTest {
    static List<Object> list1 = new ArrayList<Object>(){
        {
            add("a");
            add("b");
            add("c");
        }
    };
    static List<Object> list2 = new ArrayList<Object>(){
        {
            add("e");
            add("f");
            add("c");
        }
    };

    /**
     * 交集
     */
    @Test
    public void inter(){
        boolean b = list1.retainAll(list2);
        System.out.println(list1); // c
        System.out.println(list2); // e f c
    }

    /**
     * 差集
     */
    @Test
    public void diff(){
        boolean b = list1.removeAll(list2);
        System.out.println(list1); // a b
        System.out.println(list2); // e f c
    }

    /**
     * 并集
     */
    @Test
    public void union(){
        list1.removeAll(list2);
        list1.addAll(list2);
        System.out.println(list1); // a b e f c
        list1.forEach(System.out::print); // a b e f c
    }
}
