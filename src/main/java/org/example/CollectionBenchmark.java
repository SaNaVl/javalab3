package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CollectionBenchmark {

    private static final int ITERATIONS = 10_000; // можно менять

    public static void main(String[] args) {
        System.out.println("=== Сравнение производительности ArrayList vs LinkedList ===");
        System.out.println("Количество итераций: " + ITERATIONS);
        System.out.println();

        List<BenchmarkResult> arrayResults = runTests(new ArrayList<>(), "ArrayList");
        List<BenchmarkResult> linkedResults = runTests(new LinkedList<>(), "LinkedList");

        printResults(arrayResults, linkedResults);
    }

    private static List<BenchmarkResult> runTests(List<Integer> list, String listType) {
        List<BenchmarkResult> results = new ArrayList<>();

        // 1. Тест add (в конец)
        long time = measureTime(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                list.add(i);
            }
        });
        results.add(new BenchmarkResult(listType + " add(end)", ITERATIONS, time));
        list.clear();

        // 2. Тест add (в начало) — ТОЛЬКО если List поддерживает add(0, element)
        if (list instanceof ArrayList || list instanceof LinkedList) {
            time = measureTime(() -> {
                for (int i = 0; i < ITERATIONS; i++) {
                    list.add(0, i);
                }
            });
            results.add(new BenchmarkResult(listType + " add(begin)", ITERATIONS, time));
            list.clear();
        }

        // 3. Тест add (в середину)
        time = measureTime(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                list.add(list.size() / 2, i);
            }
        });
        results.add(new BenchmarkResult(listType + " add(middle)", ITERATIONS, time));
        list.clear();

        // 4. Заполняем для тестов get и delete
        for (int i = 0; i < ITERATIONS; i++) {
            list.add(i);
        }

        // 5. Тест get (по индексу)
        time = measureTime(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                list.get(i);
            }
        });
        results.add(new BenchmarkResult(listType + " get", ITERATIONS, time));

        // 6. Тест delete (из начала)
        time = measureTime(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                list.remove(0);
            }
        });
        results.add(new BenchmarkResult(listType + " delete(begin)", ITERATIONS, time));

        // Перезаполняем
        for (int i = 0; i < ITERATIONS; i++) list.add(i);

        // 7. Тест delete (из середины)
        time = measureTime(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                list.remove(list.size() / 2);
            }
        });
        results.add(new BenchmarkResult(listType + " delete(middle)", ITERATIONS, time));

        // Перезаполняем
        for (int i = 0; i < ITERATIONS; i++) list.add(i);

        // 8. Тест delete (из конца)
        time = measureTime(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                list.remove(list.size() - 1);
            }
        });
        results.add(new BenchmarkResult(listType + " delete(end)", ITERATIONS, time));

        return results;
    }

    private static long measureTime(Runnable action) {
        long start = System.nanoTime();
        action.run();
        return System.nanoTime() - start;
    }

    private static void printResults(List<BenchmarkResult> arrayResults,
                                     List<BenchmarkResult> linkedResults) {
        System.out.println("Метод                | Итераций   | Время выполнения");
        System.out.println("---------------------+------------+------------------");

        for (int i = 0; i < arrayResults.size(); i++) {
            BenchmarkResult ar = arrayResults.get(i);
            BenchmarkResult lr = linkedResults.get(i);

            System.out.println(ar);
            System.out.println(lr);
            System.out.println("---------------------+------------+------------------");
        }

        System.out.println("\nПримечание: для add(begin) и delete(begin) LinkedList должен быть быстрее.");
        System.out.println("Для get и delete(middle) ArrayList обычно быстрее.");
    }
}