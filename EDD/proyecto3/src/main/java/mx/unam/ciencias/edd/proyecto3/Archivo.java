package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;

public class Archivo{
    private String archivo;
    private Lista<String> palabras;
    
    public Archivo(String archivo){
	this.archivo = archivo;
	palabras = new Lista<String>();
    }
    public void setPalabras(String palabra){
	String[] p = palabra.trim().split(" ");
	for(int i = 0; i < p.length; i++)
	    if(!p[i].equals(""))
		palabras.agrega(p[i]);
    }
    public Lista<String> getPalabras(){
	return this.palabras;
    }
    public String getArchivo(){
	return this.archivo;
    }
}
