package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
	    if(indice >= elementos)
		throw new NoSuchElementException("No existe un siguiente");
	    return arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
	    this.elemento = elemento;
	    indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
	    return this.indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
	    this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
	    return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
	arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        // Aquí va su código.
	this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
	elementos = n;	
	int i = 0;
	arbol = nuevoArreglo(n);
	for(T e : iterable){
	    arbol[i] = e;
	    e.setIndice(i);
	    i++;
	}
	//Amortizacion
	for(i = elementos / 2 - 1; i >= 0; i--)
	    acomodaAbajo(arbol[i]);
    }

    /**
     *Metodo auxiliar y privado acomodaAbajo. 
     *Acomoda el subarbol del elemento recibido.
     *@param elemento, elemento de donde se va a acomodar.
     */
    private void acomodaAbajo(T elemento) {
	if (!indiceValido(elemento.getIndice()))
	    return;
	
        int izquierdo = 2 * elemento.getIndice() + 1;
        int derecho   = 2 * elemento.getIndice() + 2;
	
        if(!indiceValido(izquierdo) && !indiceValido(derecho))
	    return;
	
        int minimo = derecho;
	
	    if(indiceValido(derecho)) {
		if(arbol[minimo].compareTo(arbol[izquierdo]) >= 0)
		    minimo = izquierdo;
	    }else
		minimo = izquierdo;
	    
        if(elemento.compareTo(arbol[minimo]) > 0){
	    intercambia(elemento, arbol[minimo]);
	    acomodaAbajo(elemento);
	}	
    } 

    /**
     *Metodo privado indiceValido.
     *Nos indica si el entero recibido forma parte del indice del monticulo.
     *@param i, el indice de algun elemento del monticulo o no.
     *@return true si el entero recibido es mayor o igual a 0 y menor que
     *arbol.length, false en caso contrario.
     */
    private boolean indiceValido(int i){
	return (i >= 0 && i < elementos);
    }        

    /**
     *Metodo privado intercambia.
     *Intercambia dos elementos en el monticulo.
     *@param i, primero elemento a intercambiar.
     *@param j, segundo elemento a intercambiar.
     */
    private void intercambia(T i, T j){
	if(i.equals(j))
	    return;
	int u = i.getIndice();
	int w = j.getIndice();
	arbol[u].setIndice(w);
	arbol[w].setIndice(u);
	T aux = arbol[u];
	arbol[u] = arbol[w];
	arbol[w] = aux;
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if(elementos == arbol.length)
	    creceArreglo();
	
        arbol[elementos] = elemento;
        elemento.setIndice(elementos);
        elementos++;
	acomodaArriba(elemento);
    }

    /**
     *Duplica la longitud de el monticulo, arbol. Deja a los elementos como se encontraban.
     */
    private void creceArreglo() {
        T[] aux = nuevoArreglo(2 * arbol.length);
	elementos= 0;
        for (T e : arbol){
            aux[elementos] = e;
	    elementos++;
	}
        this.arbol = aux;
    }

    /**
     *Intercambia los valores del elemento recibido y su padre y redefinimos a elemento, como el padre.
     *@param el elemento que intercambia valores con su padre.
     */
    private void acomodaArriba(T elemento) {
	int padre = elemento.getIndice() - 1;	
	if(padre >= 0)
	    padre = padre/2;
	
	if(!indiceValido(padre) || elemento.compareTo(arbol[padre]) >= 0)
	    return;
	intercambia(arbol[padre], elemento);
	acomodaArriba(elemento);
    }

    
    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.
	if(esVacia())
	    throw new IllegalStateException("Monticulo vacio");
	
	T raiz = arbol[0];
	elimina(raiz);
	return raiz;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.	       
	int i = elemento.getIndice();
        if(!indiceValido(i))
	    return;
        intercambia(arbol[i], arbol[elementos-1]);
        elementos--;
        elemento.setIndice(-1);
	reordena(arbol[i]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	int i = elemento.getIndice();
	if(i < 0 || i >= elementos)
	    return false;
	if(arbol[i] == elemento)
	    return true;
	return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
	return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
	for(int i = 0; i < arbol.length; i++)
	    arbol[i] = null;
	elementos = 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.
	int i = elemento.getIndice();
        int padre = elemento.getIndice() - 1;
	if( padre > 1)
	    padre = padre/2;
	acomodaAbajo(elemento);
	acomodaArriba(elemento);
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
	if(i < 0 || i >= elementos)
	    throw new NoSuchElementException("Indice fuera de rango");
	return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
	String salida = "";
	for(int i = 0; i < elementos; i++){
	    salida = salida + arbol[i] + ", ";
	}
	return salida;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código.
	if(this.getElementos() != monticulo.getElementos())
	    return false;
	for (int i = 0; i < this.getElementos() ; i++)
            if (!arbol[i].equals(monticulo.get(i)))
                return false;
	return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
	Lista<Adaptador<T>> l1 = new Lista<>();
	
        for(T e : coleccion)
	    l1.agregaFinal(new Adaptador<T>(e));
	
        Lista<T> l2 = new Lista<T>();
        MonticuloMinimo<Adaptador<T>> min = new MonticuloMinimo<>(l1);
	
        while(!min.esVacia()){
	    l2.agregaFinal(min.elimina().elemento);
	}	
	return l2;
    }
}
