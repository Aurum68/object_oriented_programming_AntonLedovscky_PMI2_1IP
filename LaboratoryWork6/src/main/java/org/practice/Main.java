package org.practice;

import org.practice.controller.Controller;
import org.practice.keyboard.Keyboard;

public class Main {
    public static void main(String[] args){
        Keyboard keyboard = new Keyboard();
        new Thread(keyboard::startNativeHook, "JNativeHook-Thread").start();
        new Controller(keyboard);
        System.out.println("Start");
    }
}
