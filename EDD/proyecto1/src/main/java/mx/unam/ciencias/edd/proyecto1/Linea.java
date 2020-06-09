package mx.unam.ciencias.edd.proyecto1;
/**
 *Clase Linea.
 *Las lineas nos permiten comparar los renglones recibidos en el
 *ordenador lexicografico.
 * Implementa a comparable para poder ordenar los objetos de esta clase.
 */
public class Linea implements Comparable<Linea> {
    private String linea; // Linea que se compara.
    private String lineaimp; // Linea que se imprimira.
    private boolean espacio; // Nos sirve para saber si la linea no tiene caracteres
    private int longEs; //longitud de los espacios
    /** Unico constructor de la clase,
     * Verifica si la linea es unicamente por espacios.
     * @param String que se le asignara a la Linea.
     */
    public Linea(String linea){
	this.lineaimp = linea;
	this.linea = linea.toLowerCase();
	char[] line = linea.toCharArray();
	
	if(line.length > 0){
	    if(line[0] == ' '){
		espacio = true;
		longEs = 1;
	    }
	    else{
		espacio = false;
		longEs = 0;
	    }
	}
	
	if(line.length <= 1){
	    for(int i=1; i < line.length; i++ ){
		if(espacio == true &&  line[i] == ' ')
		    longEs++;
		else
		    espacio = false;
	    
	    }
	}
    }
    /**
     * @Override de compareTo. 
     * Compara dos lineas lexicografiamente.
     * @param Linea l, linea a comparar.
     * @return 0 si son iguales.
     * @return -1 si l es mayor.
     * @return 1 si la Linea que manda a llamar el metodo es mayor.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */

    @Override public int compareTo(Linea l) {
	char[] thiis = this.linea.toCharArray();
	char[] that = l.linea.toCharArray();

	if(this.longEs == this.linea.length() && l.longEs == l.linea.length())
	    return 0;
	if(this.longEs == this.linea.length() && l.longEs != l.linea.length())
	    return -1;
	if(this.longEs != this.linea.length() && l.longEs == l.linea.length())
	    return 1;


	if(thiis.length == 0 && 0 == that.length)
	    return 0;
	else{
	    String linea1 = this.linea.replaceAll(" ", "");
	    String linea2 = l.linea.replaceAll(" ", "");	       
	    return linea1.compareTo(linea2);	
	}
	
    }

    /**
     *Metodo to String 
     *@return el texto de la linea (String).
     */
    public String toString(){
	return this.linea;
    }    
}
