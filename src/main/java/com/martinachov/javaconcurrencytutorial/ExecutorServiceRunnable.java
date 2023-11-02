package com.martinachov.javaconcurrencytutorial;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hemos utilizado la implementación de ExecutorService de un solo hilo ayudándonos de la clase auxiliar Executors.
 * Esta clase es una factoría de executors y tiene métodos de utilidad.
 */
public class ExecutorServiceRunnable {

    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable tarea= ()->{
            Log("Inicio de la tarea");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log("Finaliza la tarea");
        };

        executor.submit(tarea);
        executor.shutdown();
    }

    private static void Log(Object mensaje) {
        System.out.println(String.format("%s [%s] %s",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString()));
    }

}