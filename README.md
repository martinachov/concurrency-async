# Concurrencia en Java

Executor es un framework para la ejecución de tareas de manera concurrente. Desacopla la creación y manejo de los hilos de las tareas:

<img src="https://blog.softtek.com/hubfs/ExecutorFramework.svg" />


A modo esquemático, y sin entrar en profundidad, la API de Java ofrece la siguiente implementación del framework:

<img src="https://blog.softtek.com/hubfs/InterfacesExecutor.svg"/>

  - Executor: una sencilla interfaz con el método ‘execute(Runnable)’. Es la definición mínima del framewok, se encarga de ejecutar una 
  tarea.
  - ExecutorService: añade funcionalidades para el manejo del ciclo de vida de las tareas.
  - ScheduledExecutorService: añade funcionalidades para la programación de las tareas.
