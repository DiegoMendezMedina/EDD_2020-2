package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *Clase Proyecto1.
 *Clase que contiene el main. 
 *Ordenador Lexicografico.
 */

public class Proyecto1 {

    static String bandera="";
    static OrdenadorLexicografico ordenador;
    //static Linea l;
    static String guardar;
    
    public static void main(String[] args){
	Lista<Linea> txt = new Lista<Linea>() ;
	Lista<Linea> salida= new Lista<Linea>();
	if(args.length == 0){
	    System.out.println("No se recibio ningun archivo. Escribe por lineas. Introduce -exit para salir " );
	    
	    txt = llena(leeConsola(System.in));	    
	    ordenador= new OrdenadorLexicografico(txt);	    
	    salida = ordenador.ordenar();	    
	    
	    for(Linea l: salida)
		System.out.println(l.toString());
	    
	}

	if(args.length >= 1){
	    txt = leeArchivos(args);	    
	    ordenador = new OrdenadorLexicografico(txt);
	    if(bandera.equals("-o")){
		salida=ordenador.ordenar();
		for(Linea l: salida)
		    System.out.println(l.toString());
		guardaArchivo(guardar, salida);
	    }
	    if (bandera.equals("-o-r") || bandera.equals("-r-o")){
		salida = ordenador.reversa();
		    for(Linea l: salida)
			System.out.println(l.toString());
		    guardaArchivo(guardar, salida);
	    }
	    if(bandera.equals("-r")){
		    salida = ordenador.reversa();
		    for(Linea l: salida)
			System.out.println(l.toString());
	    }	   
	    if( bandera.equals("")){
		salida = ordenador.ordenar();
		for(Linea l: salida)
		    System.out.println(l.toString());
	    }

	}
    }
    /**
     *Metodo llena
     *@param String texto. 
     *Llena una Lista de Lineas dado el texto recibido y la regresa.
     *Separa al texto por saltos de lineas ("\n")
     *@return Una lista de Lineas.
     */
    private static Lista<Linea> llena(String texto){	
	Lista <Linea> l= new Lista<Linea>();
	String[] lines = texto.split("\n");
	for(String s : lines){	    
	    Linea li = new Linea(s);
	    l.agrega(li);
	}
	return l;
    }
     /**
     *Metodo leeConsola
     *El programa no recibio argumentos y ahora leera la consola.
     *@param elementos de la consola
     *Lee la consola y regresa una cadena para posteriormente llenar una Lista de lineas.
     *@return una cadena dado lo recibido en la consola.
     */
    private static String leeConsola(InputStream si) {
	String s = "";
	try {
	    String l;
	    BufferedReader in = new BufferedReader(new InputStreamReader(si));
	    while (true) {
		l = in.readLine();
		if(l == null)
		    break;
		if (l.equals("-exit"))
		    break;
		if (l.length() == 0)
		    continue;
		s += l+"\n";
	    }
	}catch (IOException e) {
	    System.out.printf("");	
	}	
	return s;
    }
     /**
     *Metodo leeArchivos
     *@param String[] args. Argumentos que recibio el programa. 
     * Lee los archivos que recibio el programa, los abree y los lee.
     *Tambien verifica si el programa recibio alguna bandera.
     *@return Una lista de Lineas dados los archivos recibidos..
     */
    private static Lista<Linea> leeArchivos(String[] args) {
	File archivo = null;
	FileReader fr = null;
	BufferedReader br = null;
	Lista<Linea> ls= new Lista<Linea>();
	
	for (int i = 0; i < args.length; i++) {
	    if(args[i].equals("-o")){
		bandera = bandera + args[i];
		guardar = args[i+1];
		if(i+1 < args.length)
		    args[i+1] = "";
	    }
	    else{
	    if (args[i].equals("-r")) {
		bandera = bandera + args[i];
	    }
	    else{
	    try {
		archivo = new File (args[i]);
		fr = new FileReader(archivo);
		br = new BufferedReader(fr);
		String line;
		while( (line=br.readLine()) != null){
		    ls.agrega(new Linea(line));
		}		    
	    } catch(IOException ioe) {
		System.out.printf("Archivo no encontrado.");
		continue;
	    }	    	    
	    }
	    try{                    
		if( null != fr ){   
		    fr.close();     
		}                  
	    }catch (IOException ioe){ 
		System.out.printf("Error.");
	    }
	    }
	}
	return ls; 
    }
 /**
     *Metodo guarda archivo.
     *@param String arg. Nombre del archivo donde va a guardar.
     *@param Lista<Linea> salida. Lineas que va a guardar.
     *Dado *arg* abre el archivo y escribe la *salida* sobre el.
     */
    private static void guardaArchivo(String arg, Lista<Linea> salida){
	File f = null;
	FileWriter w = null;		    
	BufferedWriter bw = null;
	PrintWriter writer = null;
	FileReader fr = null;
	BufferedReader br = null;
	try{
	    f = new File(arg);
	    w = new FileWriter(f);
	    bw = new BufferedWriter(w);
	    writer = new PrintWriter(bw);
	    
	    for(Linea l : salida){
		 writer.write(l.toString());
		 writer.append("\n");
	    }
	     writer.close();
	     bw.close();
	}catch(IOException e){
	    System.out.println("Error al guardar archivo");
	}	
    }
}    

