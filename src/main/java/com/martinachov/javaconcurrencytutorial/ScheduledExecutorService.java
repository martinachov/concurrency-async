package com.martinachov.javaconcurrencytutorial;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *
 * Hasta ahora hemos enviado nuestras tareas al Executor dejando en sus manos el paso a ejecución, pero podríamos
 * necesitar programar en el tiempo cuándo realizar la tarea. La solución a este problema es la interfaz
 * ScheduleExecutorService, la cual añade las siguientes funcionalidades:
 *
 * schedule: empieza a ejecutar la tarea tras un tiempo determinado.
 *
 * scheduleAtFixedRate: lanza secuencialmente la misma tarea cada intervalo de tiempo definido. Es independiente
 * de lo que la tarea tarde en acabarse. También se puede definir un delay inicial.
 *
 * scheduleWithFixedDelay: lanza secuencialmente la misma tarea cada intervalo de tiempo definido una vez acabada
 * la instancia previa. Espera a que una tarea se acabe para comenzar el intervalo de espera e iniciar la siguiente.
 * También se puede definir un delay inicial.
 *
 */
public class ScheduledExecutorService {

    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) {

        java.util.concurrent.ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        Runnable tarea= ()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log("Ejecución de tarea");
        };

        //scheduledExecutor.schedule(tarea, 3, TimeUnit.SECONDS);
        //scheduledExecutor.scheduleAtFixedRate(tarea, 2, 1, TimeUnit.SECONDS);
        scheduledExecutor.scheduleWithFixedDelay(tarea, 2, 1, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log("El hilo principal continúa...");
        scheduledExecutor.shutdown();
    }

    private static void Log(Object mensaje) {
        System.out.println(String.format("%s [%s] %s",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString()));
    }

}
