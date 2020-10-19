package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            // Aquí va su código.
	    pila= new Pila<Vertice>();
	    Vertice v=raiz;
	    while(v!=null){
		pila.mete(v);
		v=v.izquierdo;
	    }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.
	    if(pila.esVacia())
		throw new java.util.NoSuchElementException("Arbol ordenado vacio");
	    Vertice v=pila.saca();
	    if(v.hayDerecho()){
		Vertice d=v.derecho;
		while(d!=null){
		    pila.mete(d);
		    d=d.izquierdo;
		}
	    }
	    return v.elemento;
        }	
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if(elemento==null)
	    throw new IllegalArgumentException("No se pueden agregar elementos vacios");
	Vertice v= nuevoVertice(elemento);
	if(raiz==null){
	    ultimoAgregado=v;
	    raiz=v;
	    elementos=1;
	    return;	    
	}
	agrega(raiz, elemento);
    }
    /**
     *Metodo auxiliar para agregar
     **/
    private void agrega(Vertice v, T e){	
	if(e.compareTo(v.elemento)>0 && v.derecho==null){
            Vertice aux=nuevoVertice(e);
            v.derecho=aux;
            aux.padre=v;
            elementos++;
            ultimoAgregado=aux;
	    return;
        }
	if(e.compareTo(v.elemento)<=0 && v.izquierdo==null){
            Vertice aux=nuevoVertice(e);
            v.izquierdo=aux;
            aux.padre=v;
            ultimoAgregado=aux;
            elementos++;
            return;
        }
        if(e.compareTo(v.elemento)>0)
            agrega(v.derecho, e);
        if(e.compareTo(v.elemento)<=0)
	    agrega(v.izquierdo, e);
    }
    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	Vertice v=vertice(busca(elemento));
	if(v== null)
            return;
        if(v==raiz && elementos==1){
            raiz=null;
            elementos=0;
            return;
        }
        if(v.izquierdo==null && v.derecho==null){
	    if(v.padre.izquierdo==v){
                v.padre.izquierdo=null;
                elementos--;
                return;
            }
            if(v.padre.derecho==v){
		v.padre.derecho=null;
                elementos--;
                return;
            }
        }
        if((v.izquierdo!=null && v.derecho==null) ||
            (v.izquierdo==null && v.derecho!=null)){
            eliminaVertice(v);
            elementos--;
            return;
        }
        if(v.izquierdo!=null && v.derecho!=null){
            eliminaVertice(intercambiaEliminable(v));
            elementos--;
            return;
	}
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
	if(vertice.izquierdo==null || vertice.derecho==null)
            return null;
        Vertice v=maxEnSubArbol(vertice.izquierdo);
        T e=vertice.elemento;
        vertice.elemento=v.elemento;
        v.elemento=e;
	return v;
    }
    /**
     *Metodo auxiliar. Max subarbol
     **/
    private Vertice maxEnSubArbol(Vertice v){
        return v.derecho == null ? v : maxEnSubArbol(v.derecho);
    }
    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.
	Vertice p=vertice.padre;
        Vertice u=null;
	if (vertice.derecho!=null && vertice.izquierdo!=null)
            return;
	
        if(vertice.derecho!=null)
            u=vertice.derecho;
	
        if(vertice.izquierdo != null)
            u = vertice.izquierdo;
	
        if(vertice.padre!=null){
	    
            if(vertice.padre.izquierdo == vertice)
                vertice.padre.izquierdo = u;
	    
            if(vertice.padre.derecho == vertice)
                vertice.padre.derecho = u;
	    
        }
	
        if(vertice.padre==null)
            raiz=u;
        if(u!=null)
            u.padre=p;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
	return esVacia() ? null : busca(raiz, elemento);
    }
    /**
     *Metodo auxiliar para buscar. Recursivo
     **/
    private VerticeArbolBinario<T> busca(Vertice v, T e){
	if(v.elemento.equals(e))
            return v;
	
        if(v.elemento.compareTo(e) > 0 && v.izquierdo != null)
            return busca(v.izquierdo, e);
	
        if (v.elemento.compareTo(e) < 0 && v.derecho != null)
            return busca(v.derecho, e);
	
	return null;
    }
    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        // Aquí va su código.
	return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
	Vertice v=vertice(vertice), p, s, r;
        boolean izq;
        if (v.izquierdo == null)
            return;
	if (v == raiz){
            raiz=v.izquierdo;
            p=v.padre;
            s=v.izquierdo.derecho;
            r=v.izquierdo;
            v.padre=r;
            v.padre.derecho=v;
            v.izquierdo=s;
            v.padre.padre=p;
	    
            if (v.izquierdo!=null)
                v.izquierdo.padre=v;
            return;
        }
        p=v.padre;
        s=v.izquierdo.derecho;
        r=v.izquierdo;
	
        if(v==v.padre.izquierdo)
            izq=true;
        else
            izq=false;
        v.padre=r;
        v.padre.derecho=v;
        v.izquierdo=s;
        v.padre.padre=p;
	
        if(izq==true)
            p.izquierdo=v.padre;
        else
            p.derecho=v.padre;
        if(v.izquierdo!=null)
	    v.izquierdo.padre=v;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
	Vertice v=vertice(vertice), p, s, l;
        boolean der;
	if(v.derecho==null)
            return;
	if(v==raiz){
            raiz=v.derecho;
	    p=v.padre;
	    s=v.derecho.izquierdo;
            l=v.derecho;
            v.padre=l;
            v.padre.izquierdo=v;
	    v.derecho=s;
            v.padre.padre=p;
            if (v.derecho!=null)
		v.derecho.padre=v;
            return;
        }
	
        if(v==v.padre.izquierdo)
            der=true;
	else
            der=false;
        p=v.padre;
        s=v.derecho.izquierdo;
        l=v.derecho;
        v.padre=l;
        v.padre.izquierdo=v;
        v.derecho=s;
        v.padre.padre=p;
	
        if(v.derecho!=null)
            v.derecho.padre=v;
        if (der==true)
            p.izquierdo=v.padre;
        else
	    p.derecho=v.padre;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
	if(esVacia())
	    return;
	dfsPreOrder(raiz, accion);
    }
    /**
     *dfsPreOrder recursivo
     **/
    private void dfsPreOrder(Vertice v, AccionVerticeArbolBinario<T> accion){
        accion.actua(v);
        if(v.hayIzquierdo())
            dfsPreOrder(v.izquierdo, accion);
        if(v.hayDerecho())
            dfsPreOrder(v.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
	if(esVacia())
	    return;
	dfsInOrder(raiz, accion);
    }
    /**
     *dfsInOrder recursivo
     **/
    private void dfsInOrder(Vertice v, AccionVerticeArbolBinario<T> accion){
        if(v.hayIzquierdo())
            dfsInOrder(v.izquierdo, accion);
        accion.actua(v);
        if(v.hayDerecho())
	    dfsInOrder(v.derecho, accion);
    }
    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
	if(esVacia())
	    return;
	dfsPostOrder(raiz, accion);
    }
    /**
     *dfsInOrder recursivo
     **/
    private void dfsPostOrder(Vertice v, AccionVerticeArbolBinario<T> accion){
        if(v.hayIzquierdo())
            dfsPostOrder(v.izquierdo, accion);
	
        if(v.hayDerecho())
            dfsPostOrder(v.derecho, accion);
	
        accion.actua(v);
    }
    
    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
