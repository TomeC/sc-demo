package com.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class MainTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
        }catch (Exception e) {
            System.out.println("exception");
        } finally {
            lock.unlock();
        }
    }
}
