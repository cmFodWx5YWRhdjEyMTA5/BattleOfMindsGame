package com.bonusgaming.battleofmindskotlin;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Temp {

    @Test
    public void temp(){

        List<String> att=new ArrayList<>();
        att.add(null);
        att.add(null);
        att.add(null);
        att.remove(null);
        att.remove(null);
        System.out.println(att.size());

        add(10.0,null);

    }

    private static void add(Double a, Double a1){
        System.out.println("Double version "+(a+a1));
    }
    private static void add(double a, double a1){
        System.out.println("double version"+(a+a1));
    }

}
