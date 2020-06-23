package mx.unam.ciencias.edd.proyecto3;

public class Palabra implements Comparable<Palabra>{
    private int repeticiones;
    private String palabra;

    public Palabra(int repeticiones, String palabra){
	this.repeticiones = repeticiones;
	this.palabra = palabra;
    }

    @Override public int compareTo(Palabra p) {
	return this.repeticiones - p.repeticiones;
	}

    public String toString(){
	return this.palabra;
    }
}
