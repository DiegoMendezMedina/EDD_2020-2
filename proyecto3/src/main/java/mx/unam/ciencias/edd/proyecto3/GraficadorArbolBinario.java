package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.Index;


    /**
     * Clase que grafica arboles binarios.
     */
public class GraficadorArbolBinario{
    
    private static String salida = "";
    private Lista<String> svgRN = new Lista<String>();
    private Lista<String> svgAVL = new Lista<String>();
    private static ArbolBinario<?> arbol;

    public String arbolRN(ArbolBinario<?> arbol) {
	salida= "";
	this.arbol = arbol;
	String cadena = "";
	int altura = arbol.altura() * 120;
	int ancho = arbol.getElementos() * 100  + 20 ;       
	cadena =  "\n <svg width='"+ancho+"' height='"+altura+"'> \n \t<g>\n";
        arbolRN(arbol.raiz(), 0, ancho/2, 0);
	for (String s : svgRN)
	    cadena += s;
	cadena += salida;
	cadena += "\t</g>\n </svg>\n";
	return cadena;
    }
    public String arbolAVL(ArbolBinario<?> arbol) {
	salida = "";
	this.arbol = arbol;
	String cadena = "";
	int altura = arbol.altura() * 120;
	int ancho = arbol.getElementos() * 100 + 20;       
	cadena =  "\n <svg width='"+ancho+"' height='"+altura+"'> \n \t<g>\n";
	cadena += "\n\t<g>\n";
        arbolAVL(arbol.raiz(), 0, ancho/2, 0);
	for (String s : svgAVL)
	    cadena += s;
	cadena += salida;
	cadena += "\t</g>\n </svg>\n";
	return cadena;
    } 
    /**
     * Metodo auxiliar que nos ayuda a recorrer el arbol,
     * @param v, vertice de el arbol.
     * @param xpadre , posicion en x del padre o 0 en caso de ser raiz.
     * @param x, posicion en x que tendra el vertice recibido.
     * @param y, posicion en y que tendra el vertice recibido.
     * @return una cadena svg que representa al arbol recibido.
     */
    public void arbolRN(VerticeArbolBinario<?> v, int xpadre, int x,int y) {
	    salida += verticeRojinegroToSvg(v, x, y+40);		

	if (v.hayIzquierdo()) {
	    int xi = (x - xpadre) / 2; 
	    svgRN.agrega(dibujaLinea(x, y+30, x-xi, y+90));
	    arbolRN(v.izquierdo(), xpadre, x-xi, y+70);
	}
	if (v.hayDerecho()) {
	    int xd = (x - xpadre) / 2;
	    svgRN.agrega(dibujaLinea(x, y+30, x+xd, y+90));
	    arbolRN(v.derecho(), x, x+xd, y+70);
	}
	
    }

    
    private void arbolAVL(VerticeArbolBinario<?> v, int xpadre, int x,int y) {
	
	salida += verticeAVLtoSVG(v, x, y+40);

	if (v.hayIzquierdo()) {
	    int xi = (x - xpadre) / 2; 
	    svgAVL.agrega(dibujaLinea(x, y+30, x-xi, y+90));
	    arbolAVL(v.izquierdo(), xpadre, x-xi, y+70);
	}
	if (v.hayDerecho()) {
	    int xd = (x - xpadre) / 2;
	    svgAVL.agrega(dibujaLinea(x, y+30, x+xd, y+90));
	    arbolAVL(v.derecho(), x, x+xd, y+70);
	}
	
    }
    /**
     * Metodo privado que dibuja una linea.
     * @param x1, coordenada en x del primer punto.
     * @param y1, coordenada en y del primer punto.
     * @param x2, coordenada en x del segundo punto.
     * @param y2, coordenada en y del segundo punto.
     * @return representacion svg de una linea dados dos puntos.
     */
    private String dibujaLinea(int x1, int y1, int x2, int y2) {
	return "\t<line x1='"+x1+"' y1='"+y1+"' x2='"+x2+"' y2='"+y2+"'"+" stroke='black' stroke-width='2'/>\n";
    }   
    
    /**
     * Metodo privado que nos regresa la representacionSvg de un vertice rojinegro.
     * @param v, vertice que se desea pasar a svg.
     * @param x, coordenada en x del vertice para svg.
     * @param y, coordenada en y del vertice para svg.
     * @return representacion en svg de el vertice rojinegro recibido.
     */
    private String verticeRojinegroToSvg(VerticeArbolBinario<?> v, int x, int y){
	String vertice = v.toString();
	int yy = y+3;
	String color = vertice.contains("R") ? "red" : "black";	
	vertice = "\t<circle cx='"+x+"' cy='"+y+"' r='30' stroke='black' fill='"+color+"' />\n \t<text fill='white' font-family='sans-serif' font-size='10' x='"+x+"' y='"+yy+"' text-anchor='middle'>"+v.get().toString()+"</text>\n";
	return vertice;
    }

    /**
     * Metodo privado que nos regresa la representacionSvg de un verticeAVL.
     * @param v, vertice que se desea pasar a svg.
     * @param x, coordenada en x del vertice para svg.
     * @param y, coordenada en y del vertice para svg.
     * @return representacion en svg de el vertice recibido.
     */
    private String verticeAVLtoSVG(VerticeArbolBinario<?> v, int x, int y) {
	int yy = y - 30;
	String vertice = v.toString();
	String[] coord = vertice.split("\\s+");
	vertice = "\t<circle cx='"+x+"' cy='"+y+"' r='30' stroke='black' fill='white' />\n\t<text fill='black' font-family='sans-serif' font-size='10' x='"+x+"' y='"+y+"' text-anchor='middle'>"+v.get().toString()+"</text>\n \t<text fill='black' font-family='sans-serif' font-size='15' x='"+ x  +"' y='"+ yy +"' text-anchor='middle'>["+coord[1]+"]</text>\n";
	return vertice;
    }


}
