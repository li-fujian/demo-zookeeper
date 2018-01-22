package com.lifujian.www.util;

public class KeepAlive {

    public static void keepAilve() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }
    }
}
