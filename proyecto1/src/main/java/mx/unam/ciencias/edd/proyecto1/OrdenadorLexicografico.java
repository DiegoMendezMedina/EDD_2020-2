package mx.unam.ciencias.edd.proyecto1;
/**
 *Clase Ordenador lexicografico.
 *Ordena lexicograficamente una lista de lineas. 
 */
import mx.unam.ciencias.edd.Lista;

public class OrdenadorLexicografico {
    
    private Lista<Linea> lineas;
    private Lista<Linea> salida;
    private String s;
    /**
     *Unico constructor de la clase.
     */
    public OrdenadorLexicografico(Lista<Linea> texto){
	lineas = texto;
	salida = new Lista<Linea>();
    }
    /**
     *Metodo ordenar.
     *Utiliza MergeSort para ordenar las Lineas.
     */
    public Lista<Linea> ordenar(){
	this.salida = this.lineas.mergeSort((a, b) -> a.compareTo(b));
	return salida;
    }

    /**
     *Metodo reversa.
     *Invierte el oriden lexicografico (si eso tiene sentido).
     */
    public Lista<Linea> reversa(){
	this.salida = this.lineas.mergeSort((a, b) -> a.compareTo(b));
	this.salida = this.salida.reversa();
	return salida;
    }
}
