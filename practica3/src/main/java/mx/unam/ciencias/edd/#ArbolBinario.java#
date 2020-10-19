package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para Ã¡rboles binarios genÃ©ricos.</p>
 *
 * <p>La clase proporciona las operaciones bÃ¡sicas para Ã¡rboles binarios, pero
 * deja la implementaciÃ³n de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vÃ©rtices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vÃ©rtice. */
        public T elemento;
        /** El padre del vÃ©rtice. */
        public Vertice padre;
        /** El izquierdo del vÃ©rtice. */
        public Vertice izquierdo;
        /** El derecho del vÃ©rtice. */
        public Vertice derecho;

        /**
         * Constructor Ãºnico que recibe un elemento.
         * @param elemento el elemento del vÃ©rtice.
         */
        public Vertice(T elemento) {
            // AquÃ­ va su cÃ³digo.
	    this.elemento=elemento;
        }

        /**
         * Nos dice si el vÃ©rtice tiene un padre.
         * @return <code>true</code> si el vÃ©rtice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayPadre() {
            // AquÃ­ va su cÃ³digo.
	    return padre!=null;
        }

        /**
         * Nos dice si el vÃ©rtice tiene un izquierdo.
         * @return <code>true</code> si el vÃ©rtice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            // AquÃ­ va su cÃ³digo.
	    return izquierdo!=null;
        }

        /**
         * Nos dice si el vÃ©rtice tiene un derecho.
         * @return <code>true</code> si el vÃ©rtice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayDerecho() {
            // AquÃ­ va su cÃ³digo.
	    return derecho!=null;
        }

        /**
         * Regresa el padre del vÃ©rtice.
         * @return el padre del vÃ©rtice.
         * @throws NoSuchElementException si el vÃ©rtice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            // AquÃ­ va su cÃ³digo.
	    if(padre==null)
		throw new NoSuchElementException(this.elemento+" no tiene padre");
	    return padre;
        }

        /**
         * Regresa el izquierdo del vÃ©rtice.
         * @return el izquierdo del vÃ©rtice.
         * @throws NoSuchElementException si el vÃ©rtice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            // AquÃ­ va su cÃ³digo.
	    if(izquierdo==null)
		throw new NoSuchElementException("No tiene vertice izquierdo");
	    return izquierdo;
        }

        /**
         * Regresa el derecho del vÃ©rtice.
         * @return el derecho del vÃ©rtice.
         * @throws NoSuchElementException si el vÃ©rtice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            // AquÃ­ va su cÃ³digo.
	    if(derecho==null)
		throw new NoSuchElementException(this.elemento+" no tiene vertice derecho");
	    return derecho;
        }

        /**
         * Regresa la altura del vÃ©rtice.
         * @return la altura del vÃ©rtice.
         */
        @Override public int altura() {
            // AquÃ­ va su cÃ³digo.
	    if(!hayIzquierdo() && !hayDerecho())
		return 0;
	    
	    if(hayIzquierdo() && !hayDerecho())
		return izquierdo.altura()+1;
	    
	    if(!hayIzquierdo() && hayDerecho())
		return derecho.altura()+1;
	    
	    return Math.max(izquierdo.altura(), derecho.altura())+1;
        }

        /**
         * Regresa la profundidad del vÃ©rtice.
         * @return la profundidad del vÃ©rtice.
         */
        @Override public int profundidad() {
            // AquÃ­ va su cÃ³digo.
	    if(!hayPadre())
		return 0;
	    return padre.profundidad()+1;
        }
	
        /**
         * Regresa el elemento al que apunta el vÃ©rtice.
         * @return el elemento al que apunta el vÃ©rtice.
         */
        @Override public T get() {
            // AquÃ­ va su cÃ³digo.
	    return this.elemento;
        }

        /**
         * Compara el vÃ©rtice con otro objeto. La comparaciÃ³n es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el mÃ©todo {@link Vertice#equals}.
         * @param objeto el objeto con el cual se compararÃ¡ el vÃ©rtice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de Ã©ste
         *         vÃ©rtice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
            // AquÃ­ va su cÃ³digo.
	    if(vertice == null || !elemento.equals(vertice.elemento))
		return false;
	    if(elemento.equals(vertice.elemento)){
		if(hayIzquierdo() && hayDerecho())
		    return izquierdo.equals(vertice.izquierdo) && derecho.equals(vertice.derecho);
		if(hayIzquierdo() && vertice.derecho == null)
		    return izquierdo.equals(vertice.izquierdo);
		if(hayDerecho() && vertice.izquierdo == null)
		    return derecho.equals(vertice.derecho);
	    }
	    return vertice.izquierdo == null && vertice.derecho == null;
	}

        /**
         * Regresa una representaciÃ³n en cadena del vÃ©rtice.
         * @return una representaciÃ³n en cadena del vÃ©rtice.
         */
        public String toString() {
            // AquÃ­ va su cÃ³digo.
	    return this.elemento.toString();
        }
}

    /** La raÃ­z del Ã¡rbol. */
    protected Vertice raiz;
    /** El nÃºmero de elementos */
    protected int elementos;

    /**
     * Constructor sin parÃ¡metros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un Ã¡rbol binario a partir de una colecciÃ³n. El Ã¡rbol binario
     * tendrÃ¡ los mismos elementos que la colecciÃ³n recibida.
     * @param coleccion la colecciÃ³n a partir de la cual creamos el Ã¡rbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        // AquÃ­ va su cÃ³digo.
	for(T e:coleccion)
	    agrega(e);
    }

    /**
     * Construye un nuevo vÃ©rtice, usando una instancia de {@link Vertice}. Para
     * crear vÃ©rtices se debe utilizar este mÃ©todo en lugar del operador
     * <code>new</code>, para que las clases herederas de Ã©sta puedan
     * sobrecargarlo y permitir que cada estructura de Ã¡rbol binario utilice
     * distintos tipos de vÃ©rtices.
     * @param elemento el elemento dentro del vÃ©rtice.
     * @return un nuevo vÃ©rtice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        // AquÃ­ va su cÃ³digo.
	return new Vertice(elemento);
    }

    /**
     * Regresa la altura del Ã¡rbol. La altura de un Ã¡rbol es la altura de su
     * raÃ­z.
     * @return la altura del Ã¡rbol.
     */
    public int altura() {
        // AquÃ­ va su cÃ³digo.
	if(raiz==null)
	    return -1;
	return raiz.altura();
    }

    /**
     * Regresa el nÃºmero de elementos que se han agregado al Ã¡rbol.
     * @return el nÃºmero de elementos en el Ã¡rbol.
     */
    @Override public int getElementos() {
        // AquÃ­ va su cÃ³digo.
	return elementos;
    }

    /**
     * Nos dice si un elemento estÃ¡ en el Ã¡rbol binario.
     * @param elemento el elemento que queremos comprobar si estÃ¡ en el Ã¡rbol.
     * @return <code>true</code> si el elemento estÃ¡ en el Ã¡rbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // AquÃ­ va su cÃ³digo.
	return busca(elemento)!=null;
    }

    /**
     * Busca el vÃ©rtice de un elemento en el Ã¡rbol. Si no lo encuentra regresa
     * <code>null</code>.
     * @param elemento el elemento para buscar el vÃ©rtice.
     * @return un vÃ©rtice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        // AquÃ­ va su cÃ³digo.
	return busca(this.raiz,elemento);
	
    }

    /**
     *Metodo auxiliar busca. Recursivo.
     **/
    private Vertice busca(Vertice v, T e){
	if(v==null)
	    return null;
	if(v.elemento.equals(e))
	    return v;
	Vertice izq=busca(v.izquierdo,e);
	Vertice der=busca(v.derecho, e);
	if(izq!=null)
	    return izq;
	if(der!=null)
	    return der;
	return null;
    }
    /**
     * Regresa el vÃ©rtice que contiene la raÃ­z del Ã¡rbol.
     * @return el vÃ©rtice que contiene la raÃ­z del Ã¡rbol.
     * @throws NoSuchElementException si el Ã¡rbol es vacÃ­o.
     */
    public VerticeArbolBinario<T> raiz() {
        // AquÃ­ va su cÃ³digo.
	if(raiz==null)
	    throw new NoSuchElementException("Arbol vacio");
	return raiz;
    }

    /**
     * Nos dice si el Ã¡rbol es vacÃ­o.
     * @return <code>true</code> si el Ã¡rbol es vacÃ­o, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // AquÃ­ va su cÃ³digo.
	return raiz==null;
    }

    /**
     * Limpia el Ã¡rbol de elementos, dejÃ¡ndolo vacÃ­o.
     */
    @Override public void limpia() {
        // AquÃ­ va su cÃ³digo.
	raiz=null;
	elementos=0;
    }

    /**
     * Compara el Ã¡rbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el Ã¡rbol.
     * @return <code>true</code> si el objeto recibido es un Ã¡rbol binario y los
     *         Ã¡rboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
        // AquÃ­ va su cÃ³digo.
	if(arbol == null)
	    return false;
	if(!esVacia())
	    return raiz.equals(arbol.raiz);
	return esVacia() && arbol.esVacia();
    }

    /**
     * Regresa una representaciÃ³n en cadena del Ã¡rbol.
     * @return una representaciÃ³n en cadena del Ã¡rbol.
     */
    @Override public String toString() {
        if(esVacia())
	    return "";
	int[] a = new int[altura()+1];
	for(int i = 0; i < altura()+1; i++)
	    a[i] = 0;
	return toString(raiz, 0, a);
    }

    private String toString(Vertice v, int l, int[] a){
	String s = v.toString() + "\n";
	a[l] = 1;
	if(v.izquierdo != null && v.derecho != null){
	    s += dibujaEspacios(l, a);
	    s += "├─›";
	    s += toString(v.izquierdo, l+1, a);
	    s += dibujaEspacios(l, a);
	    s += "└─»";
	    a[l] = 0;
	    s += toString(v.derecho, l+1, a);
	    return s;
	}else if(v.izquierdo != null){
	    s += dibujaEspacios(l, a);
	    s += "└─›";
	    a[l] = 0;
	    s += toString(v.izquierdo, l+1, a);
	    return s;
	}else if(v.derecho != null){
	    s += dibujaEspacios(l, a);
            s += "└─»";
            a[l] = 0;
            s += toString(v.derecho, l+1, a);
            return s;
	}
	return s;
    }

    private String dibujaEspacios(int l, int[] a){
	String s = "";
        for(int i = 0; i < l; i++){
            if (a[i] == 1)
                s += "│  ";
            else
                s += "   ";
        }
        return s;
    }
    /**
     * Convierte el vÃ©rtice (visto como instancia de {@link
     * VerticeArbolBinario}) en vÃ©rtice (visto como instancia de {@link
     * Vertice}). MÃ©todo auxiliar para hacer esta audiciÃ³n en un Ãºnico lugar.
     * @param vertice el vÃ©rtice de Ã¡rbol binario que queremos como vÃ©rtice.
     * @return el vÃ©rtice recibido visto como vÃ©rtice.
     * @throws ClassCastException si el vÃ©rtice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
