package com.martinachov.javaconcurrencytutorial;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

/**
 *
 * Si se quiere que la tarea devuelva un resultado, la solución es la interfaz Callable<V>.
 * Esta interfaz funcional define el método 'call()' que devuelve un resultado V.
 *
 * El funcionamiento es análogo a un Runnable pero cuando la tarea finaliza devuelve el resultado.
 *
 * Cuando se le envía una tarea de este tipo al Executor, éste devuelve un objeto Future<V>,
 * el cual representa un enlace a la tarea, mediante el cual puedes saber si la tarea ha terminado,
 * obtener su resultado o cancelarla.
 *
 */
public class ExecutorServiceCallable {
    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> tarea= ()->{
            Log("Inicio de la tarea");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log("Finaliza la tarea");
            return "Resultado de la tarea";
        };

        Future<String> future = executor.submit(tarea);

        Log(future.isDone());
        String resultado = future.get();
        Log(future.isDone());

        Log(resultado);
        executor.shutdown();

        /**
         * En el código de ejemplo podemos ver que para obtener el resultado llamamos al método ‘get()’.
         * Es importante tener en cuenta que este método bloquea el hilo principal hasta que la tarea
         * termine o, en otras palabras, las líneas de código posteriores no seguirán su ejecución hasta
         * una vez obtenido el resultado.
         */
    }

    private static void Log(Object mensaje) {
        System.out.println(String.format("%s [%s] %s",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString()));
    }

}
