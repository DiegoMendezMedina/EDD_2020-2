package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
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
import java.lang.NumberFormatException;


/**
 *Clase Proyecto2.
 *Clase que contiene el main. 
 *Graficador estructuras de datos.
 */

public class Proyecto2{
    static Lista<String> lista;
    static String estructura = "";
    static String[] elementos = {"0"};
    static Lista<Integer> elementosInt = new Lista<>();
    static String txtconsola = "";
    static String[] lineas = new String [1];
    static boolean masRenglones = true;
    static boolean existe = false;
    static String[] aux = {"",""};
    static String auxx = "";
    static String[] ayayay = {""};
    static int l = 0;
    static int errores = 0;
    
    public static void main(String[] args){
	
	if(args.length == 0){	   
	    txtconsola = leeConsola(System.in).trim();
	    lineas = txtconsola.split("\n");	    	   
	    setElementos();
	    if(!elementosInt.esVacia())
		toSvg();
	    else{
		System.out.println(" No se recibio ningun elemento valido.");
		System.exit(1);
	    }
	}
	
	if(args.length == 1){
	    try {
		txtconsola = leeConsola(new FileInputStream( new File(args[0])));
		if (txtconsola.equals("")) {
		    System.err.println("Error en el archivo.");
		    System.exit(1);
		}
		lineas = txtconsola.split("\n");
		setElementos();
		if(!elementosInt.esVacia())
		    toSvg();
		else{
		    System.out.println(" No se recibio ningun elemento valido.");
		    System.exit(1);
		}	
	    }catch(IOException ioe) {
		System.err.println("Error en el archivo.");
		System.exit(1);
	    }
	}
	
	if(estructura.equals(""))
	    System.out.println(" No se recibio ninguna estructura valida.");

    }	

    /**
     * Metodo privado que manda a llamar al respectivo graficador.
     */
    private static void toSvg(){
	//Pila
	if(estructura.equals("Pila")){
	    Pila pila = new Pila<>();
	    Graficador g = new Graficador();
	    for(Integer i : elementosInt)
		pila.mete(i);
	    System.out.println(g.pilaToSvg(pila));
	}
	//Cola
	if(estructura.equals("Cola")){
	    Cola cola= new Cola<>();
	    Graficador g = new Graficador();
	    for(Integer i : elementosInt)
		cola.mete(i);
	    System.out.println(g.colaToSvg(cola));
	}
	//Lista
	if(estructura.equals("Lista")){
	    Lista lista = new Lista<>();
	    Graficador g = new Graficador();
	    for(Integer i : elementosInt)
		lista.agrega(i);
	    System.out.println(g.listaToSvg(lista));
	}	
	//Ordenado
	if(estructura.equals("ArbolBinarioOrdenado")){
	    ArbolBinarioOrdenado arbolO = new ArbolBinarioOrdenado<>();
	    GraficadorArbolBinario g = new GraficadorArbolBinario();
	    for(Integer i : elementosInt)
		arbolO.agrega(i);
	    System.out.println(g.arbolBinarioToSvg(arbolO));
	}
	//Completo
	if(estructura.equals("ArbolBinarioCompleto")){
	    ArbolBinarioCompleto arbolC = new ArbolBinarioCompleto<>();
	    GraficadorArbolBinario g = new GraficadorArbolBinario();
	    for(Integer i : elementosInt)
		arbolC.agrega(i);
	    System.out.println(g.arbolBinarioToSvg(arbolC));
	}
	//Rojinegro
	if(estructura.equals("ArbolRojinegro")){
	    ArbolRojinegro arbolRN = new ArbolRojinegro<>();
	    GraficadorArbolBinario g = new GraficadorArbolBinario();
	    for(Integer i : elementosInt)
		arbolRN.agrega(i);
	    System.out.println(g.arbolBinarioToSvg(arbolRN));
	}	
	//AVL
	if(estructura.equals("ArbolAVL")){
	    ArbolBinarioOrdenado arbolAVL = new ArbolAVL<>();
	    GraficadorArbolBinario g = new GraficadorArbolBinario();
	    for(Integer i : elementosInt)
		arbolAVL.agrega(i);
	    System.out.println(g.arbolBinarioToSvg(arbolAVL));
	}
	//Monticulo
	if(estructura.equals("MonticuloMinimo")){
	    GraficadorArbolBinario g = new GraficadorArbolBinario();
	    System.out.println(g.monticuloToSvg(elementosInt));
	}
	//Grafica
	if(estructura.equals("Grafica")){
	    GraficadorGrafica g = new GraficadorGrafica();
	    System.out.println(g.graficaToSvg(elementosInt));
	}	
    }

    /**
     * Metodo prvado que llena los elementos, ya sea leidos por la consola o por archivo.
     */
    private static void setElementos(){
	for(int i = 0; i < lineas.length; i++){       	
	    if(existe && !lineas[i].contains("#"))
		auxx += lineas[i];
	    //Pila
	    if(existe == false && lineas[i].contains("Pila") && !lineas[i].contains("#")){
		existe = true;
		estructura = "Pila";
		if(!lineas[i].equals("Pila"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //Cola
	    if(existe == false && lineas[i].contains("Cola") && !lineas[i].contains("#")){
		existe = true;
		estructura = "Cola";
		if(!lineas[i].equals("Cola"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //Lista
	    if(existe == false && lineas[i].contains("Lista") && !lineas[i].contains("#")){
		existe = true;
		estructura = "Lista";
		if(!lineas[i].equals("Lista"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //Rojinegro
	    if(existe == false && lineas[i].contains("ArbolRojinegro") && !lineas[i].contains("#")){
		existe = true;
		estructura = "ArbolRojinegro";
		if(!lineas[i].equals("ArbolRojinegro"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //Ordenado
	    if(existe == false && lineas[i].contains("ArbolBinarioOrdenado") && !lineas[i].contains("#")){
		existe = true;
		estructura = "ArbolBinarioOrdenado";
		if(!lineas[i].equals("ArbolBinarioOrdenado"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //Completo
	    if(existe == false && lineas[i].contains("ArbolBinarioCompleto") && !lineas[i].contains("#")){
		existe = true;
		estructura = "ArbolBinarioCompleto";
		if(!lineas[i].equals("ArbolBinarioCompleto"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //AVL
	    if(existe == false && lineas[i].contains("ArbolAVL") && !lineas[i].contains("#")){
		existe = true;
		estructura = "ArbolAVL";
		if(!lineas[i].equals("ArbolAVL"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //Monticulos
	    if(existe == false && lineas[i].contains("MonticuloMinimo") && !lineas[i].contains("#")){
		existe = true;
		estructura = "MonticuloMinimo";
		if(!lineas[i].equals("MonticuloMinimo"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }
	    //Graficas
	    if(existe == false && lineas[i].contains("Grafica") && !lineas[i].contains("#")){
		existe = true;
		estructura = "Grafica";
		if(!lineas[i].equals("Grafica"))
		    aux = lineas[i].split(" ");
		if(i == lineas.length-1)
		    masRenglones = false;
	    }	    
	}
	
	if(existe == false)
	    System.out.println("No se recibio una estructura valida.");
	

	for(int i = 0; i < aux.length; i++ ){
	    if(aux[i].equals(estructura))
		l = i + 1 ;
	}
	elementos = new String[aux.length - l];

	for(int i = l,j = 0; i < aux.length; i++, j++)
	    elementos[j] = aux[i];
	
	for(String s : elementos)
	    if(!s.equals("") && (s.contains("0") || s.contains("1")|| s.contains("2")|| s.contains("3")|| s.contains("4") || s.contains("5")|| s.contains("6")|| s.contains("7")|| s.contains("8")|| s.contains("9")))
		try{
		    elementosInt.agrega(Integer.parseInt(s.trim()));
		}
		catch(NumberFormatException ioe){
		    errores++;
		    System.out.println("// Error #"+errores+": '"+s+"' no es una entrada valida y no seagrego a la estructura.");
		    continue;
		}
	    else{
		if(!s.equals("")){
		    errores++;
		    System.out.println("// Error #"+errores+": '"+ s +"' no es una entrada valida y no se agrego a la estructura.");
		}
	    }    
	if(masRenglones == true){		
	    ayayay = auxx.split(" ");

	    for(int i = 0; i < ayayay.length; i++)
		if((ayayay[i].contains("0") || ayayay[i].contains("1")|| ayayay[i].contains("2") || ayayay[i].contains("3") || ayayay[i].contains("4") || ayayay[i].contains("5") || ayayay[i].contains("6") || ayayay[i].contains("7") || ayayay[i].contains("8") || ayayay[i].contains("9")) && ayayay[i] != null)
		    try{
			elementosInt.agrega(Integer.parseInt(ayayay[i]));
		    }
		    catch(NumberFormatException ioe){
			errores++;
			System.out.println("// Error #"+errores+": '"+ayayay[i]+"' no es una entrada valida y no se agrego a la estructura.");
			continue;
		    }
		else{
		    if(!ayayay[i].equals("")){
			errores++;
			System.out.println("// Error # "+errores+": '"+ayayay[i]+"' no es una entrada valida y no se agrego a la estructura.");
		    }
		}
	}	
    }
    
    /**
     *Metodo leeConsola
     *El programa no recibio argumentos y ahora leera la consola.
     *@param si, elementos de la consola
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
		s += l+"\n";;
	    }
	}catch (IOException e) {
	    System.out.printf("");	
	}	
	return s;
    }
}
