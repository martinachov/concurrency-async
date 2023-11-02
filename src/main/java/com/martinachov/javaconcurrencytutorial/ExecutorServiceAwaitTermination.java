package com.martinachov.javaconcurrencytutorial;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * Para apagar un Executor existen dos formas. Haciendo una llamada a ‘shutdown()’, el executor deja de
 * aceptar nuevas tareas y cuando las finaliza se cierra, es decir, espera a que las tareas actualmente
 * en ejecución o en la cola se realicen. Mediante ‘shutdownNow()’ se paran de manera abrupta, finalizando
 * en ese mismo momento el executor.
 *
 */

public class ExecutorServiceAwaitTermination {

    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable tarea= ()->{
            Log("Inicio de la tarea");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log("Finaliza la tarea");
        };

        executor.submit(tarea);

        executor.shutdown();
        executor.awaitTermination(7, TimeUnit.SECONDS);

        Log("El hilo principal continúa...");
    }

    private static void Log(Object mensaje) {
        System.out.println(String.format("%s [%s] %s",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString()));
    }

    /**
     *
     * Es importante tener en cuenta que haciendo ‘shutdown()’ no se bloquea el código, las tareas que hayan
     * quedado pendientes en los respectivos hilos terminarán su ejecución de manera asíncrona, pero el hilo
     * principal del executor por su parte también continuará.
     *
     * Si se desea bloquear el hilo principal hasta que esas tareas terminen, la api ofrece para ello el
     * método awaitTermination(timeout), el cual bloquea el código hasta que las tareas terminen o se acabe el
     * tiempo de timeout declarado, lo primero que ocurra de ambas.
     *
     */

}
