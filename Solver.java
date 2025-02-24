import java.util.*;

public class Solver {

    // alfabeto del automata
    private final String[] alfabeto = {"E", "N", "S", "U", "W", "D"};

    // clase del estado del automata
    private class Estado {
        Node nodo;
        String historialDeMovimientos;

        Estado(Node nodo, String historialDeMovimientos) {
            this.nodo = nodo;
            this.historialDeMovimientos = historialDeMovimientos;
        }
    }

    public Solver() {
    }

    public String solve(Maze laberinto) {
        // estado inicial
        Estado estadoInicial = new Estado(laberinto.getStartingSpace(), "");

        // conjunto de estados visitados
        Set<Integer> estadosVisitados = new HashSet<>();

        // cola para busqueda en anchura
        Queue<Estado> cola = new LinkedList<>();
        cola.offer(estadoInicial);
        estadosVisitados.add(estadoInicial.nodo.getId());

        while (!cola.isEmpty()) {
            // obtener el estado actual de la cola
            Estado estadoActual = cola.poll();

            // verificar si el nodo actual es la salida
            if (laberinto.isExitSpace(estadoActual.nodo.xIndex, 
                                      estadoActual.nodo.yIndex, 
                                      estadoActual.nodo.zIndex)) {
                // retornar el historial de movimientos si es la salida
                return "[" + estadoActual.historialDeMovimientos.replaceAll(",$", "") + "]";
            }

            // probar todos los simbolos del alfabeto para generar nuevos estados
            for (String simbolo : alfabeto) {
                // obtener el siguiente estado basado en el simbolo
                Estado siguienteEstado = transicion(laberinto, estadoActual, simbolo);
                // verificar si el siguiente estado es valido y no ha sido visitado
                if (siguienteEstado != null && !estadosVisitados.contains(siguienteEstado.nodo.getId())) {
                    // agregar el siguiente estado a la cola y al conjunto de estados visitados
                    cola.offer(siguienteEstado);
                    estadosVisitados.add(siguienteEstado.nodo.getId());
                }
            }
        }

        return "[]";
    }

    // metodo para realizar la transicion de estados
    private Estado transicion(Maze laberinto, Estado estadoActual, String simbolo) {
        Node siguienteNodo = null;
        Node nodoActual = estadoActual.nodo;

        // determinar el siguiente nodo basado en el simbolo
        switch (simbolo) {
            case "E":
                siguienteNodo = laberinto.moveEast(nodoActual);
                break;
            case "N":
                siguienteNodo = laberinto.moveNorth(nodoActual);
                break;
            case "S":
                siguienteNodo = laberinto.moveSouth(nodoActual);
                break;
            case "U":
                siguienteNodo = laberinto.moveUp(nodoActual);
                break;
            case "W":
                siguienteNodo = laberinto.moveWest(nodoActual);
                break;
            case "D":
                siguienteNodo = laberinto.moveDown(nodoActual);
                break;
        }

        // verificar si la transicion es valida (el siguiente nodo no es el nodo actual y no es peligroso)
        if (siguienteNodo != nodoActual && !siguienteNodo.danger) {
            // construir el nuevo historial de movimientos
            String nuevoCamino = estadoActual.historialDeMovimientos.isEmpty() ? 
                                 simbolo : 
                                 estadoActual.historialDeMovimientos + "," + simbolo;
            // retornar el nuevo estado
            return new Estado(siguienteNodo, nuevoCamino);
        }

        // retornar null si la transicion se puede hacer
        return null;
    }
}