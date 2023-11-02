package com.martinachov.javaconcurrencytutorial;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * El siguiente código ejecuta las mismas tareas concurrentes en varios tipos de ExecutorService y registra el
 * tiempo que lleva completar esas tareas.
 * Esto te permitirá comparar el rendimiento de diferentes configuraciones de hilos y pools de hilos en Java.
 *
 */
public class ExecutorServicesNumberOfThreads {

    public static void main(String[] args) throws InterruptedException {

        Log("Número de procesadores lógicos disponibles: " + Runtime.getRuntime().availableProcessors());
        Log("Cantidad de hilos paralelos en el \"common pool\" : " + ForkJoinPool.commonPool().getParallelism());

        List<Callable<Object>> tareas=
                Stream.generate(ExecutorServicesNumberOfThreads::getTareaSleepUnSegundo)
                        .limit(40)
                        .collect(Collectors.toList());

        var executorServices = Arrays.asList(
                Executors.newCachedThreadPool(),
                Executors.newFixedThreadPool(3),
                Executors.newSingleThreadExecutor(),
                Executors.newWorkStealingPool(),
                Executors.newScheduledThreadPool(5),
                ForkJoinPool.commonPool());

        //Un pool de hilos en el que se crean hilos a medida que son necesarios y se reutilizan si están disponibles
        ExecutorService executorCachedThreadPool = Executors.newCachedThreadPool();
        ejecutarTareas(executorCachedThreadPool, "ExecutorCachedThreadPool",tareas);
        executorCachedThreadPool.shutdown();

        //Un pool de hilos con un número fijo de hilos (en este caso, 3)
        ExecutorService executorFixedThreadPool = Executors.newFixedThreadPool(3);
        ejecutarTareas(executorFixedThreadPool, "ExecutorFixedThreadPool", tareas);
        executorFixedThreadPool.shutdown();

        //Un pool de hilos con un solo hilo.
        ExecutorService executorSingleThread = Executors.newSingleThreadExecutor();
        ejecutarTareas(executorSingleThread, "ExecutorSingleThread", tareas);
        executorSingleThread.shutdown();

        //Un pool de hilos basado en "work-stealing" que utiliza tantos hilos como núcleos de CPU disponibles
        ExecutorService executorStealingPool = Executors.newWorkStealingPool();
        ejecutarTareas(executorStealingPool, "ExecutorStealigPool", tareas);
        executorStealingPool.shutdown();

        //Un pool de hilos programados con 5 hilos.
        ExecutorService executorScheduledThreadPool = Executors.newScheduledThreadPool(5);
        ejecutarTareas(executorScheduledThreadPool, "ExecutorScheduledThreadPool", tareas);
        executorScheduledThreadPool.shutdown();

    }

    private static Callable<Object> getTareaSleepUnSegundo() {
        return Executors.callable(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void Log(Object mensaje) {
        System.out.println(String.format("%s", mensaje.toString()));
    }

    private static void ejecutarTareas(ExecutorService executorService, String executorName, List<Callable<Object>> tareas) throws InterruptedException {
        Log("*************** " + executorName + " **************");
        Instant inicio= Instant.now();
        executorService.invokeAll(tareas);
        Log("Duracion en terminar de ejecutar las tareas: " + Duration.between(inicio, Instant.now()));
    }

}
