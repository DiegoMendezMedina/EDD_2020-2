package mx.unam.ciencias.edd;

/**
 *Index values.
 */
public class Index<T> implements ComparableIndexable<Index<T>> {

    private T elemento;
    private int valor;
    private int indice;

    /**
     * Unico constructor indexables.
     * @param elemento.
     * @param valor del elemento.
     */
    public Index(T elemento, int valor) {
        this.elemento = elemento;
        this.valor = valor;
        indice = -1;
    }

    /**
     * Regresa el valor "indexable".
     * @return valor indexable.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Compara dos valores indexables
     * @param valor index a comparar.
     * @return la resta de ambos.
     */
    @Override public int compareTo(Index<T> index) {
	return valor - index.getValor();
    }

    /**
     * Definde el indice del index.
     * @param int, nuevo indice.
     */
    @Override public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * Regresa el indice del index
     * @return indice del index.
     */
    @Override public int getIndice() {
        return indice;
    } 
}
