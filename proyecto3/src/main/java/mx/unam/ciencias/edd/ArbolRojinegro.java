package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.	    
            super(elemento);
            this.color = color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            // Aquí va su código.
	    String s = "";
	    if(this.color == Color.ROJO)
		s = "R{"+ elemento.toString()+"}";
	    if(this.color == Color.NEGRO)
		s = "N{"+ elemento.toString()+"}";
	    return s;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
            return (this.color == vertice.color && super.equals(objeto));

        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.	
        return new VerticeRojinegro(elemento);
    }
    
    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if(vertice.getClass() != VerticeRojinegro.class)
	    throw new ClassCastException();
	VerticeRojinegro v = (VerticeRojinegro)vertice;
	return v.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
	// Aquí va su código.
        super.agrega(elemento);
        VerticeRojinegro v = (VerticeRojinegro) getUltimoVerticeAgregado();
        v.color = Color.ROJO;
        rebalanceoAgrega(v);
    }

    /**
     *Metodo para rebalancear despues de agregar.
     *@param VerticeRojinegro v
     */
    private void rebalanceoAgrega(VerticeRojinegro v){
        //caso 1
        if(v.padre == null){
            v.color= Color.NEGRO;
            return;
        }
        //Caso 2
        VerticeRojinegro p = getPadre(v);
        if(p.color == Color.NEGRO)
	    return;

        //caso3
        VerticeRojinegro a = getAbuelo(v);
        VerticeRojinegro t = getTio(v);
        if(t != null && t.color == Color.ROJO){
            t.color = Color.NEGRO;
            p.color = Color.NEGRO;
            a.color = Color.ROJO;
            rebalanceoAgrega(a);
        }else{
            //caso 4
            VerticeRojinegro aux = p;
            if (a.izquierdo == p && p.derecho == v){
                super.giraIzquierda(p);
                p = v;
                v = aux;
            }
            if (a.derecho==p && p.izquierdo == v){
                super.giraDerecha(p);
                p = v;
                v = aux;
            }
            //caso 5	    
            if (v == p.izquierdo){
                super.giraDerecha(a);
            }else{
		super.giraIzquierda(a);
            }
	    p.color = Color.NEGRO;
            a.color = Color.ROJO;
        }

    }

    /**
     *Método auxiliar, regresa al padre.
     */
    private VerticeRojinegro getPadre(VerticeRojinegro v){
	VerticeRojinegro p = (VerticeRojinegro) v.padre;
	return p;
    }

    /**
     *Método auxiliar, regresa al tio.
     */
    private VerticeRojinegro getTio(VerticeRojinegro v){
	VerticeRojinegro a = getAbuelo(v);
	if(v.padre == a.izquierdo)
	    return (VerticeRojinegro) a.derecho;
	else
	    return (VerticeRojinegro) a.izquierdo;
    }

    /**
     *Método auxiliar. regresa al hermano.
     */
    private VerticeRojinegro getHermano(VerticeRojinegro v){
	VerticeRojinegro p = (VerticeRojinegro) v.padre;
	if(v == p.izquierdo)
	    return (VerticeRojinegro) p.derecho;
	else
	    return (VerticeRojinegro) p.izquierdo;
    }

    /**
     *Método auxiliar, regresa al abuelo.
     */
    private VerticeRojinegro getAbuelo(VerticeRojinegro v){
	return (VerticeRojinegro) v.padre.padre;
    }

    /**
     * Método auxiliar que nos dice si v es rojo.
     */
    private boolean esRojo(VerticeRojinegro v){
	return(v != null && v.color == Color.ROJO);
    }

    /**
     * Método auxiliar que nos dice si v es negro o v es una hoja (propiedad 3).
     */
    private boolean esNegro(VerticeRojinegro v){
	return(v == null || v.color == Color.NEGRO );
    }
    
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento){
	// Aquí va su código.
	VerticeRojinegro v = (VerticeRojinegro) busca(elemento);
	if(v == null)
	    return;
	VerticeRojinegro aux = null;
	if(v.hayIzquierdo()){
	    aux = v;
	    v = (VerticeRojinegro) maxEnSubArbol(v.izquierdo);
	    aux.elemento = v.elemento;
	    aux = null;
	}
	if(!v.hayIzquierdo() && !v.hayDerecho()){
	    v.izquierdo = nuevoVertice(null);
	    aux = (VerticeRojinegro) v.izquierdo;
	    aux.padre = v;
	    aux.color = Color.NEGRO;
	}
	VerticeRojinegro h = null;
	
	if(v.hayIzquierdo())
	    h = (VerticeRojinegro) v.izquierdo;
	else
	    h = (VerticeRojinegro) v.derecho;
	desconecta(h, v);
	if(h.color == Color.ROJO || v.color == Color.ROJO){
	    h.color = Color.NEGRO;
	}else{
	    h.color = Color.NEGRO;
	    rebalanceaElimina(h);
	}
	mataFantasma(aux);
	elementos--;
    }

    /**
     * Método que rebalancera despues de haber elminado.
     * @param VerticeRojinegro v
     */
    private void rebalanceaElimina(VerticeRojinegro v){
        //Caso 1:
        if(v.padre == null){
	    v.color = Color.NEGRO;
	    raiz = v;
	    return;
        }
        VerticeRojinegro p = getPadre(v);
        VerticeRojinegro h = getHermano(v);

        //Caso 2:
        if(h.color == Color.ROJO && p.color == Color.NEGRO){
	    p.color = Color.ROJO;
	    h.color = Color.NEGRO;
	    if(p.izquierdo == v)
		super.giraIzquierda(p);
	    else
		super.giraDerecha(p);
	    h = getHermano(v);
	    p = getPadre(v);
        }

        VerticeRojinegro hi = (VerticeRojinegro) h.izquierdo;
        VerticeRojinegro hd = (VerticeRojinegro) h.derecho;
	
        //Caso 3:
        if(p.color == Color.NEGRO && h.color == Color.NEGRO && h!= null && esNegro(hi) && esNegro(hd)){
	    h.color = Color.ROJO;
	    rebalanceaElimina(p);
	    return;
        }

        //Caso 4:
        if(esNegro(h) && esNegro(hi) && esNegro(hd) && esRojo(p)){
	    h.color = Color.ROJO;
	    p.color = Color.NEGRO;
	    return;
        }

        //Caso 5:
        if(p.izquierdo == v && esRojo(hi) && esNegro(hd) && esNegro(h)){
	    h.color = Color.ROJO;
	    hi.color = Color.NEGRO;
	    super.giraDerecha(h);
        }else if(p.derecho == v && esNegro(hi) && esRojo(hd) && esNegro(h)){
	    h.color = Color.ROJO;
	    hd.color = Color.NEGRO;
	    super.giraIzquierda(h);
        }
        h = getHermano(v);
        hi = (VerticeRojinegro) h.izquierdo;
	hd = (VerticeRojinegro) h.derecho;
	
        //Caso 6:
        h.color = p.color;
        p.color = Color.NEGRO;
        if(p.izquierdo == v){
	    hd.color = Color.NEGRO;
	    super.giraIzquierda(p);
        }else{
	    hi.color = Color.NEGRO;
	    super.giraDerecha(p);
        }
    }

    /**
     *Método auxiliar. Sustituyea v por h
     *@param hijo
     *@param v 
     */
    private void desconecta(VerticeRojinegro h, VerticeRojinegro v){
	if(v.padre == null){
	    raiz = h;
	    raiz.padre = null;
	    return;
	}
	h.padre = v.padre;	
	if(v.padre.izquierdo == v){
	    if(v.izquierdo == h)
		v.padre.izquierdo = v.izquierdo;
	    else
		v.padre.izquierdo = v.derecho;
	}
	if(v.padre.derecho == v ){
	    if(v.izquierdo == h)
		v.padre.derecho = v.izquierdo;
	    else
		v.padre.derecho = v.derecho;
	}
    }

    /**
     * Método que elimina al vértice fantasma 
     * @param VerticeRojinegro vertice fantasma
     */
    private void mataFantasma(VerticeRojinegro v){
	if(v == null)
	    return;
	if(v.padre == null){
	    this.raiz = null;
	    ultimoAgregado = null;
	}else if(v.padre.derecho == v)
	    v.padre.derecho = null;
	else{
	    v.padre.izquierdo = null;
	}
    }


    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
    
