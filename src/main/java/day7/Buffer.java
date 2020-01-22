package day7;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Buffer {
    private static final Map<String, Queue<Integer>> threadsBuffer = new ConcurrentHashMap<>();

    public static void addToBuffer(String threadName, int output) {
        addThreadsToMap();
        threadsBuffer.get(Thread.currentThread().getName()).add(output);
        threadsBuffer.get(getNextThreadName(Thread.currentThread().getName())).notify();
    }

    public static int takeFromBuffer(String threadName) {
        if (threadsBuffer.get(Thread.currentThread().getName()).isEmpty()) {
            try {

                threadsBuffer.get(Thread.currentThread().getName()).wait();

            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + " interrupt!");
            }
        }
        return threadsBuffer.get(Thread.currentThread().getName()).remove();
    }

    private static synchronized void addThreadsToMap() {
        if (!threadsBuffer.containsKey(Thread.currentThread().getName())) {
            Queue<Integer> buffer = new LinkedList<>();
            threadsBuffer.put(Thread.currentThread().getName(), buffer);
        }
        if (!threadsBuffer.containsKey(getNextThreadName(Thread.currentThread().getName()))) {
            Queue<Integer> buffer = new LinkedList<>();
            threadsBuffer.put(getNextThreadName(Thread.currentThread().getName()), buffer);
        }
    }

    private static synchronized String getNextThreadName(String threadName) {

        int nextAmpId = Integer.parseInt(threadName) + 1;
        nextAmpId = (nextAmpId == 6) ? nextAmpId = 1 : nextAmpId;

        return Integer.valueOf(nextAmpId).toString();
    }
}
