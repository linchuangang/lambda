package com.lin;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/7/30.
 */
public class Lambda {
    public static void main(String[] args) {
        //1
        Arrays.asList("a","b","c").forEach(e-> System.out.println(e));
        //2
        Arrays.asList("d","e","f").forEach(e->
        {
            System.out.println(e);
            System.out.println(e);
        }
        );
        //3
        String s=",";
        Arrays.asList("h","i","j").forEach(e-> System.out.println(e+s));
        //4
        Arrays.asList("1","3","2").sort((e1,e2)->{
            int result=e1.compareTo(e2);
            System.out.println(result);
            return result;
        });
    }

}
