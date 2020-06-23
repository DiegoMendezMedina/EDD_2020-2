package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
	    super(elemento);
	    altura = 0;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
	    return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
	    String s = elemento.toString()+" "+ altura + "/"+balance();
	    return s;
        }

	/**
	 *Metodo privado que calcula el balanca de un VerticeAVL.
	 *El balance es la diferencia de las alturas de sus hijos.
	 */
        private int balance() {
	    int i = 0;
	    int d = 0;
            VerticeAVL vi = (VerticeAVL)this.izquierdo;
            Vertice vd = (VerticeAVL)this.derecho;
	    if(vi != null)
		i = vi.altura();
	    if(vd != null)
		d = vd.altura();
	    
            return (i-d);
	}
	
        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
	    return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
	return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	super.agrega(elemento);
	VerticeAVL v = (VerticeAVL)ultimoAgregado;
	rebalancea(v);
    }

    /**
     * Metodo privado  que balancea un arbol desde un vertice que recibe hasta la raiz, recursivo
     * @param VerticeAVL v, vertice agregado o vertice padre del eliminado.
     */
    private void rebalancea(VerticeAVL v) {
        if (v == null)
            return;
	setAltura(v);
        int e = getAltura(izquierdo(v)) - getAltura(derecho(v));
        if (e == -2) {
            VerticeAVL der = derecho(v);
            if (getAltura(izquierdo(der)) - getAltura(derecho(der)) == 1)
                giraDerechaAVL(der);
            giraIzquierdaAVL(v);
        } else if (e == 2) {
            VerticeAVL izq = izquierdo(v);
            if (getAltura(izquierdo(izq)) - getAltura(derecho(izq)) == -1)
                giraIzquierdaAVL(izq);
            giraDerechaAVL(v);
        }
	rebalancea((VerticeAVL)v.padre);
    }
    
    /**
     * Metodo privado que regresa la altura de el verticeAVL que recibe.
     * @param VerticeAVL v, vertice del que se desea conocer su altura;
     */
    private int getAltura(VerticeAVL v) {
        if (v == null)
            return -1;
	return v.altura;
    }
    
    /**
     * Metodo privado que actualiza la altura de el vertice que recibe, v, como
     * la diferencia entre las alturas de sus hijos + 1.
     * @param VerticeAVL v, al que se le actualizará la altura.
     */
    private void setAltura(VerticeAVL v) {
	VerticeAVL vi = (VerticeAVL)v.izquierdo;
        VerticeAVL vd = (VerticeAVL)v.derecho;
        if (vi == null && vd == null) {
            v.altura = 0;
            return;
        }
	v.altura = Math.max(getAltura(vi),getAltura(vd)) + 1; 
    }

    /**
     * Metodo privado que obtiene el hijo derecho del vertice recibido.
    * @param VerticeAVL v, Vertice del que se desea conocer su hijo derecho.
    */
    private VerticeAVL derecho(VerticeAVL v) {
        if (v.derecho == null)
            return null;
        return (VerticeAVL)v.derecho;
    }

    /**
     * Metodo privado que regresa el hijo izquierdo del vértice recibido.
     * @param VerticeAVL v, vertice del que se desea conocer su hijo izquierdo.
     */
    private VerticeAVL izquierdo(VerticeAVL v) {
        if (v.izquierdo == null)
            return null;
        return (VerticeAVL)v.izquierdo;
    }
    
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	VerticeAVL v = (VerticeAVL)busca(elemento);
	if( v == null)
	    return;
	VerticeAVL vertice = (VerticeAVL)super.intercambiaEliminable(v);
	
	if(vertice == null){
	 eliminaVertice(v);
	 elementos --;
	rebalancea((VerticeAVL)v.padre);
	}
	
	else{
	eliminaVertice(vertice);
	elementos--;
	rebalancea((VerticeAVL)vertice.padre);
	}	
    }

    private void giraIzquierdaAVL(VerticeAVL vertice){
        super.giraIzquierda(vertice);
	setAltura(vertice);
	setAltura((VerticeAVL)vertice.padre);
    }

    private void giraDerechaAVL(VerticeAVL vertice){
        super.giraDerecha(vertice);
	setAltura(vertice);
	setAltura((VerticeAVL)vertice.padre);
    }
    
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
