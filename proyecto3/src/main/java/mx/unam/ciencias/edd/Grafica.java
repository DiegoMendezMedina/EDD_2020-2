package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
          iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            this.vecinos = new Lista<Vecino>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

	public void setColor(Color color){
	    this.color = color;
	}

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Método para cambiar la distancia de un vértice */
	private void setDistancia(double distancia){
          this.distancia = distancia;
        }
	
        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            if (distancia < vertice.distancia)
                return -1;
            else if (distancia > vertice.distancia)
                return 1;
            return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            return vecino.get();
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return vecino.getColor();
        }
	
        /* Regresa el peso del vecino. */
        private double getPeso() {	    
            return peso;
        }
	
	/* Define el color del vecino.*/
	private void setColor(Color color){
	    vecino.color = color;
	}
	
        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos();
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice>();
	aristas = 0;
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *         había sido agregado a la gráfica.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
	    throw new IllegalArgumentException("No se pueden agregar elementos vacios");
	
	for(Vertice v : vertices) 
            if (v.elemento.equals(elemento))
                throw new IllegalArgumentException("El elemento ya se encuentra en la grafica");
	vertices.agregaFinal(new Vertice(elemento));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        conecta(a, b, 1.0);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
      if(!contiene(a) || !contiene(b))
	    throw new NoSuchElementException("Alguno de los elementos no forma parte de la grafica");
	if(a.equals(b) || sonVecinos(a, b) || peso < 0)
	    throw new IllegalArgumentException("Elementos ya conectados");
	
	Vertice va = audicion(vertice(a));
	Vertice vb = audicion(vertice(b));
	
	va.vecinos.agregaFinal(new Vecino(vb, peso));
	vb.vecinos.agregaFinal(new Vecino(va, peso));
	aristas++;
    }

    /**
     * Metodo auxiliar. Hace una audicion a los vertices para poder acceder a sus atributos.
     * @param el vertice a hacer audicion.
     * @return el vertice como instancia de Vertice.
     */
    private Vertice audicion(VerticeGrafica<T> v) {
        return (Vertice)v;
    }
    
    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
     if(a == null || b == null || !contiene(a) || !contiene(b))
	    throw new NoSuchElementException("Alguno de los elementos no forma parte de la grafica.");        
	
        Vertice va = audicion(vertice(a));
        Vertice vb = audicion(vertice(b));
	
	if (!sonVecinos(a,b) || a.equals(b))
            throw new IllegalArgumentException("Elementos no conectados.");
	
        Vecino vab = null;
        Vecino vba = null;        
        for (Vecino ve : va.vecinos)
            if (ve.vecino.equals(vb))
                vab = ve;

        for (Vecino ve : vb.vecinos)
            if (ve.vecino.equals(va))
                vba = ve;
	
        va.vecinos.elimina(vab);
        vb.vecinos.elimina(vba);
	aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (Vertice v : vertices)
            if (elemento.equals(v.elemento))
                return true;
	return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
	if (!contiene(elemento))
	    throw new NoSuchElementException("El elemento no está en la gráfica.");
	Vertice vertice = audicion(vertice(elemento));
	for (Vecino vecino : vertice.vecinos)
	    desconecta(vecino.vecino.get(), elemento);
	vertices.elimina(vertice);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
	if (!this.contiene(a) || !this.contiene(b))
            throw new NoSuchElementException("Alguno de los elemento no está en la gráfica.");
	
        Vertice va = audicion(vertice(a));
        Vertice vb = audicion(vertice(b));	
        for (Vecino ve : va.vecinos)
            if (ve.vecino.equals(vb))
                return true;
	return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
	if (!this.contiene(a) || !this.contiene(b))
            throw new NoSuchElementException("Alguno de los elemento no está en la gráfica.");
        if (!sonVecinos(a,b))
            throw new IllegalArgumentException("Elementos no conectados.");

	return buscaVecino(audicion(vertice(a)),b).peso;	
    }

     /**
     * Método axuliar que busca el vecino y lo regresa.
     * @param vertice El vértice en el que vamos a buscar el vecino.
     * @param elemento El elemento que debe tener el vecino a buscar.
     * @return el vecino del vertice, null en otro caso.
     */
    private Vecino buscaVecino(Vertice vertice, T elemento) {
        for (Vecino vecino : vertice.vecinos)
            if (vecino.vecino.get().equals(elemento))
                return vecino;
        return null;
    } 
    
    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
	if(!contiene(a) || !contiene(b))
	    throw new NoSuchElementException("Alguno de los elementos no está contenido en la gráfica");
        if(!sonVecinos(a, b)) throw new IllegalArgumentException("Elementos no conectados");
	
        Vertice va = audicion(vertice(a));
        Vertice vb = audicion(vertice(b));
	
        for(Vecino v : va.vecinos){
	    if(v.vecino.equals(vb))
		v.peso = peso;
        }
        for(Vecino v : vb.vecinos){
	    if(v.vecino.equals(va))
		v.peso = peso;
	}
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
	if (!this.contiene(elemento))
            throw new NoSuchElementException("El elemento no está en la gráfica.");
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                return v;
	return null;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
	if(vertice == null )
	    throw new IllegalArgumentException("Vertice vacio");
	if(vertice.getClass() != Vecino.class && vertice.getClass() != Vertice.class)
	    throw new IllegalArgumentException("Error en la clase");
	 
        if(vertice.getClass() == Vertice.class){
	    Vertice v = (Vertice) vertice;
	    v.color = color;
        }
	else  if(vertice.getClass() == Vecino.class){
	    Vecino v = (Vecino) vertice;
	    v.vecino.color = color;
	}
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
	if(esVacia())
	    return true;	
        Vertice e = vertices.getPrimero();
        paraCadaVertice(v -> setColor(v, Color.ROJO));
        setColor(e, Color.NEGRO);
        Cola<Vertice> cola = new Cola<>();
	cola.mete(e);
        while(!cola.esVacia()){
	    e = cola.saca();
	    for(Vecino v : e.vecinos)
		if(v.getColor().equals(Color.ROJO)){
		    setColor(v, Color.NEGRO);
		    cola.mete(v.vecino);
		}
	}
	
    for(Vertice v : vertices)
	if(!v.getColor().equals(Color.NEGRO))
            return false;
    return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
	for (Vertice v : vertices)
            accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
	recorrido(elemento,new Cola<Vertice>(), accion);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
	recorrido(elemento,new Pila<Vertice>(), accion);
    }

    /**
     *Metodo auxiliara para hacer bfs y dfs.
     *@param elemento sobre cuyo vertice comineza el recorrido.
     *@param meteSaca, pila o cola.
     *@param accion a realizar.
     */
    private void recorrido(T elemento, MeteSaca<Vertice> meteSaca, AccionVerticeGrafica<T> accion) {
        if (vertices.esVacia())
            return;
        Vertice v = audicion(vertice(elemento));
        for (Vertice ve : vertices)
            ve.color = Color.NINGUNO;
        meteSaca.mete(v);
        while (!meteSaca.esVacia()) {
            v = meteSaca.saca();
            v.color = Color.ROJO;
            accion.actua(v);
            for (Vecino vv : v.vecinos) {
                if (vv.getColor() == Color.ROJO)
                    continue;
                vv.setColor(Color.ROJO);
                meteSaca.mete(vv.vecino);
            }
        }
	paraCadaVertice(w -> setColor(w, Color.NINGUNO));
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        paraCadaVertice(v -> setColor(v, Color.ROJO));
        String s = "{";
        String e = "{";
        for(Vertice v : vertices){
           s += v.get() + ", ";
          for(Vecino vv : v.vecinos){
            if(vv.getColor() == Color.ROJO)
              e += "(" + v.get() + ", " + vv.get() + "), ";
            v.color = Color.NEGRO;
          }
        }
        return s + "}, " + e + "}";
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
	if(aristas != grafica.getAristas() || grafica.vertices.getLongitud() != vertices.getLongitud())
	    return false;
	
        for(Vertice v : vertices){
	    if(!grafica.contiene(v.get()))
		return false;
        }

        for(Vertice v : vertices){
	    for(Vecino vv : v.vecinos){
		if(!grafica.sonVecinos(v.elemento, vv.get()))
		    return false;
	    }
        }
	return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        if( origen == null || destino == null)
	    throw new IllegalArgumentException("Alguno de los elementos es vacio");
        Vertice vorigen  = audicion(vertice(origen));
	Vertice vdestino = audicion(vertice(destino));
        if(vorigen == vdestino){
            Lista<VerticeGrafica<T>> lista = new Lista<>();
            lista.agregaFinal(vorigen);
            return lista;
        }

        inicializaDistancias();
        vorigen.setDistancia(0);	
        Cola<Vertice> colaVertices  = new Cola<>();
	Vertice aux = null;
        colaVertices.mete(vorigen);
        while(!colaVertices.esVacia()){         
	    aux= colaVertices.saca();
          for(Vecino v : aux.vecinos){
            if(v.vecino.distancia == 10000000  ){
              v.vecino.distancia = aux.distancia + 1;
              colaVertices.mete(v.vecino);
            }
          }
        }
        return reconstruyeTrayectoria(vorigen, vdestino);
    }

    /**
    * Reconstruye la trayectoria una vez que se actualizaron las distancias en 
    * los vértices de la gráfica.
    * @param origen, el elemento de donde partimos.
    * @param destino, el elemento al que hay que llegar.
    * @return una lista con la trayectoria.
    */   
    private Lista<VerticeGrafica<T>> reconstruyeTrayectoria(Vertice origen, Vertice destino){	
      Vertice aux = destino;
      Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
      
      if(destino.distancia != 10000000){
        while(aux != origen){
          for(Vecino v : aux.vecinos){	      
            if(aux.distancia - v.peso  == v.vecino.distancia){
              trayectoria.agregaInicio(aux);
              aux = v.vecino;
              break;
            }
          }
          if(aux == origen)
	      trayectoria.agregaInicio(origen);
        }
      }
      return trayectoria;
    }

    
    private void inicializaDistancias(){
      for(Vertice vertice : vertices){
        vertice.distancia = 10000000;
      }
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
      if( origen == null || destino == null)
	  throw new IllegalArgumentException("Alguno de los elementos es vacio.");
      Vertice vorigen  = audicion(vertice(origen));
      Vertice vdestino = audicion(vertice(destino));
      
      if(origen == destino){
          Lista<VerticeGrafica<T>> lista = new Lista<>();
          lista.agregaFinal(vorigen);
          return lista;
      }     
      inicializaDistancias();
      vorigen.setDistancia(0);
      MonticuloDijkstra<Vertice> monticuloVertices = new MonticuloMinimo<>(vertices);          
      Vertice aux = null;
      
      while(!monticuloVertices.esVacia()){
	  aux= monticuloVertices.elimina();
        for(Vecino v : aux.vecinos){
          if(v.vecino.distancia > aux.distancia + v.getPeso() ){
            v.vecino.distancia = aux.distancia + v.getPeso();
            monticuloVertices.reordena(v.vecino);
          }
        }
      }
      
      return reconstruyeTrayectoria(vorigen, vdestino);
    }
   
}

