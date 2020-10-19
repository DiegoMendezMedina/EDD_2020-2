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
	int[] num = new int[args.length];
	final Archivo[] arch = new Archivo[args.length];	
	String cad = "";
	if(args.length == 0){	   
	    System.out.println("No se recibio ningun archivo.");
	    System.exit(1);
	}
	
	if(args.length >= 1){
	    for(int i = 0; i < args.length; i++){
		if(i + 1 < args.length && args[i].equals("-o") && existe == false ){		    
		    creaDirectorio(args[i+1]);
		    if(i+1 < args.length){
			save = args[i+1];
			args[i] = args[i+1] = null;
			if(i + 2 < args.length)
			    i++;
			else
			    break;
		    }
		}
		else{   		    
		    txt = leeArchivos(args[i]);	    		    
		    setPalabras(txt);
		    palabras.agrega("FIN");
		}
	    }
	}
	for(int i = 0; i < args.length; i++){
	    arch[i] = new Archivo(args[i]);
	    num[i] = 0;
	}
	int cont = 0;
	if(existe){
	    for(int i = 0; i < args.length; i++){
		cont = 0;
		if(args[i] != null){
		    for(String s : palabras){
			if(!palabrasNum.contiene(s) && !s.equals("FIN")){
			    palabrasNum.agrega(s);
			    num[i]++;
			    char[] gr = s.toCharArray();
			    if(gr.length >= 7)
				cad += " "+s;
			    
			}
			if(s.equals("FIN")){			    
			    palabras.elimina(s);
			    break;			 
			}
			else{
			    palabras.elimina(s);
			    palabrasFile.agrega(s);
			}			    
		    }
		    arch[i].setPalabras(cad);
		    cad = "";
		    getStats(args[i]);
		}
	    }
	    Grafica<String> grafica = new Grafica<>();
	    for(int i = 0; i < args.length; i++)
		if(args[i] != null)
		    grafica.agrega(args[i]);
	    for(int i = 0; i < arch.length; i++){
		if(arch[i] != null)
		    for(int j = i +1;j < arch.length; j++)
			if(arch[j] != null)
			    for(String s: arch[j].getPalabras())
				if(arch[i].getPalabras().contiene(s))
				    if(!grafica.sonVecinos(args[i], args[j]))
					grafica.conecta(args[i], args[j]);
	    }
	    String html = "<!DOCTYPE html>\n<html>\n<head>\n\t<meta charset='utf-8'>\n\t<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n\t<title>index</title>\n\t\n";
	    html += "\t<style>\n\t\ttr:hover {background-color: #f5f5f5;} \n\t\ttable, th, td {\n\t\t\tborder: 1px solid black;\n\t\t\ttext-align: center;\n\t\t}\n\t</style>\n";
	    html += "<h3>Index</h3>\n";
	    html += "<table class='egt'>\n\t<tr>\n\t\t<th>Documento :</th>\n\t\t<th>Numero de palabras :</th>\n\t\t<th>Link :</th>\n\t</tr>\n";

	    String[] arggs = new String[args.length];
	    for(int i = 0; i < args.length; i++){
		if(args[i] != null){
		    char[] argg = args[i].toCharArray();
		    int auxxx = 0;
		    for(int j =0; j < argg.length; j++)
			if(argg[j] == '.')
			    auxxx = j;
		    char[] f = new char[auxxx];
		    for(int j = 0; j < auxxx; j++)
			f[j] = argg[j];
		    arggs[i] = new String(f);
		}
	    }
	    for(int i = 0; i < args.length; i++)
		if(args[i] != null)
		    html+= "\t<tr>\n\t\t<td>"+args[i]+"</td>\n\t\t<td>"+num[i]+"</td>\n\t<td><a href="+arggs[i]+".html>"+arggs[i]+".html</a></td></tr>\n";
	    html += "\t</table>\n";
	    GraficadorGrafica g = new GraficadorGrafica();
	    html += g.graficaToSvg(grafica);
	    html += "</body>\n</html>";
	    guardaArchivo("index.txt", html);
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
	String salida = "<!DOCTYPE html>\n<html>\n<head>\n\t<meta charset='utf-8'>\n\t<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n\t<title>"+arg+"</title>\n\t<style>\n";

	salida += "\t.leyendaH {text-align:center;}\n\t.leyenda  ul {list-style-type:none;padding:0 10px;}\n\t.leyendaH ul {display:inline-block;}\n\t.leyenda  ul>li {font-size:14px;}\n\t.leyendaH ul>li {float:left;margin-right:10px;}\n\t.leyenda  ul>li>span {width:12px;height:12px;display:inline-block;margin-right:3px;}\n\t</style>\n";
	salida += "\t<style>\n\t\ttr:hover {background-color: #f5f5f5;} \n\t\ttable, th, td {\n\t\t\tborder: 1px solid black;\n\t\t\ttext-align: center;\n\t\t}\n\t</style>\n";
	salida +="\t<style>\n\t.chart-wrap {\n\t\t--chart-width:420px;\n\t\t--grid-color:#aaa;\n\t\t--bar-color:#F16335;\n\t\t--bar-thickness:40px;\n\t\t--bar-rounded: 3px;\n\t\t--bar-spacing:10px;\n\t\tfont-family:sans-serif;\n\t\twidth:var(--chart-width);\n\t}\n\t.chart-wrap.horizontal .grid{\n\t\ttransform:rotate(-90deg);\n\t}\n\t.chart-wrap.horizontal .bar::after{\n\t\ttransform: rotate(45deg);\n\t\tpadding-top:0px;\n\t\tdisplay: block;\n\t}\n\t.chart-wrap .grid{\n\t\tmargin-left:750px;\n\t\tposition:relative;\n\t\tpadding:5px 0 5px 0;\n\t\theight:100%;\n\t\twidth:100%;;\n\t\tborder-left:2px solid var(--grid-color);\n\t}\n\t.chart-wrap .grid::before{\n\t\tfont-size:0.8em;\n\t\tfont-weight:bold;\n\t\tcontent:'0%';\n\t\tposition:absolute;\n\t\tleft:-0.5em;\n\t\ttop:-1.5em;\n\t}\n\t.chart-wrap .grid::after{\n\t\tfont-size:0.8em;\n\t\tfont-weight:bold;\n\t\tcontent:'100%';\n\t\tposition:absolute;\n\t\tright:-1.5em;\n\t\ttop:-1.5em;\n\t}\n\t.chart-wrap.horizontal .grid::before, .chart-wrap.horizontal .grid::after {\n\t\ttransform: rotate(90deg);\n\t}\n\t.chart-wrap .bar {\n\t\twidth: var(--bar-value);\n\t\theight:var(--bar-thickness);\n\t\tmargin:var(--bar-spacing) 0;\n\t\tbackground-color:var(--bar-color);\n\t\tborder-radius:0 var(--bar-rounded) var(--bar-rounded) 0;\n\t}\n\t.chart-wrap .bar:hover{\n\t\topacity:0.7;\n\t}\n\t</style>\n";
	
	salida += "</head>\n<body>\n\n";

	salida += "<h3>Reporte del archivo : "+arg+"</h3>\n";
	salida += "<a href=index.html>Regreso al index</a>\n";
	quicksort(contWords, words, 0, contWords.length-1);
	//tabla
	salida += "<table class='egt'>\n\t<tr>\n\t\t<th>Palabras :</th>\n\t\t<th>Numero de apariciones :</th>\n\t</tr>\n";
	
	for(int j = contWords.length - 1; j >= 0; j--)
	    salida += "\t<tr>\n\t\t<td>"+words[j]+"</td>\n\t\t<td>"+contWords[j]+"</td>\n";
	
	salida += "\t</table>\n";
	//Pastel
	salida +="<div style='float:left;margin-right:50px;'>\n\t<canvas id='canvas1'></canvas>\n\t<div id='leyenda1' class='leyenda'></div>\n</div>\n";

	salida += "<div style='float:left;'>\n\t<canvas id='canvas2'></canvas>\n\t<div id='leyenda2' class='leyenda leyendaH'></div>\n</div>\n\n<script>\n";
	
	salida += "var miPastel=function(canvasId,width,height,valores) {\n\tthis.canvas=document.getElementById(canvasId);\n\tthis.canvas.width=width;\n\tthis.canvas.height=height;\n\tthis.radio=Math.min(this.canvas.width/2,this.canvas.height/2);\n\tthis.context=this.canvas.getContext('2d');\n\tthis.valores=valores;\n\n";

	salida += "\tthis.dibujar=function() {\n\t\tthis.total=this.getTotal();\n\t\tvar valor=0;\n\t\tvar inicioAngulo=0;\n\t\tvar angulo=0;\n\t\tfor(var i in this.valores){\n\t\t\tvalor=valores[i]['valor'];\n\t\t\tcolor=valores[i]['color'];\n\t\t\tangulo=2*Math.PI*valor/this.total;\n\t\t\tthis.context.fillStyle=color;\n\t\t\tthis.context.beginPath();\n\t\t\tthis.context.moveTo(this.canvas.width/2, this.canvas.height/2);\n\t\t\tthis.context.arc(this.canvas.width/2, this.canvas.height/2, this.radio, inicioAngulo, (inicioAngulo+angulo));\n\t\t\tthis.context.closePath();\n\t\t\tthis.context.fill();\n\t\t\tinicioAngulo+=angulo;\n\t\t}\n\t}\n\n";

	salida += "\tthis.ponerPorCiento=function(color){\n\t\tvar valor=0;\n\t\tvar etiquetaX=0;\n\t\tvar etiquetaY=0;\n\t\tvar inicioAngulo=0;\n\t\tvar angulo=0;\n\t\tvar texto='';\n\t\tvar incrementar=0;\n\t\tthis.context.font='bold 12pt Calibri';\n\t\tthis.context.fillStyle=color;\n\t\tfor(var i in this.valores){\n\t\t\tvalor=valores[i]['valor'];\n\t\t\tangulo=2*Math.PI*valor/this.total;\n\t\t\tetiquetaX=this.canvas.width/2+(incrementar+this.radio/2)*Math.cos(inicioAngulo+angulo/2);\n\t\t\tetiquetaY=this.canvas.height/2+(incrementar+this.radio/2)*Math.sin(inicioAngulo+angulo/2);\n\t\t\tif(etiquetaX<this.canvas.width/2)\n\t\t\t\tetiquetaX-=10;\n\t\t\tthis.context.beginPath();\n\t\t\tthis.context.fillText(texto, etiquetaX, etiquetaY);\n\t\t\tthis.context.stroke();\n\t\t\tinicioAngulo+=angulo;\n\t\t}\n\t}\n\n";

	salida += "\tthis.getTotal=function() {\n\t\tvar total=0;\n\t\tfor(var i in this.valores){\n\t\t\ttotal+=valores[i]['valor'];\n\t\t}\n\t\treturn total;\n\t}\n\n";
	String sal = "<ul class='leyenda'>";
	salida += "\tthis.ponerLeyenda=function(leyendaId) {\n\tvar valor = 0;\n\tvar texto='';\n\t\tvar codigoHTML=";
	salida +="\"" + sal + "\"";
	salida +=  "\n\t\tfor(var i in this.valores){\n\tvalor=valores[i]['valor'];\n\ttexto=Math.round(100*valor/this.total);\n\t\t\tcodigoHTML+=";

	sal= "<li><span style='background-color:";
	salida +="\"" + sal + "\"";
	salida += "+valores[i]['color']+";
	sal =  "'></span>";
	salida +="\"" + sal + "\"";
	salida += " +i+': <b>'+texto+'%</b></li>';";
	salida += "\n\t\t}\n\t\tcodigoHTML+='</ul>';\n\t\tdocument.getElementById(leyendaId).innerHTML=codigoHTML;\n\t}\n}\n\n";

	String[] colors = new String[21];
	
	colors[0] = "red";
	colors[1] = "#00FFFF";
	colors[2] = "green";
	colors[3] = "Orange";	
	colors[4] = "#00FF00";
	colors[5] = "#f44336";
	colors[6] = "#008000";
	colors[7] = "#008080";
	colors[8] = "#000080";
	colors[9] = "#FF00FF";
	colors[10] = "#800080";
	colors[11] = "#800000";
	colors[12] = "#FFFF00";
	colors[13] = "#808000";
	colors[14] ="#A52A2A";
	colors[15] = "#FF7F50";
	colors[16] = "#D2691E";
	colors[17] = "#DC143C";
	colors[18] = "#9ACD32";
	colors[19] = "#FA8072";
	colors[20] = "#FF6347 ";
	
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
	salida += "var valores={\n\n";
	int c = 0;
	for(int j = words.length - 1; j >= 0; j--, c++){
	    if(j >= half && c < 21){
		val = (contWords[j] * 100 )/ total;
		salida += "\t'"+words[j]+"':{valor:"+val+",color:'"+colors[c]+"'},\n";
		contt += val;
	    }
	    else{
		break;
	    }
	}
	val = 100 - contt;
	salida +="\t'Resto de las palabras':{valor:"+val+",color:'"+colors[17]+"'}\n}\n\n";	
	
	salida +="var pastel=new miPastel('canvas1',300,300,valores);\npastel.dibujar();\npastel.ponerPorCiento('white');\npastel.ponerLeyenda('leyenda1');\n</script>\n\n";
	
	//Barras
	salida += "<div class=";
	sal = "chart-wrap horizontal";
	salida +="\"" + sal + "\"";
	salida+=">\n\t<div class='grid'>\n";
	c = 0;
	contt = 0;
	for(int j = words.length - 1; j >= 0; j--, c++){
	    if(j >= half){
		val = (contWords[j] * 100 )/ total;
		salida+= "\t\t<div class='bar' style='--bar-value:"+val+"%;' data-name='"+words[j]+"' title='"+words[j]+": "+val+"%'></div>\n";
		contt += val;
	    }
	    else{
		break;
	    }
	}
	val = 100 - contt;
	salida+= "\t\t<div class='bar' style='--bar-value:"+val+"%;' data-name='Demas palabras' title='Demas palabras"+val+"%'></div>\n";
	
	salida +="\t</div>\n</div>\n";
	salida += "<br>\n<br>\n<br>\n<br>\n<br>\n<br>\n<br>\n<br>\n<br>";
	//Arboles
	Lista<Palabra> lpalabras = new Lista<Palabra>();
	int ji = words.length;
	if(ji >= 15){
	    ji = ji - 15;
	    for(int j = words.length-1; j >= ji; j--){
		Palabra pal = new Palabra(contWords[j], words[j]);
		lpalabras.agrega(pal);
	    }
	}
	else{
	    for(int j = words.length-1; j >= 0; j--){
		Palabra pal = new Palabra(contWords[j], words[j]);
		lpalabras.agrega(pal);
	    }
	}
	ArbolRojinegro rn = new ArbolRojinegro<>();
	GraficadorArbolBinario g = new GraficadorArbolBinario();
	GraficadorArbolBinario gg = new GraficadorArbolBinario();
	ArbolAVL avl = new ArbolAVL<>();	
	for(Palabra j : lpalabras){
	    rn.agrega(j);
	    avl.agrega(j);
	}
	
	salida += g.arbolRN(rn);
	salida += gg.arbolAVL(avl);

	


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
		    System.out.printf("No se encontro el archivo : "+args+".\n");
		    System.out.println("El programa se cerrara.");
		    System.exit(1);
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

    private static void creaDirectorio(String arg){
	if(arg.contains(".") && !arg.contains("./")){
	    System.out.println("El directorio recibido: "+arg+", es un archivo.\nEl programa se cerrara");
	    System.exit(1);
	}
	    
	File f = null;
	f = new File(arg);
	if (!f.exists()) {
	    if (f.mkdirs()) {
		System.out.println("Directorio creado");
		existe = true;
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
	    if(save.endsWith("/"))
		file = new File(save+arg+".html");
	    else
		file = new File(save+"/"+arg+".html");
	    w = new FileWriter(file);
	    bw = new BufferedWriter(w);
	    writer = new PrintWriter(bw);	   
	    writer.write(salida);
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
		if(!p[i].equals("")){
		    String limpio = limpiarAcentos(p[i].toLowerCase());
			if(limpio != null)
			    palabras.agrega(limpio);
		}
	    }
	}
    }
    
    public static String limpiarAcentos(String cadena) {
	String limpio =null;
	char[] cad = cadena.toCharArray();
	char[] cadaux = new char[cad.length];
	if (cadena.endsWith(".")) 
	    cadena = cadena.substring(0, cadena.length() - 1);
	//if (cadena.startsWith(".")) 
	//  cadena = cadena.substring(0, 0);       
	cadena = cadena.replaceAll(",$", "");	
	if (cadena !=null) {
	    if(cadena != ""){
		String valor = cadena;
		valor = valor.toLowerCase();
		limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
		//limpio = limpio.replaceAll("[^\\p{L}\\p{Z}]","");
		limpio = limpio.replaceAll("[^\\w\\s]","");
		limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
		limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
	    }
	}
	if(limpio.equals(""))
	    return null;
	return limpio;
    }
}

    
