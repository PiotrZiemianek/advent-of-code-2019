package day7;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Buffer {
    private static final Map<String, Queue<Integer>> threadsBuffer = new ConcurrentHashMap<>();

    public static synchronized void inputSettings(int setting) {
        addThreadsToMap();
        threadsBuffer.get(Thread.currentThread().getName()).add(setting);
    }
    public static void addToBuffer(int signal) {
//            addThreadsToMap();
        synchronized (threadsBuffer.get(getNextThreadName(Thread.currentThread().getName()))) {
            String threadName = Thread.currentThread().getName();
            threadsBuffer.get(getNextThreadName(threadName)).add(signal);
            threadsBuffer.get(getNextThreadName(threadName)).notify();
        }
    }

    public static int takeFromBuffer() {
        synchronized (threadsBuffer.get(Thread.currentThread().getName())){
        String threadName = Thread.currentThread().getName();
            if (threadsBuffer.get(threadName).isEmpty()) {
                try {

                    threadsBuffer.get(threadName).wait();

                } catch (InterruptedException e) {
                    System.out.println("Thread " + threadName + " interrupt!");
                }
            }
            return threadsBuffer.get(threadName).remove();
        }
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

    public static int getAmplifyResult() {
        return threadsBuffer.get("1").remove();
    }
}
