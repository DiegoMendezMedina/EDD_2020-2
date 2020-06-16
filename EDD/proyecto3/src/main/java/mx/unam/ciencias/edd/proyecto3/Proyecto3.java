package mx.unam.ciencias.edd.proyecto3;

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
import java.text.Normalizer;

/**
 *Clase Proyecto3.
 *Clase que contiene el main. 
 */

public class Proyecto3{
    static String guardar;
    static String save = "as";
    static boolean existe = false;
    static Lista<String> palabrasNum = new Lista<>();
    static Lista<String> palabras = new Lista<>();
    static Lista<String> palabrasFile = new Lista<>();
    public static void main(String[] args){
	Lista<String> txt = new Lista<>();
	Lista<String> aux = new Lista<>();
	if(args.length == 0){	   
	    System.out.println("No se recibio ningun archivo.");
	    System.exit(1);
	}
	
	if(args.length >= 1){
	    for(int i = 0; i < args.length; i++){
		if(args[i].equals("-o") && existe == false){
		    creaDirectorio(args[i+1]);
		    if(i+1 < args.length){
			save = args[i+1];
			args[i] = args[i+1] = null;
			if(i+3 < args.length)
			    i = i +3;
			else
			    i = args.length;
		    }
		}
		else{   		    
		    txt = leeArchivos(args[i]);	    		    
		    setPalabras(txt);
		    palabras.agrega("FIN");
		}
	    }
	}
	
	if(existe){
	    for(int i = 0; i < args.length; i++){
		if(args[i] != null){
		    for(String s : palabras){
			if(!palabrasNum.contiene(s) && !s.equals("FIN"))
			    palabrasNum.agrega(s);
			    if(s.equals("FIN")){
				palabras.elimina(s);
				break;			 
			    }
			    else{
				palabras.elimina(s);
				palabrasFile.agrega(s);
			    }			    
		    }
		    getStats(args[i]);
		    System.out.println("Lo logramos");
		}
	    }
	}
	else{
	    System.out.println("missing destination file");
	    System.exit(1);
	}
    }

    public static void getStats(String arg){
	Lista<String> aux = new Lista<>();
	String[] words = new String[palabrasNum.getElementos()];
	int[] contWords = new int[palabrasNum.getElementos()];
	int i = 0;
	int cont = 0;
	String cadena = palabrasFile.getPrimero();
	int limite = palabrasNum.getElementos();
	
	while(i < limite){
	    if (palabrasFile.contiene(cadena)){
		    if(!aux.contiene(cadena)){	    
			aux.agrega(cadena);
			contWords[i] = 1;
			words[i] = cadena;
		    }
		    else{
			contWords[i]++;
		    }
		    palabrasFile.elimina(cadena);
	    }
	    else{
		if(!palabrasFile.esVacia())
		    cadena = palabrasFile.getPrimero();
		i++;
	    }	    
	}
	palabrasFile.limpia();
	String salida = "<!DOCTYPE html>\n<html>\n<head>\n\t<meta charset='utf-8'>\n\t<title>"+arg+"</title>\n\t<style>\n";
	salida += "\t.leyendaH {text-align:center;}\n\t.leyenda  ul {list-style-type:none;padding:0 10px;}\n\t.leyendaH ul {display:inline-block;}\n\t.leyenda  ul>li {font-size:14px;}\n\t.leyendaH ul>li {float:left;margin-right:10px;}\n\t.leyenda  ul>li>span {width:12px;height:12px;display:inline-block;margin-right:3px;}\n\t</style>\n</head>\n<body>\n\n";
	
	quicksort(contWords, words, 0, contWords.length-1);
	for(int j = words.length-1; j >= 0;j--)
	    System.out.println(words[j]+":"+contWords[j]);
	//tabla
	salida += "<table class='egt'>\n\t<tr>\n\t\t<th>Palabras :</th>\n\t\t<th>Numero de apariciones :</th>\n\t</tr>\n";
	
	for(int j = contWords.length - 1; j >= 0; j--){
	    salida += "\t<tr>\n\t\t<td>"+words[j]+"</td>\n\t\t<td>"+contWords[j]+"</td>\n";
	}
	salida += "\t</table>\n";
	//Pastel
	salida +="<div style='float:left;margin-right:50px;'>\n\t<canvas id='canvas1'></canvas>\n\t<div id='leyenda1' class='leyenda'></div>\n</div>\n";

	salida += "<div style='float:left;'>\n\t<canvas id='canvas2'></canvas>\n\t<div id='leyenda2' class='leyenda leyendaH'></div>\n</div>\n\n<script>\n";
	
	salida += "var miPastel=function(canvasId,width,height,valores) {\n\tthis.canvas=document.getElementById(canvasId);\n\tthis.canvas.width=width;\n\tthis.canvas.height=height;\n\tthis.radio=Math.min(this.canvas.width/2,this.canvas.height/2);\n\tthis.context=this.canvas.getContext('2d');\n\tthis.valores=valores;\n\n";

	salida += "\tthis.dibujar=function() {\n\t\tthis.total=this.getTotal();\n\t\tvar valor=0;\n\t\tvar inicioAngulo=0;\n\t\tvar angulo=0;\n\t\tfor(var i in this.valores){\n\t\t\tvalor=valores[i]['valor'];\n\t\t\tcolor=valores[i]['color'];\n\t\t\tangulo=2*Math.PI*valor/this.total;\n\t\t\tthis.context.fillStyle=color;\n\t\t\tthis.context.beginPath();\n\t\t\tthis.context.moveTo(this.canvas.width/2, this.canvas.height/2);\n\t\t\tthis.context.arc(this.canvas.width/2, this.canvas.height/2, this.radio, inicioAngulo, (inicioAngulo+angulo));\n\t\t\tthis.context.closePath();\n\t\t\tthis.context.fill();\n\t\t\tinicioAngulo+=angulo;\n\t\t}\n\t}\n\n";

	salida += "\tthis.ponerPorCiento=function(color){\n\t\tvar valor=0;\n\t\tvar etiquetaX=0;\n\t\tvar etiquetaY=0;\n\t\tvar inicioAngulo=0;\n\t\tvar angulo=0;\n\t\tvar texto='';\n\t\tvar incrementar=0;\n\t\tthis.context.font='bold 12pt Calibri';\n\t\tthis.context.fillStyle=color;\n\t\tfor(var i in this.valores){\n\t\t\tvalor=valores[i]['valor'];\n\t\t\tangulo=2*Math.PI*valor/this.total;\n\t\t\tetiquetaX=this.canvas.width/2+(incrementar+this.radio/2)*Math.cos(inicioAngulo+angulo/2);\n\t\t\tetiquetaY=this.canvas.height/2+(incrementar+this.radio/2)*Math.sin(inicioAngulo+angulo/2);\n\t\t\ttexto=Math.round(100*valor/this.total);\n\t\t\tif(etiquetaX<this.canvas.width/2)\n\t\t\t\tetiquetaX-=10;\n\t\t\tthis.context.beginPath();\n\t\t\tthis.context.fillText(texto+'%', etiquetaX, etiquetaY);\n\t\t\tthis.context.stroke();\n\t\t\tinicioAngulo+=angulo;\n\t\t}\n\t}\n\n";

	salida += "\tthis.getTotal=function() {\n\t\tvar total=0;\n\t\tfor(var i in this.valores){\n\t\t\ttotal+=valores[i]['valor'];\n\t\t}\n\t\treturn total;\n\t}\n\n";
	String sal = "<ul class='leyenda'>";
	salida += "\tthis.ponerLeyenda=function(leyendaId) {\n\t\tvar codigoHTML=";
	salida +="\"" + sal + "\"";
	salida +=  "\n\t\tfor(var i in this.valores){\n\t\t\tcodigoHTML+=";

	sal= "<li><span style='background-color:";
	salida +="\"" + sal + "\"";
	salida += "+valores[i]['color']+";
	sal =  "'></span>";
	salida +="\"" + sal + "\"";
	salida += " +i+'</li>';";
	salida += "\n\t\t}\n\t\tcodigoHTML+='</ul>';\n\t\tdocument.getElementById(leyendaId).innerHTML=codigoHTML;\n\t}\n}\n\n";

	String[] colors = new String[14];
	
	colors[0] = "red";
	colors[1] = "#f44336";
	colors[2] = "green";
	colors[3] = "Orange";	
	colors[4] = "#00FF00";
	colors[5] = "#00FFFF";
	colors[6] = "#008000";
	colors[7] = "#008080";
	colors[8] = "#000080";
	colors[9] = "#FF00FF";
	colors[10] = "#800080";
	colors[11] = "#800000";
	colors[12] = "#FFFF00";
	colors[13] = "#808000";
	
	int half = 0;
	int contt = 0;
	int val = 0;
	int total = 0;
	for(int j = 0; j < contWords.length; j++)
	    total += contWords[j];
	if(words.length % 2 == 0)
	    half = (words.length)*3 /5;
	else
	    half = ((words.length + 1 )*3/5);
	System.out.println(half);
	salida += "var valores={\n\n";
	int c = 0;
	for(int j = words.length - 1; j >= 0; j--, c++){
	    if(j >= half && c < 13){
		val = (contWords[j] * 100 )/ total;
		salida += "\t'"+words[j]+"':{valor:"+val+",color:'"+colors[c]+"'},\n";
		contt += val;
	    }
	    else{
		break;
	    }
	}
	val = 100 - contt;
	salida += "\t'Resto de las palabras':{valor:"+val+",color:'"+colors[10]+"'}\n}\n\n";	
	
	salida +="var pastel=new miPastel('canvas1',300,300,valores);\npastel.dibujar();\npastel.ponerPorCiento('white');\npastel.ponerLeyenda('leyenda1');\n</script>\n\n";
	//

	salida += "</body>\n</html>";
	aux.limpia();
	palabrasNum.limpia();
	guardaArchivo(arg, salida);
    }

    public static void quicksort(int[] A,String[] words, int izq, int der) {

	int pivote=A[izq];
	String piv = words[izq];
	int i=izq; 
	int j=der; 
	int aux;
	String auxword= "";
	while(i<j){            
	    while(A[i] <= pivote && i < j)
		i++; 
	    while(A[j]>pivote) j--;         
	    if (i<j) {                      
		aux= A[i];                  
		A[i]=A[j];
		A[j]=aux;

		auxword = words[i];
		words[i] = words[j];
		words[j] = auxword;
		
	    }
	}
	A[izq]=A[j];
	words[izq] = words[j];
	A[j]=pivote; 
	words[j]=piv;
	if(izq<j-1)
	    quicksort(A,words,izq,j-1);
	if(j+1 <der)
	    quicksort(A,words,j+1,der);
    }
    
    /**
     *Metodo leeArchivos
     *@param String[] args. Argumentos que recibio el programa. 
     * Lee los archivos que recibio el programa, los abree y los lee.
     *Tambien verifica si el programa recibio alguna bandera.
     *@return Una lista de Lineas dados los archivos recibidos..
     */
    private static Lista<String> leeArchivos(String args) {
	File archivo = null;
	FileReader fr = null;
	BufferedReader br = null;
	Lista<String> ls= new Lista<String>();
		try {		    
		    archivo = new File (args);
		    fr = new FileReader(archivo);
		    br = new BufferedReader(fr);
		    String line;
		    while( (line = br.readLine()) != null){
			ls.agrega(line);
		    }
		    
		} catch(IOException ioe) {
		    System.out.printf("Archivo no encontrado.");
		}
		
		try{                    
		    if( null != fr ){   
			fr.close();     
		    }                  
		}catch (IOException ioe){ 
		    System.out.printf("Error.");
		}
	    
	return ls; 
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
		if (l.length() == 0)
		    continue;
		s += l+"\n";;
	    }
	}catch (IOException e) {
	    System.out.printf("");	
	}	
	return s;
    }

    private static void creaDirectorio(String arg){
	File f = null;
	f = new File(arg);
	if (!f.exists()) {
	    if (f.mkdirs()) {
		System.out.println("Directorio creado");
		existe = true;
	    } else {
		System.out.println("Error al crear directorio");
	    }
	}
	else existe = true;
    }

    
    /**
     *Metodo guarda archivo.
     *@param String arg. Nombre del archivo donde va a guardar.
     *@param Lista<Linea> salida. Lineas que va a guardar.
     *Dado *arg* abre el archivo y escribe la *salida* sobre el.
     */
    private static void guardaArchivo(String arg, String salida){
	File file = null;
	FileWriter w = null;		    
	BufferedWriter bw = null;
	PrintWriter writer = null;
	FileReader fr = null;
	BufferedReader br = null;
	char[] argg = arg.toCharArray();
	int aux = 0;
	for(int i =0; i < argg.length; i++)
	    if(argg[i] == '.')
		aux = i;
	char[] f = new char[aux];
	for(int i = 0; i < aux; i++)
	    f[i] = argg[i];
	arg = new String(f);	       
	try{	   
	    file = new File(save+"/"+arg+".html");
	    w = new FileWriter(file);
	    bw = new BufferedWriter(w);
	    writer = new PrintWriter(bw);
	    
	    //for(String l : salida){
	    writer.write(salida);
		//writer.append("\n");
		//}
	    writer.close();
	    bw.close();
	}catch(IOException e){
	    System.out.println("Error al guardar archivo");
	}	
    }


    public static void setPalabras(Lista<String> texto){
	for(String s: texto){	    
	    String[] p = s.trim().split(" ");
	    for(int i = 0; i <p.length; i++){
		if(!p[i].equals(""))
		    palabras.agrega(limpiarAcentos(p[i].toLowerCase()));
	    }
	}
    }
    
    public static String limpiarAcentos(String cadena) {
	String limpio =null;
	char[] cad = cadena.toCharArray();
	char[] cadaux = new char[cad.length];
	if (cadena.endsWith(".")) 
	    cadena = cadena.substring(0, cadena.length() - 1);       
	cadena = cadena.replaceAll(",$", "");	
	if (cadena !=null) {
	    String valor = cadena;
	    valor = valor.toLowerCase();
	    limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
	    limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
	    limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
	}
	return limpio;
    }
}

    
