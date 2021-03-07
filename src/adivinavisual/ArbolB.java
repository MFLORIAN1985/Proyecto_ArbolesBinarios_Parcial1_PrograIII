package adivinavisual;

import java.io.Serializable;

/**
 *
 * @author Florian
 */

// Serializable convierte un objeto en un motón de bytes y luego los recupera, al convertir el objeto en byres, se puede enviar por por la red o guardarlo en un fichero
public class ArbolB<T> implements Serializable {

    private static final long serialVersionUID = 1;
    int altura;

    static class Nodo<T> implements Serializable {

        int cant;
        T info;
        Nodo<T> izq, der, raiz;
        Nodo<T> nuevo;

        public Nodo() {

        }

        // Esta pasando un parametro del objeto T con la variable palabra para pasarle el valor a info
        public Nodo(T palabra) {
            info = palabra;
            izq = null;
            der = null;
        }

        //Comprueba que el arbol no esté vacío
        public boolean validaEmpty(Nodo<T> nodo) {
            if (nodo.izq != null || nodo.der != null) {
                return true;
            }
            return false;
        }
        
        
        //Recursividad que se invoca a si misma, cuando el if se cumple sale de la recursividad.
        // Calcula la altura del arbol.
        public int altura(Nodo<T> alturaArbol) {
            if (alturaArbol == null) {
                return -1;
            } else {
                cant = 0;
            }
            return (1 + Math.max(altura(alturaArbol.izq), altura(alturaArbol.der)));
        }

        // Calcula la cantida de nodos del arbol.
        public int cantidad(Nodo<T> reco) {
            if (reco != null) {
                cant++;
                cantidad(reco.izq);
                cantidad(reco.der);
                return cant;
            }
            //cant = 0;
            return -1;
        }
    }


}
