package day7;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Buffer {
    static Map<String, Queue<Integer>> threadsBufferMap = new ConcurrentHashMap<>();

    public static void inputSettings(String id, int setting) {

        Queue<Integer> integers = threadsBufferMap.get(id);
        integers.add(setting);
        if (id.equals("1")) {
            threadsBufferMap.get("1").add(0);
        }

    }

    public static void addToBuffer(int signal) {
//            addThreadsToMap();
        synchronized (threadsBufferMap.get(getNextThreadName(Thread.currentThread().getName()))) {
            String threadName = Thread.currentThread().getName();
            threadsBufferMap.get(getNextThreadName(threadName)).add(signal);
            System.out.println("thread " + Thread.currentThread().getName() + " add to buffer " + signal);
            threadsBufferMap.get(getNextThreadName(threadName)).notify();
        }
    }

    public static int takeFromBuffer() {
        synchronized (threadsBufferMap.get(Thread.currentThread().getName())) {
            String threadName = Thread.currentThread().getName();
            if (threadsBufferMap.get(threadName).isEmpty()) {
                try {

                    threadsBufferMap.get(threadName).wait();

                } catch (InterruptedException e) {
                    System.out.println("Thread " + threadName + " interrupt!");
                }
            }
            Integer result = threadsBufferMap.get(threadName).remove();
            System.out.println("thread: " + threadName + " takes value " + result + " from buffer"); //todo kto co bierze z bufora
            return result;
        }
    }

    private static synchronized String getNextThreadName(String threadName) {

        int nextAmpId = Integer.parseInt(threadName) + 1;
        nextAmpId = (nextAmpId == 6) ? 1 : nextAmpId;

        return Integer.valueOf(nextAmpId).toString();
    }

    public static int getAmplifyResult() {
        return threadsBufferMap.get("1").remove();
    }
}
