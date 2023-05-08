# 🕹️ Buscaminas en Java 🧩

Este es un juego de buscaminas implementado en Java utilizando el framework **JFrame**. 🎮

El juego incluye una interfaz gráfica de usuario con botones que representan las celdas del tablero del juego. El objetivo del juego es descubrir todas las celdas no minadas sin hacer clic en ninguna mina. 💣

El juego comienza generando aleatoriamente un tablero con un número determinado de minas. El jugador puede hacer clic en una celda para revelar su contenido o marcarla como minada con un clic derecho del ratón. 🔍

La ventana del juego incluye una etiqueta que muestra el número de minas restantes. Cuando se descubre una mina, el juego termina y se muestra un mensaje de diálogo. Si se descubren todas las celdas no minadas, el jugador gana y se muestra otro mensaje de diálogo. 🎉

## Compilación y Ejecución

Para compilar el archivo `Buscaminas.java`, se debe abrir una terminal o consola y ejecutar el siguiente comando:

```bash
javac Buscaminas.java
```

Esto generará un archivo `Buscaminas.class`. Para ejecutar el juego, se debe ejecutar el siguiente comando en la misma terminal:

```bash
java Buscaminas
```
