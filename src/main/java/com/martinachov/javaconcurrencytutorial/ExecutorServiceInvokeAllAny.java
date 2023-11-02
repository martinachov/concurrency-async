package com.martinachov.javaconcurrencytutorial;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * El framework ofrece la posibilidad de gestionar grupos de tareas:
 *
 * invokeAll: se envía una colección de tareas al executor y éste devuelve una lista con los correspondientes
 * futures. Esta llamada es bloqueante, es decir, se espera a que todas las tareas estén completadas
 * (ya sea de manera exitosa o devolviendo excepción).
 *
 * invokeAny: se envía una colección de tareas al executor y éste devuelve la primera tarea completada de manera
 * exitosa, si es que la hubiera. Si ninguna terminara correctamente se devolvería una ExecutionException. En el
 * momento que el executor valida un resultado exitoso, cancela el resto de tareas en ejecución. Este método
 * también es bloqueante.
 */
public class ExecutorServiceInvokeAllAny {

    private static final Instant INICIO = Instant.now();
    private static int contadorTareas = 1;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //Se crean 5 tareas
        List<Callable<String>> tareas=
                Stream.generate(ExecutorServiceInvokeAllAny::getTareaSleepUnSegundo)
                        .limit(5)
                        .collect(Collectors.toList());

        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Future<String>> futures = executor.invokeAll(tareas);

        for(Future<String> future : futures) {
            String resultado= future.get();
            Log(resultado);
        }

        Log("El hilo principal continúa...");

        String resultadoAny = executor.invokeAny(tareas);
        Log(resultadoAny);

        Log("El hilo principal continúa...");
        executor.shutdown();
    }

    private static Callable<String> getTareaSleepUnSegundo() {
        int numeroTarea = contadorTareas++;

        return ()->{
            Log("Inicio de la tarea " + numeroTarea);
            try {
                TimeUnit.SECONDS.sleep(1);
                Log("Finaliza la tarea " + numeroTarea);
                return "resultado de la tarea " + numeroTarea;
            } catch (InterruptedException e) {
                Log("sleep ha sido interrumpido en tarea " + numeroTarea);
                return null;
            }
        };
    }

    private static void Log(Object mensaje) {
        System.out.println(String.format("%s [%s] %s",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString()));
    }

}
