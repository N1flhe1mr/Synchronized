package ru.netology;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static final int routesSize = 1000;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(routesSize);

        for (int i = 0; i < routesSize; i++) {
            threadPool.submit(runFreqR());
        }

        threadPool.shutdownNow();
        List<Integer> keys = new ArrayList<>(sizeToFreq.keySet());
        keys.sort(Collections.reverseOrder());
        List<Integer> values = new ArrayList<>(sizeToFreq.values());
        values.sort(Collections.reverseOrder());
        System.out.printf("Самое частое количество повторений %d (встретилось %d раз) \nДругие размеры:\n", keys.get(0), values.get(0));

        for (int i = 1; i < keys.size(); i++) {
            System.out.printf("- %d (%d раз)\n", keys.get(i), values.get(i));
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();

        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }

        return route.toString();
    }

    public static Runnable runFreqR() {
        return () -> {
            String route = generateRoute("RLRFR", 100);
            int freqR = 0;

            for (int i = 0; i < route.length(); i++) {
                if (route.charAt(i) == 'R') {
                    freqR++;
                }
            }

            synchronized (sizeToFreq) {
                sizeToFreq.put(freqR, sizeToFreq.containsKey(freqR) ? sizeToFreq.get(freqR) + 1 : 1);
            }
        };
    }
}